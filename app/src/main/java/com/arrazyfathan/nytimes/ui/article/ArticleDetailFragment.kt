package com.arrazyfathan.nytimes.ui.article

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.arrazyfathan.nytimes.databinding.FragmentArticleBinding
import com.arrazyfathan.nytimes.ui.MainActivity
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        val article = args.article

        binding.webviewDetail.apply {
            webViewClient = WebViewClient()
            article.url?.let { loadUrl(it) }
            settings.userAgentString = "Android"
        }

        setupProgressBar()

        binding.sectionDetail.text =
            article.subsection?.ifEmpty { article.section }?.replaceFirstChar { it.uppercase() }

        binding.fabDetail.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(view, "Article saved", Snackbar.LENGTH_SHORT).show()
        }

    }

    private fun setupProgressBar() {
        binding.webviewDetail.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                return false
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                binding.loadingProgressWebview.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                try {
                    binding.loadingProgressWebview.visibility = View.INVISIBLE
                } catch (e: NullPointerException) {
                    Log.d(TAG, e.message.toString())
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}