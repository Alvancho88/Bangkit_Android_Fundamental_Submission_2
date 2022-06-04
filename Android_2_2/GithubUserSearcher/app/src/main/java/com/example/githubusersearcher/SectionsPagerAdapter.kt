package com.example.githubusersearcher

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var username: String = ""

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position) {
            0 -> {
                fragment = FollowerFragment()
                fragment.arguments = Bundle().apply {
                    putString(FollowerFragment.USERNAME, username)
                }
            }

            1 -> {
                fragment = FollowingFragment()
                fragment.arguments = Bundle().apply {
                    putString(FollowingFragment.USERNAME, username)
                }
            }
        }
        return fragment as Fragment
    }

//    override fun createFragment(position: Int): Fragment {
//        val fragment = FollowerFragment()
//        fragment.arguments = Bundle().apply {
//            putInt(FollowerFragment.ARG_SECTION_NUMBER, position + 1)
//        }
//        return fragment
//    }

}