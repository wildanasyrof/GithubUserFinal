package com.dicoding.githubuserfinal.ui.main

import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuserfinal.R
import com.dicoding.githubuserfinal.databinding.ActivityMainBinding
import com.dicoding.githubuserfinal.ui.favorite.FavoriteActivity
import com.dicoding.githubuserfinal.ui.settings.SettingsActivity
import com.dicoding.githubuserfinal.utils.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)

        val viewModels: MainViewModel by viewModels { factory }
        viewModel = viewModels

        getTheme(viewModel)
        recyclerViewSetting()
        getSearchUser()
    }

    private fun getTheme(viewModel: MainViewModel) {
        viewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun getSearchUser(username: String = "a") {
        viewModel.getSearchUser(username).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is com.dicoding.githubuserfinal.data.Result.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    is com.dicoding.githubuserfinal.data.Result.Success -> {
                        binding.progressBar.isVisible = false
                        val adapter = UserAdapter(result.data)
                        binding.rvUser.adapter = adapter
                    }
                    is com.dicoding.githubuserfinal.data.Result.Error -> {
                        binding.progressBar.isVisible = false
                        Toast.makeText(
                            this@MainActivity,
                            "Terjadi kesalahan" + result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun recyclerViewSetting() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_option, menu)

        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                getSearchUser(query)
                searchView.clearFocus()
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                val i = Intent(this, FavoriteActivity::class.java)
                startActivity(i)
            }
            R.id.settings -> {
                val i = Intent(this, SettingsActivity::class.java)
                startActivity(i)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}