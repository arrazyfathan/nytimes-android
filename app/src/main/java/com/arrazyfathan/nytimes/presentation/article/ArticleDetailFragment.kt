package com.arrazyfathan.nytimes.presentation.article

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.arrazyfathan.nytimes.core.domain.model.Article
import com.arrazyfathan.nytimes.databinding.FragmentArticleBinding
import com.arrazyfathan.nytimes.utils.format
import com.arrazyfathan.nytimes.utils.fromJson
import com.arrazyfathan.nytimes.utils.launchUrl
import com.arrazyfathan.nytimes.utils.toLocalDateTime
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleDetailFragment : Fragment() {

    private val viewModel: ArticleDetailViewModel by viewModels()
    private val args: ArticleDetailFragmentArgs by navArgs()
    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val TAG = "ArticleFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val article = fromJson<Article>(args.article)
        setupView(article)

        /*binding.webviewDetail.apply {
            webViewClient = WebViewClient()
            article.url?.let { loadUrl(it) }
            settings.userAgentString = "Android"
            settings.javaScriptEnabled = true
        }

        setupProgressBar()

        if (!article.isSaved) {
            binding.fabDetail.visibility = View.VISIBLE
            binding.webviewDetail.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
                if (scrollY > oldScrollY && scrollY > 0) {
                    binding.fabDetail.hide()
                }
                if (scrollY < oldScrollY) {
                    binding.fabDetail.show()
                }
            }
        } else {
            binding.fabDetail.visibility = View.INVISIBLE
        }

        binding.sectionDetail.text =
            article.subsection?.ifEmpty { article.section }?.replaceFirstChar { it.uppercase() }

        binding.fabDetail.setOnClickListener {

            val savedArticle = Article(
                id = article.url!!,
                url = article.url,
                byline = article.byline,
                multimedia = article.multimedia,
                published_date = article.published_date,
                section = article.section,
                short_url = article.short_url,
                subsection = article.subsection,
                title = article.title,
                description = article.description,
                isSaved = true,
            )
            viewModel.saveArticle(savedArticle)
            Snackbar.make(view, "Article saved", Snackbar.LENGTH_SHORT).show()
        }

        binding.btnShare.setOnClickListener {
            val shareIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT,
                    """
                    ${article.title}
                    ${article.description}
                    ${article.short_url}
                    """.trimIndent()
                )
                type = "text/plain"
            }
            startActivity(shareIntent)
        }*/
    }

    @RequiresApi(Build.VERSION_CODES.O)
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
    }
}
