package com.example.newsfeedapp.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsfeedapp.R
import com.example.newsfeedapp.model.Articles
import com.example.newsfeedapp.model.RecyclerOnClick
import java.util.*


class NewsAdapter(private var context: Context, private var articleList: ArrayList<Articles>,
        private var recyclerviewOnClick: RecyclerOnClick)
    :RecyclerView.Adapter<NewsAdapter.NewsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_news_view, null)
        return NewsHolder(view)
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        var article = articleList[position]
        holder.tvAuthor.text = "Author : " + article.author
        holder.tvLink.text = article.url
        holder.tvTitle.text = "Title : " + article.title
        holder.tvPublishedDate.text = "Published At : " + article.publishedAt

        holder.tvLink.setOnClickListener {
            val uri: String = java.lang.String.format(
                Locale.ENGLISH,article.url)
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            context.startActivity(intent)
        }

        Glide.with(context).load(article.urlToImage)
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_image)
            .into(holder.ivNewsFeed)

        holder.cardView.setOnClickListener {
            recyclerviewOnClick.onClick(articleList[position])
        }
    }

    override fun getItemCount(): Int {
       return articleList.size
    }

    class NewsHolder(view: View):RecyclerView.ViewHolder(view){
        var tvAuthor = view.findViewById<TextView>(R.id.tvAuthorName)
        var tvLink = view.findViewById<TextView>(R.id.tvNewsLink)
        var tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        var tvPublishedDate = view.findViewById<TextView>(R.id.tvPublishedDate)
        var ivNewsFeed = view.findViewById<ImageView>(R.id.ivNewsFeedImage)
        var cardView = view.findViewById<CardView>(R.id.card_view)
    }
    fun removeAt(index: Int) {
        articleList.removeAt(index)
        notifyItemRemoved(index)
    }
}