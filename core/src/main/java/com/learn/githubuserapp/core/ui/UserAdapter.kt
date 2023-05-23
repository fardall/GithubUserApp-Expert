package com.learn.githubuserapp.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.learn.githubuserapp.core.databinding.ItemUserBinding
import com.learn.githubuserapp.core.domain.model.User

class UserAdapter(private val listUser: List<User>, private val listener: (User) -> Unit) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(listUser[position], listener)
    }

    override fun getItemCount(): Int = listUser.size

    class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User, listener: (User) -> Unit) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(user.photoUrl)
                    .into(ivUser)
                tvName.text = user.username
                itemView.setOnClickListener { listener(user) }
            }
        }
    }
}