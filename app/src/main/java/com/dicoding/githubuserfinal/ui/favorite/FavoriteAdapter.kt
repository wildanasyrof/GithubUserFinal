package com.dicoding.githubuserfinal.ui.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubuserfinal.data.local.entity.UserEntity
import com.dicoding.githubuserfinal.databinding.ItemUserBinding
import com.dicoding.githubuserfinal.ui.detail.DetailActivity

class FavoriteAdapter(private var users: List<UserEntity>) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    class ViewHolder(private var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserEntity) {
            Glide
                .with(itemView.context)
                .load(user.avatarUrl)
                .circleCrop()
                .into(binding.itemPhoto)
            binding.tvName.text = user.username
            itemView.setOnClickListener {
                val i = Intent(itemView.context, DetailActivity::class.java)
                i.putExtra("username", user.username)
                itemView.context.startActivity(i)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        holder.bind(user)
    }

    fun setData(newUser: List<UserEntity>) {
        val diffUtil = diffCallback(this.users, newUser)
        val diffResult = DiffUtil.calculateDiff(diffUtil)

        this.users = newUser
        diffResult.dispatchUpdatesTo(this)
    }

    private fun diffCallback(
        oldList: List<UserEntity>,
        newList: List<UserEntity>
    ) = object : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].username == newList[newItemPosition].username

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] === newList[newItemPosition]
    }
}