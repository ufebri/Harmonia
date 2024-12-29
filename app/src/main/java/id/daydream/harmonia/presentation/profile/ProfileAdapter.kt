package id.daydream.harmonia.presentation.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import id.daydream.harmonia.databinding.ItemProfileBinding

class ProfileAdapter(private val onClick: (Int) -> Unit) :
    ListAdapter<Pair<Int, String>, ProfileViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        return ProfileViewHolder(
            ItemProfileBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        holder.bind(getItem(position), position == 2, onClick)
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Pair<Int, String>>() {
            override fun areItemsTheSame(
                oldItem: Pair<Int, String>,
                newItem: Pair<Int, String>
            ): Boolean = oldItem.first == newItem.first

            override fun areContentsTheSame(
                oldItem: Pair<Int, String>,
                newItem: Pair<Int, String>
            ): Boolean = oldItem == newItem

        }
    }
}