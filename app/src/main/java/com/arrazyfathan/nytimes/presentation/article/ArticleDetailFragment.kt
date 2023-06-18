package com.arrazyfathan.nytimes.presentation.article

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
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

    companion object {
        private const val TAG = "ArticleFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        /*_binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root*/
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // setupView(article)
    }

    /*@RequiresApi(Build.VERSION_CODES.O)
    private fun setupView(article: Article) = with(binding) {
        val formattedDate = article.publishedDate.toLocalDateTime().format("EEEE, MMMM dd yyyy")
        imgDetailNews.load(article.getMainImage()) {
            crossfade(true)
        }
        sectionDetail.text = article.section.replaceFirstChar { it.uppercase() }
        newsTitle.text = article.title
        author.text = article.byline
        date.text = formattedDate
        captions.text = article.description
        captionImage.text = article.getImageCaption()
        imageCopyright.text = article.getImageCopyright()
        thumbnails.load(article.getImage())
        titleOpenLinks.text = article.title

        btnShare.setOnClickListener {
            val shareIntent: Intent = Intent().apply {
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
            startActivity(shareIntent)
        }

        btnOpenLinks.setOnClickListener {
            requireContext().launchUrl(article.shortUrl)
        }

        viewModel.checkArticleIsBookmarked(article.shortUrl).observe(viewLifecycleOwner) {
            fabDetail.isVisible = !it
        }

        fabDetail.setOnClickListener {
            viewModel.bookmarkArticle(
                Article(
                    articleId = article.shortUrl,
                    description = article.description,
                    byline = article.byline,
                    createdDate = article.createdDate,
                    itemType = article.itemType,
                    kicker = article.kicker,
                    materialTypeFacet = article.materialTypeFacet,
                    multimedia = article.multimedia,
                    publishedDate = article.publishedDate,
                    section = article.section,
                    shortUrl = article.shortUrl,
                    subsection = article.subsection,
                    title = article.title,
                    updatedDate = article.updatedDate,
                    uri = article.uri,
                    url = article.url,
                    isBookmarked = true,
                ),
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }*/
}
