package com.example.mynewsapp.paging

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.mynewsapp.R
import com.example.mynewsapp.models.Article

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val title: TextView
    private val poster: ImageView
    private val description: TextView

    init {
        title = itemView.findViewById(R.id.title)
        poster = itemView.findViewById(R.id.news_poster)
        description = itemView.findViewById(R.id.description)
    }

    fun bind(
        data: Article?,
        context: Context
    ) {
        title.text = data?.title ?: ""
        description.text = data?.description
        Glide.with(context)
            .load(data?.urlToImage)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transform(CenterCrop(), RoundedCorners(16))
            .error(AppCompatResources.getDrawable(context, R.drawable.error_placeholder))
            .into(poster)
    }
}