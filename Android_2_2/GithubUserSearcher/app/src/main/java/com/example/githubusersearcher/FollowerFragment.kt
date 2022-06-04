package com.example.githubusersearcher

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class FollowerFragment : Fragment() {

    private lateinit var progressBar: ProgressBar
    private lateinit var rvFollowers: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(USERNAME)

        progressBar = view.findViewById(R.id.progressBarFollowers)
        rvFollowers = view.findViewById(R.id.recycleViewFollowers)

        displayUserFollowers(username.toString())
    }

    private fun displayUserFollowers(username: String) {
        showLoading(true)
        Log.d(TAG, "displayUserFollowers: TEST")

        val client = ApiConfig.getApiService().getUserFollowers(username)
        client.enqueue(object : Callback<List<FollowerResponse>> {
            override fun onResponse(
                call: retrofit2.Call<List<FollowerResponse>>,
                response: Response<List<FollowerResponse>>
            ) {
                showLoading(false)
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d(TAG, "onResponse: ${responseBody.toString()}")
                    if(responseBody != null) {
                        setFollowersData(responseBody)
                    }
                } else {
                    Log.e(TAG, "onFailure1: ${response.message()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<List<FollowerResponse>>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure2: ${t.message}")
            }

        })
    }

    private fun setFollowersData(items: List<FollowerResponse>) {
        val listFollowers = ArrayList<GithubUser>()
        for(item in items) {
            val user = GithubUser(
                item.login,
                null,
                item.avatarUrl,
                null,
                null,
                null,
                null,
                null
            )
            listFollowers.add(user)
        }
        Log.d(TAG, "setFollowersData: $listFollowers")

        rvFollowers.layoutManager = LinearLayoutManager(requireContext())
        val adapter = Follow_Adapter(listFollowers)
        rvFollowers.adapter = adapter
    }

    companion object {
        const val TAG = "FollowersFragment"
        const val USERNAME = "Alvancho88"
    }

    private fun showLoading(isLoading: Boolean) {
        if(isLoading) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}