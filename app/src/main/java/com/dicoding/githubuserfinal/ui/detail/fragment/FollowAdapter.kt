package com.dicoding.githubuserfinal.ui.detail.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubuserfinal.data.remote.response.ItemsUser
import com.dicoding.githubuserfinal.databinding.ItemUserBinding
import com.dicoding.githubuserfinal.ui.detail.DetailActivity

class FollowAdapter(private val listUser: List<ItemsUser>) : RecyclerView.Adapter<FollowAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listUser[position]
        Glide
            .with(holder.itemView.context)
            .load(data.avatarUrl)
            .circleCrop()
            .into(holder.binding.itemPhoto)
        holder.binding.tvName.text = data.login

        holder.itemView.setOnClickListener {
            val i = Intent(holder.itemView.context, DetailActivity::class.java)
            i.putExtra("username", data.login)
            holder.itemView.context.startActivity(i)
        }
    }

}