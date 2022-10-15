package com.example.hakaton.ui.news.fullnews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.hakaton.R
import com.example.hakaton.databinding.FragmentFullNewsBinding

class FullNewsFragment : Fragment() {

    private var _binding: FragmentFullNewsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FullNewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFullNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: FullNewsFragmentArgs by navArgs()
        args?.let {
            it.news?.let { news ->
                viewModel.news = news
                initViews()
            }
        }
        setUpListeners()
    }

    private fun setUpListeners() {
        binding.apply {
            toolbar.toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun initViews() {
        binding.apply {
            viewModel.news?.let {
                textNewsDate.text = it.date
                textNewsTitle.text = it.title
                textNewsBody.text = it.description

                Glide.with(imageNews)
                    .load(it.image)
                    .placeholder(R.drawable.ic_thumb)
                    .centerCrop()
                    .into(imageNews)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}