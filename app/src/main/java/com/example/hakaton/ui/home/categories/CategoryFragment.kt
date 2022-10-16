package com.example.hakaton.ui.home.categories

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.hakaton.R
import com.example.hakaton.databinding.FragmentCategoryBinding
import com.example.hakaton.ui.home.categories.adapter.CategoryAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CategoryViewModel by viewModels()

    private val categoryAdapter: CategoryAdapter by lazy { CategoryAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        val args: CategoryFragmentArgs by navArgs()
        args?.let {
            it.category?.let { category ->
                viewModel.category = category
                categoryAdapter.differ.submitList(category.subcategories)
                initViews()
            }
        }
        openSubcategoryUrl()
        setUpListeners()
    }

    private fun setUpListeners() {
        binding.apply {
            toolbar.toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun openSubcategoryUrl() {
        categoryAdapter.setOnItemClickListener { subcategory ->
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(subcategory.url)))
        }
    }

    private fun initViews() {
        binding.apply {
            viewModel.category?.let {
                textCategoryName.text = it.name
                textCategoryDescription.text = it.description

                Glide.with(categoryImage)
                    .load(it.image)
                    .placeholder(R.drawable.ic_thumb)
                    .centerCrop()
                    .into(categoryImage)
            }
        }
    }

    private fun initAdapter() {
        with(binding) {
            recyclerSubcategories.apply {
                layoutManager = LinearLayoutManager(requireActivity())
                adapter = categoryAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}