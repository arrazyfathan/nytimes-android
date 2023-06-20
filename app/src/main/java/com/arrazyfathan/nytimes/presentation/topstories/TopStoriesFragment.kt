package com.arrazyfathan.nytimes.presentation.topstories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.arrazyfathan.nytimes.R
import com.arrazyfathan.nytimes.core.domain.model.Article
import com.arrazyfathan.nytimes.databinding.FragmentTopStoriesBinding
import com.arrazyfathan.nytimes.designsystem.theme.NyTimesTheme
import com.arrazyfathan.nytimes.presentation.adapter.TopStoriesAdapter
import com.arrazyfathan.nytimes.utils.toJson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopStoriesFragment : Fragment() {

    private val viewModel: TopStoriesViewModel by viewModels()
    private lateinit var topStoriesAdapter: TopStoriesAdapter
    private var _binding: FragmentTopStoriesBinding? = null
    private val binding get() = _binding!!
    private var currentSection = DEFAULT_SECTION
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    companion object {
        private const val DEFAULT_SECTION = "home"
        private const val TAG = "TopStoriesFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        /*_binding = FragmentTopStoriesBinding.inflate(inflater, container, false)
        return binding.root*/
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                NyTimesTheme {
                    HomeScreen(
                        viewModel,
                        onBookmarkClicked = {
                            findNavController().navigate(
                                R.id.action_topStoriesFragment_to_bookmarkFragment,
                            )
                        },
                        sections,
                        onItemSelected = { toDetailArticle(it) },
                    )
                }
            }
        }
    }

    private fun toDetailArticle(article: Article) {
        val bundle = bundleOf("article" to article.toJson())
        findNavController().navigate(
            R.id.action_topStoriesFragment_to_articleDetailFragment,
            bundle,
        )
    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupView()
        requestTopStories(DEFAULT_SECTION)
        observe()
    }

    private fun requestTopStories(section: String) {
        viewModel.getTopStories(section)
    }

    private fun setupView() = with(binding) {
        btnRetry.setOnClickListener {
            requestTopStories(currentSection)
            hideNoInternet()
        }

        swipeRefreshLayout = swipeRefresh
        swipeRefresh.setOnRefreshListener {
            requestTopStories(currentSection)
            hideNoInternet()
            swipeRefreshLayout.isRefreshing = false
        }

        btnBookmarks.setOnClickListener {
            findNavController().navigate(
                R.id.action_topStoriesFragment_to_bookmarkFragment,
            )
        }

        chipGroup.setOnCheckedChangeListener { _, checkedId ->
            val selectedChip = activity?.findViewById<Chip>(checkedId)
            requestTopStories(selectedChip?.text.toString().lowercase())
            currentSection = selectedChip?.text.toString().lowercase()
        }

        topBar.setOnClickListener {
            Logger.d("Clicked")
        }
    }

    private fun observe() {
        viewModel.topStories.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                    hideProgressBar()
                    handleError(it.message!!)
                }
                is Resource.Loading -> {
                    binding.rvTopStories.visibility = View.GONE
                    showProgressBar()
                }
                is Resource.Success -> {
                    hideProgressBar()
                    hideNoInternet()
                    binding.rvTopStories.visibility = View.VISIBLE
                    topStoriesAdapter.differ.submitList(it.data?.filter { data -> data.title.isNotBlank() })
                }
            }
        }
    }

    private fun handleError(message: String) {
        when (message) {
            MessageResult.NO_CONNECTION -> showNoInternet()
            else -> toast(message)
        }
    }

    private fun setupRecyclerView() {
        topStoriesAdapter = TopStoriesAdapter { article ->
            val bundle = bundleOf("article" to article.toJson())
            findNavController().navigate(
                R.id.action_topStoriesFragment_to_articleDetailFragment,
                bundle,
            )
        }
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
    }*/
}
