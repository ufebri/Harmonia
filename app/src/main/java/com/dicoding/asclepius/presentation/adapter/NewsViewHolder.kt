package com.dicoding.asclepius.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.asclepius.databinding.ItemNewsBinding
import febri.uray.bedboy.core.domain.model.News

class NewsViewHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(mData: News, onClick: (News) -> Unit) {
        binding.apply {
            Glide.with(root).load(mData.urlToImage).into(ivCoverNews)
            tvTitleNews.text = mData.title
            tvDateSourceNews.text = String.format("%s - %s", mData.publishedAt, mData.source)

            root.setOnClickListener { onClick(mData) }
        }
    }
}