package org.zelenikljuc.prvazelenahitnapomoc.ui.home.wastedisposal

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.zelenikljuc.prvazelenahitnapomoc.ui.home.wastedisposal.adater.WasteDisposalAdapter
import dagger.hilt.android.AndroidEntryPoint
import org.zelenikljuc.common.models.home.wastedisposal.WasteDisposalLocation
import org.zelenikljuc.prvazelenahitnapomoc.R
import org.zelenikljuc.prvazelenahitnapomoc.databinding.FragmentWasteDisposalBinding

@AndroidEntryPoint
class WasteDisposalFragment : Fragment() {

    private var _binding: FragmentWasteDisposalBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WasteDisposalViewModel by viewModels()

    private val wasteDisposalAdapter: WasteDisposalAdapter by lazy { WasteDisposalAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWasteDisposalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkInternetConnection()
        initAdapter()
        setUpListeners()
        loadWasteDisposalDetails()
        showLocation()
    }

    private fun setUpListeners() {
        binding.apply {
            toolbar.toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun showLocation() {
        wasteDisposalAdapter.setOnItemClickListener { location: WasteDisposalLocation ->
            if (location.url.contains("http")) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(location.url)))
            } else {
                Toast.makeText(requireActivity(), getString(R.string.toast_error), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadWasteDisposalDetails() {
        viewModel.wasteDisposalDetails.observe(viewLifecycleOwner) { wasteDisposalDetails->
            wasteDisposalDetails?.let {
                binding.apply {
                    textWasteDisposalTitle.text = wasteDisposalDetails.title
                    textWasteDisposalDescription.text = wasteDisposalDetails.description
                }
                wasteDisposalAdapter.differ.submitList(wasteDisposalDetails.locations)
            }
        }
    }

    private fun initAdapter() {
        with(binding) {
            recyclerWasteDisposalLocations.apply {
                layoutManager = LinearLayoutManager(requireActivity())
                adapter = wasteDisposalAdapter
            }
        }
    }

    private fun checkInternetConnection() {
        if (isInternetConnected().not()) Toast.makeText(requireActivity(), getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show()
    }

    private fun isInternetConnected(): Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm.activeNetwork != null && cm.getNetworkCapabilities(cm.activeNetwork) != null
        } else {
            cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnectedOrConnecting
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}