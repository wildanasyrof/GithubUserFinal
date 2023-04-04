package com.dicoding.githubuserfinal.ui.detail.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuserfinal.data.Result
import com.dicoding.githubuserfinal.databinding.FragmentFollowBinding
import com.dicoding.githubuserfinal.utils.ViewModelFactory

class FollowFragment : Fragment() {

    private var tabName: String? = null
    private var username: String? = null

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabName = arguments?.getString(ARG_TAB)
        username = arguments?.getString("username")

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: FollowViewModel by viewModels { factory }

        if (tabName == TAB_FOLLOWERS) {
            viewModel.getFollowers(username.toString()).observe(viewLifecycleOwner) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> binding?.progressBar?.isVisible = true
                        is Result.Success -> {
                            binding?.progressBar?.isVisible = false
                            val adapter = FollowAdapter(result.data)
                            binding?.rvFollow?.adapter = adapter
                        }
                        is Result.Error -> {
                            binding?.progressBar?.isVisible = false
                            Toast.makeText(
                                requireActivity(),
                                "Terjadi kesalahan" + result.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        } else if (tabName == TAB_FOLLOWING) {
            viewModel.getFollowing(username.toString()).observe(viewLifecycleOwner) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> binding?.progressBar?.isVisible = true
                        is Result.Success -> {
                            binding?.progressBar?.isVisible = false
                            val adapter = FollowAdapter(result.data)
                            binding?.rvFollow?.adapter = adapter
                        }
                        is Result.Error -> {
                            binding?.progressBar?.isVisible = false
                            Toast.makeText(
                                requireActivity(),
                                "Terjadi kesalahan" + result.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

        recyclerViewSetting()
    }

    private fun recyclerViewSetting() {
        val layoutManager = LinearLayoutManager(requireActivity())
        binding?.rvFollow?.layoutManager = layoutManager
        binding?.rvFollow?.setHasFixedSize(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val ARG_TAB = "tab_name"
        const val TAB_FOLLOWERS = "Followers"
        const val TAB_FOLLOWING = "Following"
    }
}