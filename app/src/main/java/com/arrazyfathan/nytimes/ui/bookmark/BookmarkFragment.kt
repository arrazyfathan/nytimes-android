package com.arrazyfathan.nytimes.ui.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.arrazyfathan.nytimes.R
import com.arrazyfathan.nytimes.adapter.TopStoriesAdapter
import com.arrazyfathan.nytimes.databinding.FragmentSavedArticleBinding
import com.arrazyfathan.nytimes.ui.MainActivity
import com.arrazyfathan.nytimes.viewmodel.MainViewModel

class BookmarkFragment : Fragment() {

    private var _binding: FragmentSavedArticleBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel
    private lateinit var newsAdapter: TopStoriesAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSavedArticleBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }

            findNavController().navigate(
                R.id.action_bookmarkFragment_to_articleDetailFragment,
                bundle
            )
        }

        viewModel.getSavedArticle().observe(viewLifecycleOwner) { articles ->
            newsAdapter.differ.submitList(articles)
        }
    }

    private fun setupRecyclerView() {
        newsAdapter = TopStoriesAdapter()
        binding.rvBookmark.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}