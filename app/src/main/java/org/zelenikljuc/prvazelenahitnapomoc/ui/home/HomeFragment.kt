package org.zelenikljuc.prvazelenahitnapomoc.ui.home

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
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import org.zelenikljuc.common.utils.NUM_OF_COLUMNS
import org.zelenikljuc.prvazelenahitnapomoc.MainViewModel
import org.zelenikljuc.prvazelenahitnapomoc.ui.home.adapter.HomeCategoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import org.zelenikljuc.common.models.home.categories.Category
import org.zelenikljuc.prvazelenahitnapomoc.R
import org.zelenikljuc.prvazelenahitnapomoc.databinding.FragmentHomeBinding


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    //private val viewModel: HomeViewModel by viewModels()
    private val viewModel: MainViewModel by activityViewModels()

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
        checkInternetConnection()
        initAdapter()
        loadCategories()
        reportProblem()
        openCategory()
        openWasteDisposal()
    }

    private fun loadCategories() {
        viewModel.categories.observe(viewLifecycleOwner){ categories ->
            categoryAdapter.differ.submitList(categories)
        }
    }

    private fun reportProblem() {
        binding.btnReportProblem.setOnClickListener {
            view?.findNavController()?.navigate(HomeFragmentDirections.actionNavigationHomeToReportProblemFragment(null))
        }
    }

    private fun openCategory() {
        categoryAdapter.setOnItemClickListener { category: Category ->
            view?.findNavController()?.navigate(HomeFragmentDirections.actionNavigationHomeToCategoryFragment(category))
        }
    }

    private fun openWasteDisposal() {
        binding.btnShowWasteMap.setOnClickListener {
            view?.findNavController()?.navigate(HomeFragmentDirections.actionNavigationHomeToWasteDisposalFragment())
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