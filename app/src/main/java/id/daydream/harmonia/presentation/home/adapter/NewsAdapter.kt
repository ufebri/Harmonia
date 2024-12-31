package id.daydream.harmonia.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import febri.uray.bedboy.core.domain.model.News
import id.daydream.harmonia.databinding.ItemNewsBinding

class NewsAdapter(private val onClick: (News) -> Unit, private val length: (Int) -> Unit) :
    PagingDataAdapter<News, NewsViewHolder>(DIFF_CALLBACK) {

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val mData = getItem(position)
        if (mData != null)
            itemCount.let { holder.bind(mData, onClick) }
        else
            itemCount.let { length }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            ItemNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<News>() {
            override fun areItemsTheSame(
                oldItem: News,
                newItem: News
            ): Boolean {
                return oldItem.publishedAt == newItem.publishedAt
            }

            override fun areContentsTheSame(
                oldItem: News,
                newItem: News
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}