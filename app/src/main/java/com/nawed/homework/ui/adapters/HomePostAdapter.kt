package com.nawed.homework.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nawed.homework.R

class HomePostAdapter(val data: ArrayList<String>) : RecyclerView.Adapter<HomePostAdapter.HomePostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePostViewHolder {
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.item_home_post_layout,parent,false)

        return HomePostViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomePostViewHolder, position: Int) {
       holder.tv_userName.text = data[position]
    }

    override fun getItemCount(): Int {
      return data.size
    }
    inner class HomePostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_userName: TextView = itemView.findViewById(R.id.tv_userName)
    }
}