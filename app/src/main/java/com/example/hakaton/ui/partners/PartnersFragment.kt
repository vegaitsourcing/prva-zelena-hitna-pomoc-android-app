package com.example.hakaton.ui.partners

import android.content.Context
import android.net.ConnectivityManager
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
import com.example.hakaton.R
import com.example.hakaton.databinding.FragmentPartnersBinding
import com.example.hakaton.ui.partners.adapter.PartnersAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PartnersFragment : Fragment() {

    private var _binding: FragmentPartnersBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PartnersViewModel by viewModels()

    private val partnersAdapter: PartnersAdapter by lazy { PartnersAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPartnersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkInternetConnection()
        initAdapter()
        loadPartnersData()
        setUpClickListeners()
    }

    private fun setUpClickListeners() {
        binding.toolbar.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun loadPartnersData() {
        viewModel.partnerDetails.observe(viewLifecycleOwner) { partnerDetails->
            partnerDetails?.let {
                binding.textPartnersDescription.text = partnerDetails.description
                partnersAdapter.differ.submitList(partnerDetails.listOfPartners)
            }
        }
    }

    private fun initAdapter() {
        with(binding) {
            recyclerPartners.apply {
                layoutManager = LinearLayoutManager(requireActivity())
                adapter = partnersAdapter
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