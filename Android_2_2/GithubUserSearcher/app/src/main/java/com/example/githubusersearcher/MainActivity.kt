package com.example.githubusersearcher

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusersearcher.databinding.ActivityMainBinding
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
        var USERNAME = "Alvancho88"
        const val PER_PAGE = 100
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvSearchResponse.layoutManager = layoutManager

        findUser()
    }

    private fun findUser() {
        showLoading(true)

        val client = ApiConfig.getApiService().getUser(USERNAME, PER_PAGE)
        client.enqueue(object : Callback<SearchUserResponse> {
            override fun onResponse(
                call: retrofit2.Call<SearchUserResponse>,
                response: Response<SearchUserResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        //setUserData(responseBody.items)
                        setUsersData(responseBody.items)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: retrofit2.Call<SearchUserResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun setUsersData(items: List<ItemsItem>) {
        val listUsers = ArrayList<GithubUser>()
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
            listUsers.add(user)
        }

        val adapter = SearchUserAdapter(listUsers)
        binding.rvSearchResponse.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                USERNAME = query
                findUser()
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
        return true
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }


}