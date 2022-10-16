package com.example.hakaton.ui.contact

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.hakaton.databinding.FragmentContactBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactFragment : Fragment() {

    private var _binding: FragmentContactBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ContactViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadContactData()
        setUpListeners()
    }

    private fun setUpListeners() {
        binding.apply {
            toolbar.toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun loadContactData() {
        viewModel.contactDetails.observe(viewLifecycleOwner){ contact->
            binding.apply {
                contact?.let {
                    textContactDescription.text = "${textContactDescription.text}: ${contact.description}"
                    textPhoneNumber.text = "${textPhoneNumber.text}: ${contact.phoneNumber}"
                    textEmail.text = "${textEmail.text}: ${contact.email}"
                    textWebPage.text = "${textWebPage.text}: ${contact.webAddress}"
                    btnInstagram.setOnClickListener { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(contact.instagramProfile))) }
                    btnFacebook.setOnClickListener { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(contact.facebookProfile))) }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}