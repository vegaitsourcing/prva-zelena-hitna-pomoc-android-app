package com.example.hakaton.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.common.utils.NUM_OF_COLUMNS
import com.example.hakaton.R
import com.example.hakaton.databinding.FragmentHomeBinding
import com.example.hakaton.ui.home.adapter.HomeCategoryAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private val categoryAdapter: HomeCategoryAdapter by lazy { HomeCategoryAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        loadCategories()
        reportProblem()
        openCategory()
    }

    private fun loadCategories() {
        viewModel.categories.observe(viewLifecycleOwner){ categories ->
            categoryAdapter.differ.submitList(categories)
        }
    }

    private fun reportProblem() {
        binding.btnReportProblem.setOnClickListener {
            view?.findNavController()?.navigate(HomeFragmentDirections.actionNavigationHomeToReportProblemFragment())
        }
    }

    private fun openCategory() {
        categoryAdapter.setOnItemClickListener { category ->
            view?.findNavController()?.navigate(HomeFragmentDirections.actionNavigationHomeToCategoryFragment(category))
        }
    }

    private fun initAdapter() {
        with(binding) {
            recyclerCategories.apply {
                layoutManager = GridLayoutManager(requireActivity(), NUM_OF_COLUMNS)
                adapter = categoryAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}