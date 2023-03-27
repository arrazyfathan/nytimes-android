package com.arrazyfathan.nytimes.presentation.article

import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.arrazyfathan.nytimes.data.model.Article
import com.arrazyfathan.nytimes.databinding.FragmentArticleBinding
import com.arrazyfathan.nytimes.presentation.MainActivity
import com.arrazyfathan.nytimes.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class ArticleDetailFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private val args: ArticleDetailFragmentArgs by navArgs()

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!

    private val TAG = "ArticleFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        val article = args.article

        binding.webviewDetail.apply {
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
        }
    }

    private fun setupProgressBar() {
        binding.webviewDetail.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                return false
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                if (activity != null) {
                    binding.loadingProgressWebview.visibility = View.VISIBLE
                }
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                if (activity != null) {
                    try {
                        binding.loadingProgressWebview.visibility = View.INVISIBLE
                    } catch (e: NullPointerException) {
                        Log.d(TAG, e.message.toString())
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
