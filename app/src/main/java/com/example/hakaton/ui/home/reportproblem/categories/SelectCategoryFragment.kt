package com.example.hakaton.ui.home.reportproblem.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hakaton.MainViewModel
import com.example.hakaton.databinding.FragmentSelectCategoryBinding
import com.example.hakaton.ui.home.reportproblem.categories.adapter.SelectCategoryAdapter

class SelectCategoryFragment : Fragment() {

    private var _binding: FragmentSelectCategoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()

    private val categoryAdapter: SelectCategoryAdapter by lazy { SelectCategoryAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        loadCategories()
        selectCategory()
        setUpListeners()
    }

    private fun setUpListeners() {
        binding.apply {
            toolbar.toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun selectCategory() {
        categoryAdapter.setOnItemClickListener { category ->
            view?.findNavController()?.navigate(SelectCategoryFragmentDirections.actionSelectCategoryFragmentToReportProblemFragment(category))
        }
    }

    private fun loadCategories() {
        viewModel.categories.observe(viewLifecycleOwner){ categories ->
            categoryAdapter.differ.submitList(categories)
        }
    }

    private fun initAdapter() {
        with(binding) {
            recyclerCategories.apply {
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