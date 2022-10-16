package com.example.hakaton.ui.home.reportproblem

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.models.home.reportproblem.Problem
import com.example.common.utils.TAG
import com.example.common.utils.sdk29AndUp
import com.example.hakaton.R
import com.example.hakaton.databinding.FragmentReportProblemBinding
import com.example.hakaton.ui.home.reportproblem.adapter.IOnMediaFileClickListener
import com.example.hakaton.ui.home.reportproblem.adapter.MediaFilesAdapter
import com.example.hakaton.util.LocaionUtil.DEFAULT_LAT
import com.example.hakaton.util.LocaionUtil.DEFAULT_LNG
import com.example.hakaton.util.LocaionUtil.REQUEST_CODE_LOCATION_PERMISSION
import com.example.hakaton.util.LocaionUtil.hasLocationPermissions
import com.example.hakaton.util.displayLocationSettingsRequest
import com.example.hakaton.util.hideKeyboard
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import dagger.hilt.android.AndroidEntryPoint
import io.nlopez.smartlocation.SmartLocation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ReportProblemFragment : Fragment(), IOnMediaFileClickListener, EasyPermissions.PermissionCallbacks {

    private var _binding: FragmentReportProblemBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ReportProblemViewModel by viewModels()

    private var readPermissionGranted = false
    private var writePermissionGranted = false

    private var mediaFilesAdapter: MediaFilesAdapter? = null

    private val location by lazy { LocationServices.getFusedLocationProviderClient(requireActivity()) }
    private val geocoder by lazy { Geocoder(requireContext(), Locale.getDefault()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportProblemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: ReportProblemFragmentArgs by navArgs()
        args?.let {
            it.category?.let { category ->
                binding.etCategory.setText(category.name)
            }
        }

        initAdapter()
        reportProblem()
        observeReportStatus()
        addMediaFile()
        updateOrRequestPermissions()
        setUpClickListeners()
    }

    private fun setUpClickListeners() {
        binding.apply {
            tilLocation.setEndIconOnClickListener {
                requestLocationPermissions()
            }
            tilCategory.setEndIconOnClickListener {
                view?.findNavController()?.navigate(ReportProblemFragmentDirections.actionReportProblemFragmentToSelectCategoryFragment())
            }
            toolbar.toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun reportProblem() {
        binding.btnReportProblem.setOnClickListener {
            if (inputCheck()) viewModel.reportProblem(createProblem())
            else Toast.makeText(requireActivity(), getString(R.string.text_invalid_input), Toast.LENGTH_SHORT).show()
        }
    }

    private fun createProblem() : Problem {
        binding.apply {
            return Problem(
                id = UUID.randomUUID().toString(),
                userName = if (checkboxReportPrivate.isChecked) "" else etName.text.toString(),
                location = etLocation.text.toString(),
                category = etCategory.text.toString(),
                description = etDescription.text.toString(),
                media = "",
                timestamp = getDate(),
            )
        }
    }

    private fun inputCheck() : Boolean {
        binding.apply {
            return etName.text.toString().isNotEmpty() && etLocation.text.toString().isNotEmpty() && etCategory.text.toString().isNotEmpty() && etDescription.text.toString().isNotEmpty()
        }
    }

    private fun getDate(): String = SimpleDateFormat("dd-MM-yyyy").format(Date())

    private fun observeReportStatus() {
        viewModel.problemReported.observe(viewLifecycleOwner) { response->
            response?.let {
                view?.findNavController()?.navigate(ReportProblemFragmentDirections.actionReportProblemFragmentToSuccessfullyReportedFragment())
            }
        }
    }

    private fun initAdapter() {
        with(binding) {
            mediaFilesAdapter = MediaFilesAdapter(viewModel.mediaFiles, this@ReportProblemFragment)
            recyclerMediaFiles.apply {
                layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
                adapter = mediaFilesAdapter
            }
        }
    }

    private fun storeMediaFile(uri: Uri) {
        viewModel.apply {
            mediaFiles.add(uri)
            binding.recyclerMediaFiles.visibility = View.VISIBLE
            initAdapter()
        }
    }

    private fun addMediaFile() {
        binding.apply {
            btnOpenGallery.setOnClickListener {
                openGallery()
            }
            btnOpenCamera.setOnClickListener {
                openCamera()
            }
        }
    }

    override fun onMediaFileClick(position: Int, uri: Uri) {
        viewModel.mediaFiles.remove(uri)
        mediaFilesAdapter?.notifyDataSetChanged()
        if (viewModel.mediaFiles.isEmpty()) binding.recyclerMediaFiles.visibility = View.GONE
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/* video/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/*", "video/*"))
        resultLauncher.launch(intent)
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            onActivityResult(result)
        }

    private fun onActivityResult(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            //If selected multiple medias
            if (result.data?.clipData != null) {
                val count: Int =
                    result.data!!.clipData!!.itemCount
                for (i in 0 until count) {
                    val selectedUri: Uri? = result.data!!.clipData?.getItemAt(i)?.uri
                    selectedUri?.let { storeMediaFile(uri = selectedUri) }
                }
            }
            //If selected single media
            else if (result.data?.data != null) {
                val selectedUri: Uri? = result.data?.data
                selectedUri?.let { storeMediaFile(uri = selectedUri) }
            }
        }
    }

    private fun openCamera() {
        takePhoto.launch()
    }

    private val takePhoto = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
        lifecycleScope.launch {
            val isSavedSuccessfully = savePhotoToExternalStorage(UUID.randomUUID().toString(), it!!)
            if(isSavedSuccessfully) Log.d(TAG, "Photo saved successfully")
            else Log.d(TAG, "Failed to save photo")
        }
    }

    private suspend fun savePhotoToExternalStorage(displayName: String, bmp: Bitmap): Boolean {
        return withContext(Dispatchers.IO) {
            val imageCollection = sdk29AndUp {
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            } ?: MediaStore.Images.Media.EXTERNAL_CONTENT_URI

            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "$displayName.jpg")
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.WIDTH, bmp.width)
                put(MediaStore.Images.Media.HEIGHT, bmp.height)
            }
            try {
                requireActivity().contentResolver.insert(imageCollection, contentValues)?.also { uri ->
                    requireActivity().contentResolver.openOutputStream(uri).use { outputStream ->
                        if(!bmp.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)) {
                            throw IOException("Couldn't save bitmap")
                        } else {
                            withContext(Dispatchers.Main) { uri?.let { storeMediaFile(uri) } }
                        }
                    }
                } ?: throw IOException("Couldn't create MediaStore entry")
                true
            } catch(e: IOException) {
                e.printStackTrace()
                false
            }
        }
    }

    private fun updateOrRequestPermissions() {
        val hasReadPermission = ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        val hasWritePermission = ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        val minSdk29 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

        readPermissionGranted = hasReadPermission
        writePermissionGranted = hasWritePermission || minSdk29

        val permissionsToRequest = mutableListOf<String>()
        if (!writePermissionGranted) {
            permissionsToRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (!readPermissionGranted) {
            permissionsToRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private fun requestLocationPermissions() {
        if (hasLocationPermissions(requireContext())) {
            getCurrentAddress()
        } else {
            EasyPermissions.requestPermissions(
                this,
                this.getString(R.string.enable_location),
                REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            SettingsDialog.Builder(requireContext()).build().show()
        } else {
            requestLocationPermissions()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        requestLocationPermissions()
        getCurrentAddress()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    private fun getCurrentAddress() {
        val location = getCurrentLocation()
        val latLng = location?.let {
            LatLng(it.latitude, it.longitude)
        } ?: LatLng(DEFAULT_LAT, DEFAULT_LNG)
        val addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)[0]
        val currentAddress = addressList.getAddressLine(0)
        binding.etLocation.setText(currentAddress)
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() : Location? {
        if (viewModel.currentLocation == null) {
            lifecycleScope.launch {
                location.lastLocation.addOnSuccessListener { location ->
                    viewModel.currentLocation = location
                }.await()
            }
        }
        return viewModel.currentLocation
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}