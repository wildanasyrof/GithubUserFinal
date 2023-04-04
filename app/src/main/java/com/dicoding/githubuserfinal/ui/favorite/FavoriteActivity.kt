package com.dicoding.githubuserfinal.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuserfinal.databinding.ActivityFavoriteBinding
import com.dicoding.githubuserfinal.utils.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: FavoriteViewModel by viewModels { factory }
        supportActionBar?.title = "Favorite User"

        viewModel.listUser.observe(this){dataUser ->
            adapter.setData(dataUser)
        }

        recyclerViewSetting()
    }

    private fun recyclerViewSetting(){
        adapter = FavoriteAdapter(mutableListOf())

        binding.apply {
            rvFavorite.setHasFixedSize(true)
            rvFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFavorite.adapter = adapter
        }
    }
}