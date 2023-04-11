package com.arrazyfathan.nytimes.presentation.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arrazyfathan.nytimes.R
import com.arrazyfathan.nytimes.core.domain.model.Article
import com.arrazyfathan.nytimes.core.utils.Utils
import com.arrazyfathan.nytimes.databinding.ItemArticleBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class TopStoriesAdapter(val onClick: (Article) -> Unit) :
    RecyclerView.Adapter<TopStoriesAdapter.ArticleViewHolder>() {
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
                .load(if (article.multimedia.isEmpty()) R.drawable.placeholder else article.getImage())
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(imageViewArticle)

            val sectionText = article.subsection.ifEmpty { article.section }
            val timeAgo = article.publishedDate.let { Utils.dateTimeAgo(it) }

            section.text = sectionText.replaceFirstChar { it.uppercase() }
            tvTitle.text = article.title
            tvByline.text = article.byline.ifEmpty { "Unknown" }
            tvAbstract.text = article.abstract
            tvPublished.text = timeAgo

            articleCard.setOnClickListener {
                onClick(article)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}
