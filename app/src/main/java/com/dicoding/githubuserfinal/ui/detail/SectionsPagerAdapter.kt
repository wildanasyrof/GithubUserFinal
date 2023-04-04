package com.dicoding.githubuserfinal.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.githubuserfinal.ui.detail.fragment.FollowFragment

class SectionsPagerAdapter internal constructor(activity: AppCompatActivity, private var username: String) : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragment()
        val bundle = Bundle()
        if (position == 0) {
            bundle.putString(FollowFragment.ARG_TAB, FollowFragment.TAB_FOLLOWERS)
        } else {
            bundle.putString(FollowFragment.ARG_TAB, FollowFragment.TAB_FOLLOWING)
        }
        bundle.putString("username", username)
        fragment.arguments = bundle
        return fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}