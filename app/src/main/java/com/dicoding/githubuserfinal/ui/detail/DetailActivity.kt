package com.dicoding.githubuserfinal.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.dicoding.githubuserfinal.R
import com.dicoding.githubuserfinal.data.Result
import com.dicoding.githubuserfinal.data.local.entity.UserEntity
import com.dicoding.githubuserfinal.data.remote.response.DetailResponse
import com.dicoding.githubuserfinal.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.dicoding.githubuserfinal.utils.ViewModelFactory

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var userEntity: UserEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("username").toString()
        supportActionBar?.title = "Detail User"


        val factory: ViewModelFactory = ViewModelFactory.getInstance(application)
        val viewModel: DetailViewModel by viewModels {
            factory
        }

        viewModel.getByUsername(username)

        viewModel.isExists.observe(this) {
            if (it != null) {
                binding.apply {
                    fabAdd.setImageResource(R.drawable.baseline_favorite_24)
                    fabAdd.setOnClickListener {
                        viewModel.deleteUser(userEntity)
                        Toast.makeText(
                            this@DetailActivity,
                            "$username dihapus dari favorite",
                            Toast.LENGTH_SHORT
                        ).show()
                        viewModel.getByUsername(username)
                    }
                }
            } else {
                binding.apply {
                    fabAdd.setImageResource(R.drawable.baseline_favorite_border_24)
                    fabAdd.setOnClickListener {
                        viewModel.insertUser(userEntity)
                        Toast.makeText(
                            this@DetailActivity,
                            "$username ditambahkan ke favorite",
                            Toast.LENGTH_SHORT
                        ).show()
                        viewModel.getByUsername(username)
                    }
                }
            }
        }

        getDetailUser(viewModel, username)
        sectionPagerSetting(username)
    }

    private fun getDetailUser(viewModel: DetailViewModel, username: String) {
        viewModel.getDetailUser(username).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    is Result.Success -> {
                        binding.progressBar.isVisible = false
                        setData(result.data)
                    }
                    is Result.Error -> {
                        binding.progressBar.isVisible = false
                        Toast.makeText(
                            this@DetailActivity,
                            "Terjadi kesalahan" + result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun setData(dataUser: DetailResponse) {
        userEntity = UserEntity(dataUser.login, dataUser.avatarUrl)

        Glide
            .with(this)
            .load(dataUser.avatarUrl)
            .circleCrop()
            .into(binding.ivDetail)

        binding.apply {
            tvDetaiName.text = dataUser.name
            tvDetailUsername.text = dataUser.login
            tvDetailFollowers.text =
                StringBuilder().append(dataUser.followers).append(" Followers")
            tvDetailFollowing.text =
                StringBuilder().append(dataUser.following).append(" Following")
        }
    }

    private fun sectionPagerSetting(username: String) {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, username)
        binding.viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(
            binding.tabs, binding.viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.followers, R.string.following)
    }
}