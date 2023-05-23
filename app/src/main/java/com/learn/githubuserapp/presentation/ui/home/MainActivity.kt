package com.learn.githubuserapp.presentation.ui.home

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.learn.githubuserapp.R
import com.learn.githubuserapp.databinding.ActivityMainBinding
import com.learn.githubuserapp.core.domain.model.User
import com.learn.githubuserapp.core.ui.UserAdapter
import com.learn.githubuserapp.presentation.ui.detail.DetailActivity
import com.learn.githubuserapp.presentation.ui.setting.SettingActivity
import com.learn.githubuserapp.core.utils.Result
import com.learn.githubuserapp.core.utils.showToast
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupTheme()
        setObservers()
    }

    private fun setObservers() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.foundUsers.collect { result ->
                    when (result) {
                        is Result.Loading -> showLoading(true)
                        is Result.Success -> {
                            showLoading(false)
                            binding.tvEmpty.visibility = if (result.data.isEmpty()) View.VISIBLE else View.GONE
                            showRecycler(result.data)
                        }
                        is Result.Error -> {
                            showLoading(false)
                            showToast(this@MainActivity, result.error)
                        }
                    }
                }
            }
        }
    }

    private fun setupTheme() {
        viewModel.getThemeSettings().observe(this) { isDarkMode ->
            AppCompatDelegate.setDefaultNightMode(
                if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
        }
    }

    private fun findUser(username: String) {
        viewModel.findUser(username)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showRecycler(listUser: List<User>) {
        binding.rvUsers.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = UserAdapter(listUser) { user ->
                val navigateToDetail = Intent(this@MainActivity, DetailActivity::class.java)
                navigateToDetail.putExtra(DetailActivity.EXTRA_USER, user.username)
                startActivity(navigateToDetail)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                findUser(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting -> {
                val navigateToSetting = Intent(this, SettingActivity::class.java)
                startActivity(navigateToSetting)
            }
            R.id.favorite -> {
                try {
                    val uri = Uri.parse("githubuserapp://favorite")
                    startActivity(Intent(Intent.ACTION_VIEW, uri))
                } catch (e: Exception) {
                    showToast(this, "Module not installed")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}