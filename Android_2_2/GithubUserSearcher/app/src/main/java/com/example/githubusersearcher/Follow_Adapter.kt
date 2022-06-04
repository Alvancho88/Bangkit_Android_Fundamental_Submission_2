package com.example.githubusersearcher

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class Follow_Adapter(private val listFollowers: ArrayList<GithubUser>) : RecyclerView.Adapter<Follow_Adapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvUsername: TextView = view.findViewById(R.id.txt_username)
        val imgAvatar: ImageView = view.findViewById(R.id.img_avatar)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.search_fragment_item, viewGroup, false))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvUsername.text = listFollowers[position].username
        Glide.with(viewHolder.itemView.context)
            .load(listFollowers[position].avatar)
            .circleCrop()
            .into(viewHolder.imgAvatar)
    }

    override fun getItemCount() = listFollowers.size
}