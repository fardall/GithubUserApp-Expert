package com.learn.githubuserapp.favorite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.learn.githubuserapp.databinding.ActivityFavoriteBinding
import com.learn.githubuserapp.core.domain.model.User
import com.learn.githubuserapp.presentation.ui.detail.DetailActivity
import com.learn.githubuserapp.core.ui.UserAdapter
import com.learn.githubuserapp.core.utils.collectLifecycleFlow
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val viewModel: FavoriteViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadKoinModules(favorite)
        viewModel.getAllUsers()
        setObservers()
    }

    private fun setObservers() {
        collectLifecycleFlow(viewModel.favUsers) { favUsers ->
            Log.d("asdf", "setObservers: $favUsers")
            binding.tvEmpty.visibility = if (favUsers.isEmpty()) View.VISIBLE else View.GONE
            showRecycler(favUsers)
        }
    }

    private fun showRecycler(favUsers: List<User>) {
        binding.rvFavUsers.apply {
            layoutManager = GridLayoutManager(this@FavoriteActivity, 2)
            setHasFixedSize(true)
            adapter = UserAdapter(favUsers) { user ->
                val toDetail = Intent(this@FavoriteActivity, DetailActivity::class.java)
                toDetail.putExtra(DetailActivity.EXTRA_USER, user.username)
                startActivity(toDetail)
            }
        }
    }
}