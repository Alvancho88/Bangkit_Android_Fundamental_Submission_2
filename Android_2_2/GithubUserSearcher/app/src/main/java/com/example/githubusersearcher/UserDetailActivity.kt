package com.example.githubusersearcher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.example.githubusersearcher.databinding.ActivityUserDetailBinding
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Callback
import retrofit2.Response

class UserDetailActivity : AppCompatActivity() {

    companion object {
        const val TAG = "UserDetailActivity"
        const val USERNAME = "username"

        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.tab_text_1, R.string.tab_text_2)
    }

    private lateinit var binding: ActivityUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(USERNAME)
        displayUserDetail(username.toString())

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username.toString()

        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }

    private fun displayUserDetail(username: String) {
        showLoading(true)

        val client = ApiConfig.getApiService().getUserDetail(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: retrofit2.Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                showLoading(false)
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    if(responseBody != null) {
                        setUsersData(responseBody)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<DetailUserResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    private fun setUsersData(items: DetailUserResponse) {
        Glide.with(this)
            .load(items.avatarUrl)
            .circleCrop()
            .into(binding.imgDetailPhoto)

        val textDetailName = items.name
        val textDetailUsername = items.login
        val textDetailCompany = "Company : ${items.company}"
        val textDetailLocation = "Location : ${items.location}"
        val textDetailRepos = "Repositories : ${items.publicRepos}"

        binding.tvDetailName.text = textDetailName
        binding.tvDetailUsername.text = textDetailUsername
        binding.tvDetailCompany.text = textDetailCompany
        binding.tvDetailLocation.text = textDetailLocation
        binding.tvDetailRepository.text = textDetailRepos
    }

    private fun showLoading(isLoading: Boolean) {
        if(isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}