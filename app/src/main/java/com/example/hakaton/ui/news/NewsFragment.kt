package com.example.hakaton.ui.news

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.doOnNextLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hakaton.R
import com.example.hakaton.databinding.FragmentNewsBinding
import com.example.hakaton.ui.news.adapter.NewsAdapter
import com.example.hakaton.util.hideKeyboard
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NewsViewModel by viewModels()

    private val newsAdapter: NewsAdapter by lazy { NewsAdapter() }
    private lateinit var etSearch: EditText
    private var searchItem: MenuItem? = null
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkInternetConnection()
        initAdapter()
        setUpListeners()
        setUpSearch()
        loadNews()
        openFullNews()
    }

    private fun setUpListeners() {
        binding.apply {
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun setUpSearch() {
        searchItem = binding.toolbar.menu?.findItem(R.id.searchItem)
        searchView = searchItem?.actionView as SearchView
        etSearch = searchView.findViewById(androidx.appcompat.R.id.search_src_text)
        val searchCloseButton = searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
        searchView.queryHint = "Pretrazi..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    newsAdapter.filterList(it)
                    if (newText.isEmpty()) {
                        binding.recyclerNews.doOnNextLayout {
                            binding.recyclerNews.scrollToPosition(0)
                        }
                    }
                }
                searchCloseButton.visibility = View.VISIBLE
                return false
            }
        })

        searchCloseButton.setOnClickListener {
            clearSearch()
        }
    }

    private fun loadNews() {
        viewModel.news.observe(viewLifecycleOwner){ news ->
            newsAdapter.differ.submitList(news)
            news?.let {
                newsAdapter.originalList.addAll(it)
            }
        }
    }

    private fun openFullNews() {
        newsAdapter.setOnItemClickListener { news->
            view?.findNavController()?.navigate(NewsFragmentDirections.actionNavigationNewsToFullNewsFragment(news))
        }
    }

    private fun initAdapter() {
        with(binding) {
            recyclerNews.apply {
                layoutManager = LinearLayoutManager(requireActivity())
                adapter = newsAdapter
            }
        }
    }

    private fun clearSearch() {
        etSearch.text.clear()
        searchView.apply {
            onActionViewCollapsed()
            clearFocus()
            isIconified = true
        }
        binding.toolbar.collapseActionView()
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
        newsAdapter.originalList.clear()
        _binding = null
    }
}