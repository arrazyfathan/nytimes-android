package com.arrazyfathan.nytimes.presentation.bookmark

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.arrazyfathan.nytimes.R
import com.arrazyfathan.nytimes.databinding.FragmentSavedArticleBinding
import com.arrazyfathan.nytimes.presentation.adapter.TopStoriesAdapter
import com.arrazyfathan.nytimes.utils.toJson
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BookmarkFragment : Fragment() {

    private val viewModel: BookmarkViewModel by viewModels()
    private var _binding: FragmentSavedArticleBinding? = null
    private val binding get() = _binding!!

    private lateinit var newsAdapter: TopStoriesAdapter

    /* Setup on swipe delete*/
    private val background = ColorDrawable()
    private val backgroundColor = Color.parseColor("#ba000d")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSavedArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        /*newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }

            findNavController().navigate(
                R.id.action_bookmarkFragment_to_articleDetailFragment,
                bundle
            )
        }*/

        /*val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                val deleteIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_delete)!!
                val intrinsicWidth = deleteIcon.intrinsicWidth + 30
                val intrinsicHeight = deleteIcon.intrinsicHeight + 30

                val itemView = viewHolder.itemView
                val itemHeight = itemView.bottom - itemView.top

                //draw red delete
                background.color = backgroundColor
                background.setBounds(
                    itemView.right + dX.toInt(),
                    itemView.top,
                    itemView.right,
                    itemView.bottom
                )
                background.draw(c)

                //calculate position of delete icon
                val iconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
                val iconMargin = (itemHeight - intrinsicHeight) / 2
                val iconLeft = itemView.right - iconMargin - intrinsicWidth
                val iconRight = itemView.right - iconMargin
                val iconBottom = iconTop + intrinsicHeight

                deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                deleteIcon.draw(c)
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]

                viewModel.deleteArticle(article)
                Snackbar.make(view, "Article deleted", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        viewModel.saveArticle(article)
                    }
                    show()
                }
            }

        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvBookmark)
        }

        viewModel.getSavedArticle().observe(viewLifecycleOwner) { articles ->
            newsAdapter.differ.submitList(articles)
        }*/

        observe()
    }

    private fun observe() {
        viewModel.getAllBookmarkedArticle().observe(viewLifecycleOwner) { articles ->
            binding.emptyText?.isVisible = articles.isEmpty()
            newsAdapter.differ.submitList(articles)
        }
    }

    private fun setupRecyclerView() {
        newsAdapter = TopStoriesAdapter { article ->
            val bundle = bundleOf("article" to article.toJson())
            findNavController().navigate(
                R.id.action_bookmarkFragment_to_articleDetailFragment,
                bundle,
            )
        }
        binding.rvBookmark.apply {
            adapter = newsAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}