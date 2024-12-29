package id.daydream.harmonia.presentation.profile

import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import id.daydream.harmonia.databinding.ItemProfileBinding

class ProfileViewHolder(private val binding: ItemProfileBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(mData: Pair<Int, String>, isTheLastIndex: Boolean, onClick: (Int) -> Unit) {
        binding.apply {

            tvActionName.text = mData.second
            viewSeparator.isGone = isTheLastIndex
            root.setOnClickListener { onClick(mData.first) }
        }
    }
}
