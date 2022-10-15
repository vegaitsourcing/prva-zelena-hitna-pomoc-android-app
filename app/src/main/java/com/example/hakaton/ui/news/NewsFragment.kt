package com.example.hakaton.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hakaton.databinding.FragmentNewsBinding
import com.example.hakaton.ui.news.adapter.NewsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NewsViewModel by viewModels()

    private val newsAdapter: NewsAdapter by lazy { NewsAdapter() }

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
        initAdapter()
        loadNews()
        openFullNews()
    }
    private fun loadNews() {
        viewModel.news.observe(viewLifecycleOwner){ news ->
            newsAdapter.differ.submitList(news)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}