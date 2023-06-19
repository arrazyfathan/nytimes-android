package com.arrazyfathan.nytimes.presentation.article

import android.content.Intent
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
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.arrazyfathan.nytimes.core.domain.model.Article
import com.arrazyfathan.nytimes.designsystem.theme.NyTimesTheme
import com.arrazyfathan.nytimes.utils.fromJson
import com.arrazyfathan.nytimes.utils.launchUrl
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
                        onShareClicked = { openShareIntent() },
                        openLink = { openLink() },
                        modifier = Modifier.background(Color.White).fillMaxSize(),
                    )
                }
            }
        }
    }

    private fun openLink() {
        requireContext().launchUrl(article.shortUrl)
    }

    private fun openShareIntent() {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                """
                ${article.title}
                ${article.description}
                ${article.shortUrl}
                """.trimIndent(),
            )
            type = "text/plain"
        }
        ContextCompat.startActivity(requireContext(), shareIntent, null)
    }
}
