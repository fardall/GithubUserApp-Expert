package com.learn.githubuserapp.presentation.ui.detail.pager

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.learn.githubuserapp.databinding.FragmentFollowersBinding
import com.learn.githubuserapp.core.domain.model.User
import com.learn.githubuserapp.core.ui.FollowAdapter
import com.learn.githubuserapp.presentation.ui.detail.DetailViewModel
import com.learn.githubuserapp.core.utils.Result
import com.learn.githubuserapp.core.utils.TAG
import com.learn.githubuserapp.core.utils.collectLifecycleFlow
import com.learn.githubuserapp.core.utils.showToast
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class FollowersFragment : Fragment() {
    private lateinit var binding: FragmentFollowersBinding
    private val viewModel: DetailViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.username.observe(viewLifecycleOwner) { username ->
            Log.d(TAG, "onViewCreated: $username is null")
            if (username != null) {
                viewModel.getUserFollowers(username)
            }
        }

        setObservers()
    }

    private fun setObservers() {
        collectLifecycleFlow(viewModel.userFollowers) { result ->
            when (result) {
                is Result.Loading -> showLoading(true)
                is Result.Success -> {
                    showLoading(false)
                    binding.tvEmpty.visibility =
                        if (result.data.isEmpty()) View.VISIBLE else View.GONE
                    showRecycler(result.data)
                }
                is Result.Error -> {
                    showLoading(false)
                    activity?.let { showToast(it.applicationContext, result.error) }
                }
            }
        }

    }

    private fun showRecycler(data: List<User>) {
        binding.rvFollower.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = FollowAdapter(data)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}