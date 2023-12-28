package com.dev.newsapp.ui.article

import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev.newsapp.R
import com.dev.newsapp.data.model.entity.Article
import com.dev.newsapp.databinding.ItemArticlesBinding
import com.dev.newsapp.utils.convertDate

class ArticleAdapter(val callback: (url: String) -> Unit) : PagingDataAdapter<Article, ArticleAdapter.ArticleViewHolder>(ARTICLE_COMPARATOR) {

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        if(article != null){
            Glide.with(holder.itemView.context)
                .load(article.urlToImage)
                .placeholder(R.drawable.baseline_broken_image_24)
                .into(holder.binding.imgNews)

            holder.binding.txvAuthor.text = if(article.author.isNullOrEmpty()){
                article.source.name
            }else{
                article.author
            }
            holder.binding.txvTitle.text = article.title
            holder.binding.txvDate.text = article.publishedAt?.let { convertDate(it) }

            holder.binding.root.setOnClickListener {
                article.url?.let { it1 -> callback(it1) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticlesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ArticleViewHolder(binding)
    }
    class ArticleViewHolder(val binding: ItemArticlesBinding): RecyclerView.ViewHolder(binding.root){}
    companion object{
        private val ARTICLE_COMPARATOR = object : DiffUtil.ItemCallback<Article>(){
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem == newItem

        }
    }
}