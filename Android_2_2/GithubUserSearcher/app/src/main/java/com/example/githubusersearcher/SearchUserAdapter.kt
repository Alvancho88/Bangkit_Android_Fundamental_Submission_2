package com.example.githubusersearcher

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class SearchUserAdapter(private val listUsers: ArrayList<GithubUser>) : RecyclerView.Adapter<SearchUserAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_user_name)
        val imgAvatar: ImageView = view.findViewById(R.id.img_user_photo)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_search_user, viewGroup, false))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvName.text = listUsers[position].username
        Glide.with(viewHolder.itemView.context)
            .load(listUsers[position].avatar)
            .circleCrop()
            .into(viewHolder.imgAvatar)

        viewHolder.itemView.setOnClickListener {
            val intent = Intent(viewHolder.itemView.context, UserDetailActivity::class.java)
            intent.putExtra(UserDetailActivity.USERNAME, listUsers[position].username)
            viewHolder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = listUsers.size


}