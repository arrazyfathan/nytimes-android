package com.arrazyfathan.nytimes.ui.topstories

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.arrazyfathan.nytimes.R
import com.arrazyfathan.nytimes.adapter.TopStoriesAdapter
import com.arrazyfathan.nytimes.databinding.FragmentTopStoriesBinding
import com.arrazyfathan.nytimes.ui.MainActivity
import com.arrazyfathan.nytimes.utils.Resources
import com.arrazyfathan.nytimes.viewmodel.MainViewModel

class TopStoriesFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var topStoriesAdapter: TopStoriesAdapter

    private var _binding: FragmentTopStoriesBinding? = null
    private val binding get() = _binding!!

    val TAG = "TopStoriesFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTopStoriesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()

        viewModel.topStories.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resources.Success -> {
                    hideProgressBar()
                    hideNoInternet()
                    response.data?.let { topStoriesResponse ->
                        topStoriesAdapter.differ.submitList(topStoriesResponse.results.toList())
                    }
                }
                is Resources.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.d(TAG, "Error : $message")
                        showNoInternet()
                        Toast.makeText(activity, "An error: $message", Toast.LENGTH_SHORT).show()
                    }
                }
                is Resources.Loading -> {
                    showProgressBar()
                }
            }

        }

        binding.btnRetry.setOnClickListener {
            viewModel.getTopStories()
            hideProgressBar()
            changeColorBackground()
        }

    }

    private fun changeColorBackground() {
       // binding.topStoriesLayout.background = ContextCompat.getColor(requireContext(), R.color.white)
    }

    private fun setupRecyclerView() {
        topStoriesAdapter = TopStoriesAdapter()
        binding.rvTopStories.apply {
            adapter = topStoriesAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun hideProgressBar() {
        binding.loadingProgress.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.loadingProgress.visibility = View.VISIBLE
    }

    private fun showNoInternet() {
        binding.apply {
            noInternet.visibility = View.VISIBLE
            tvNoInternet.visibility = View.VISIBLE
            btnRetry.visibility = View.VISIBLE
            rvTopStories.visibility = View.INVISIBLE
        }
    }

    private fun hideNoInternet() {
        binding.apply {
            noInternet.visibility = View.INVISIBLE
            tvNoInternet.visibility = View.INVISIBLE
            btnRetry.visibility = View.INVISIBLE
            rvTopStories.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}