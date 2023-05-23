package com.learn.githubuserapp.presentation.ui.detail

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.learn.githubuserapp.R
import com.learn.githubuserapp.databinding.ActivityDetailBinding
import com.learn.githubuserapp.core.domain.model.User
import com.learn.githubuserapp.presentation.ui.detail.pager.SectionsPagerAdapter
import com.learn.githubuserapp.core.utils.BASE_SITE_URL
import com.learn.githubuserapp.core.utils.Result
import com.learn.githubuserapp.core.utils.collectLifecycleFlow
import com.learn.githubuserapp.core.utils.showToast
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModel()
    private var username: String? = null
    private var favUserEntity: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        username = intent.getStringExtra(EXTRA_USER)
        if (username != null) {
            viewModel.setUsername(username!!)
            viewModel.getUserDetail(username!!)
            viewModel.isFavorite(username!!)
        }

        setObservers()
        setupViewPager()

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
    }

    private fun setObservers() {
        collectLifecycleFlow(viewModel.isFavorite) {
            setFavoriteState(it)
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.user.collect { result ->
                    when (result) {
                        is Result.Loading -> showLoading(true)
                        is Result.Success -> {
                            showLoading(false)
                            showUserDetail(result.data)
                            Log.d("asdf", "setObservers: $favUserEntity")
                            favUserEntity = User(
                                username = result.data.username,
                                photoUrl = result.data.photoUrl
                            )
                            Log.d("asdf", "setObservers: $favUserEntity")
                        }
                        is Result.Error -> {
                            showLoading(false)
                            showToast(this@DetailActivity, result.error)
                        }
                    }
                }
            }
        }
    }

    private fun setFavoriteState(isFavorite: Int) {
        if (isFavorite < 1) {
            with(binding.fabAdd) {
                imageTintList = ColorStateList.valueOf(Color.WHITE)
                setOnClickListener {
                    isEnabled = false
                    imageTintList = ColorStateList.valueOf(Color.RED)
                    favUserEntity?.let {
                        viewModel.insertUser(it)
                        Log.d("asdf", "setFavoriteState: $favUserEntity")
                        showToast(this@DetailActivity, "User Added")
                    }

                    isEnabled = true
                }
            }
        } else {
            with(binding.fabAdd) {
                imageTintList = ColorStateList.valueOf(Color.RED)
                setOnClickListener {
                    isEnabled = false
                    imageTintList = ColorStateList.valueOf(Color.WHITE)
                    favUserEntity?.let {
                        Log.d("asdf", "setFavoriteState: $favUserEntity")
                        viewModel.deleteUser(it.username)
                    }
                    showToast(this@DetailActivity, "User Removed")
                    isEnabled = true
                }
            }
        }
    }

    private fun setupViewPager() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    private fun showUserDetail(data: User) {
        binding.detailContent.apply {
            tvName.text = data.name
            tvUsername.text = data.username
            tvFollowers.text = data.followers
            tvFollowing.text = data.following

            Glide.with(this@DetailActivity)
                .load(data.photoUrl)
                .into(ivUser)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_share) {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            if (username != null) {
                sendIntent.putExtra(Intent.EXTRA_TEXT, "$BASE_SITE_URL$username")
            }
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
        }
        return true
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}