package com.arrazyfathan.nytimes.presentation.article

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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.arrazyfathan.nytimes.core.domain.model.Article
import com.arrazyfathan.nytimes.designsystem.theme.NyTimesTheme
import com.arrazyfathan.nytimes.utils.fromJson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleDetailFragment : Fragment() {

    private val viewModel: ArticleDetailViewModel by viewModels()

    private val args: ArticleDetailFragmentArgs by navArgs()

    private val article by lazy {
        fromJson<Article>(args.article)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                NyTimesTheme {
                    ArticleDetailScreen(
                        viewModel,
                        article,
                        modifier = Modifier.background(Color.White).fillMaxSize(),
                    )
                }
            }
        }
    }
}
