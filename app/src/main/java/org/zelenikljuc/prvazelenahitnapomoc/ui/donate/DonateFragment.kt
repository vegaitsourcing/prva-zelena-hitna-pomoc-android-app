package org.zelenikljuc.prvazelenahitnapomoc.ui.donate

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.text.color
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.zelenikljuc.prvazelenahitnapomoc.R
import org.zelenikljuc.prvazelenahitnapomoc.databinding.FragmentDonateBinding

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
        checkInternetConnection()
        loadDonationData()
        setUpListeners()
    }

    private fun setUpListeners() {
        binding.apply {
            toolbar.toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun loadDonationData() {
        viewModel.donateDetails.observe(viewLifecycleOwner){ donationDetails->
            binding.apply {
                donationDetails?.let {
                    textDonateDescription.text = donationDetails.description
                    val cardNumberColor = ContextCompat.getColor(requireActivity(), R.color.text_desc_color)
                    val cardNumberText = SpannableStringBuilder()
                        .append(textBankAccountNumber.text)
                        .append(": ")
                        .color(cardNumberColor) { append(donationDetails.cardNumber) }

                    textBankAccountNumber.text = cardNumberText
                }
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