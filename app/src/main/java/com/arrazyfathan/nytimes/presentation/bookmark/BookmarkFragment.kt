package com.arrazyfathan.nytimes.presentation.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.arrazyfathan.nytimes.R
import com.arrazyfathan.nytimes.core.domain.model.Article
import com.arrazyfathan.nytimes.utils.toJson
import com.google.accompanist.themeadapter.material.MdcTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarkFragment : Fragment() {

    private val viewModel: BookmarkViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            // Dispose when the view LifecycleOwner is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MdcTheme {
                    BookmarkScreen(
                        viewModel,
                        modifier = Modifier.background(Color.White).fillMaxSize(),
                        onClicked = { navigateToDetail(it) },
                    )
                }
            }
        }
    }

    private fun navigateToDetail(article: Article) {
        val bundle = bundleOf("article" to article.toJson())
        findNavController().navigate(
            R.id.action_bookmarkFragment_to_articleDetailFragment,
            bundle,
        )
    }
}
