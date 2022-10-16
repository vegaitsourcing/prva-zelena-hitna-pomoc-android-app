package com.example.hakaton.ui.home.wastedisposal

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hakaton.databinding.FragmentWasteDisposalBinding
import com.example.hakaton.ui.home.wastedisposal.adater.WasteDisposalAdapter
import dagger.hilt.android.AndroidEntryPoint

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
        wasteDisposalAdapter.setOnItemClickListener { location ->
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(location.url)))
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}