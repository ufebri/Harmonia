package id.daydream.harmonia.presentation.home.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import febri.uray.bedboy.core.domain.model.News
import febri.uray.bedboy.core.util.DateUtil
import id.daydream.harmonia.databinding.ItemNewsBinding

class NewsViewHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(mData: News, onClick: (News) -> Unit) {
        binding.apply {
            Glide.with(root).load(mData.urlToImage).into(ivCoverNews)
            tvTitleNews.text = mData.title
            tvDateSourceNews.text = String.format("%s - %s", DateUtil.formatDate(mData.publishedAt), mData.source)

            root.setOnClickListener { onClick(mData) }
        }
    }
}