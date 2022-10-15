package com.example.hakaton.ui.donate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.hakaton.databinding.FragmentDonateBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DonateFragment : Fragment() {

    private var _binding: FragmentDonateBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DonateViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDonateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadDonationData()
    }

    private fun loadDonationData() {
        viewModel.donateDetails.observe(viewLifecycleOwner){ donationDetails->
            binding.apply {
                donationDetails?.let {
                    textDonateDescription.text = donationDetails.description
                    textBankAccountNumber.text = "${textBankAccountNumber.text}: ${donationDetails.cardNumber} "
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}