package org.zelenikljuc.prvazelenahitnapomoc.ui.home.reportproblem.categories

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import org.zelenikljuc.common.models.home.categories.Category
import org.zelenikljuc.common.models.home.reportproblem.Problem
import org.zelenikljuc.prvazelenahitnapomoc.R
import org.zelenikljuc.prvazelenahitnapomoc.databinding.FragmentSelectCategoryBinding
import org.zelenikljuc.prvazelenahitnapomoc.MainViewModel
import org.zelenikljuc.prvazelenahitnapomoc.ui.home.reportproblem.categories.adapter.SelectCategoryAdapter

class SelectCategoryFragment : Fragment() {

    private var _binding: FragmentSelectCategoryBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: MainViewModel by activityViewModels()
    private val viewModel: SelectCategoryViewModel by viewModels()

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

        val args: SelectCategoryFragmentArgs by navArgs()
        args?.let {
            it.problem?.let { problem: Problem ->
                viewModel.problem = problem
            }
        }

        checkInternetConnection()
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
        categoryAdapter.setOnItemClickListener { category: Category ->
            viewModel.apply {
                problem.category = category.name
                view?.findNavController()?.apply {
                    previousBackStackEntry?.savedStateHandle?.set("problem", problem)
                    popBackStack()
                }
            }
        }
    }

    private fun loadCategories() {
        sharedViewModel.categories.observe(viewLifecycleOwner){ categories ->
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