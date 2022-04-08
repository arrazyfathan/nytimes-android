package com.arrazyfathan.nytimes.adapter

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arrazyfathan.nytimes.R
import com.arrazyfathan.nytimes.data.model.Article
import com.arrazyfathan.nytimes.databinding.ItemArticleBinding
import com.arrazyfathan.nytimes.utils.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.util.Util

class TopStoriesAdapter : RecyclerView.Adapter<TopStoriesAdapter.ArticleViewHolder>() {

    private var onItemClickListener: ((Article) -> Unit)? = null

    inner class ArticleViewHolder(val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]

        holder.binding.apply {
            Glide.with(this.root)
                .load(if (article.multimedia == null) R.drawable.placeholder else article.multimedia[1].url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageViewArticle)

            val sectionText = article.subsection.ifEmpty { article.section }
            val timeAgo = Utils.dateTimeAgo(article.published_date)

            section.text = sectionText.replaceFirstChar { it.uppercase() }
            tvTitle.text = article.title
            tvByline.text = article.byline.ifEmpty { "Unknown" }
            tvAbstract.text = article.abstract
            tvPublished.text = timeAgo

            articleCard.setOnClickListener {
                onItemClickListener?.let { it(article) }
            }
        }


    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
}