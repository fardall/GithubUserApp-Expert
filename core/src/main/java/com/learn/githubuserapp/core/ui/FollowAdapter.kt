package com.learn.githubuserapp.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.learn.githubuserapp.core.databinding.ItemFollowBinding
import com.learn.githubuserapp.core.domain.model.User

class FollowAdapter(private val listFollow: List<User>) : RecyclerView.Adapter<FollowAdapter.FollowViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowViewHolder {
        val binding = ItemFollowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowViewHolder, position: Int) {
        holder.bind(listFollow[position])
    }

    override fun getItemCount(): Int = listFollow.size

    class FollowViewHolder(private val binding: ItemFollowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(follow: User) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(follow.photoUrl)
                    .into(ivUser)

                tvUsername.text = follow.username
            }
        }
    }
}