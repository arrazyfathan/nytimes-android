package com.arrazyfathan.nytimes.presentation.topstories

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.arrazyfathan.nytimes.R
import com.arrazyfathan.nytimes.adapter.TopStoriesAdapter
import com.arrazyfathan.nytimes.core.data.source.Resource
import com.arrazyfathan.nytimes.core.utils.Resources
import com.arrazyfathan.nytimes.core.utils.toast
import com.arrazyfathan.nytimes.databinding.FragmentTopStoriesBinding
import com.arrazyfathan.nytimes.presentation.MainActivity
import com.arrazyfathan.nytimes.viewmodel.MainViewModel
import com.arrazyfathan.nytimes.viewmodel.TryingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopStoriesFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private val tryViewModel: TryingViewModel by viewModels()
    private lateinit var topStoriesAdapter: TopStoriesAdapter

    private var _binding: FragmentTopStoriesBinding? = null
    private val binding get() = _binding!!

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    val TAG = "TopStoriesFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentTopStoriesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()

        /*viewModel.topStories.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resources.Success -> {
                    hideProgressBar()
                    hideNoInternet()
                    changeColorBackground(R.color.bg_gray)
                    response.data?.let { topStoriesResponse ->
                        topStoriesAdapter.differ.submitList(topStoriesResponse.results.toList())
                    }
                }
                is Resources.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.d(TAG, "Error : $message")
                        changeColorBackground(R.color.white)
                        showNoInternet()
                        Toast.makeText(activity, "An error: $message", Toast.LENGTH_SHORT).show()
                    }
                }
                is Resources.Loading -> {
                    showProgressBar()
                }
            }
        }*/

        binding.btnRetry.setOnClickListener {
            viewModel.getTopStories()
            hideNoInternet()
        }

        /*topStoriesAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }

            findNavController().navigate(
                R.id.action_topStoriesFragment_to_articleDetailFragment,
                bundle,
            )
        }*/

        swipeRefreshLayout = binding.swipeRefresh
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getTopStories()
            hideNoInternet()
            swipeRefreshLayout.isRefreshing = false
        }

        binding.btnBookmarks.setOnClickListener {
            findNavController().navigate(
                R.id.action_topStoriesFragment_to_bookmarkFragment,
            )
        }

        tryViewModel.topStories.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                    hideProgressBar()
                    changeColorBackground(R.color.white)
                    showNoInternet()
                    toast(it.message!!)
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Success -> {
                    hideProgressBar()
                    hideNoInternet()
                    changeColorBackground(R.color.bg_gray)
                    topStoriesAdapter.differ.submitList(it.data)
                }
            }
        }
    }

    private fun changeColorBackground(color: Int) {
        binding.topStoriesLayout.background =
            ContextCompat.getDrawable(requireContext(), color)
    }

    private fun setupRecyclerView() {
        topStoriesAdapter = TopStoriesAdapter()
        binding.rvTopStories.apply {
            adapter = topStoriesAdapter
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
