package com.starlab.dailycollection.ui.bottom_nav.home

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.starlab.dailycollection.R
import com.starlab.dailycollection.logic.model.DCollection
import com.starlab.dailycollection.ui.collection_details.DetailsActivity

class HomeAdapter(private val homeFragment: HomeFragment, val dCollectionList: ArrayList<DCollection>) : RecyclerView.Adapter<HomeAdapter.ViewHolder>(){

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dCollectionTitle: TextView = view.findViewById(R.id.collectionItemTitle)
        val dCollectionCover: ImageView = view.findViewById(R.id.collectionItemCover)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.collection_item, parent, false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            val dcollection = dCollectionList[position]
            //跳转到details页面
            val intent = Intent(parent.context, DetailsActivity::class.java).apply {
                putExtra("dcollection_parcelize", dcollection)
            }
            parent.context.startActivity(intent)
        }
        return holder

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val dCollection = dCollectionList[position]
        holder.dCollectionTitle.text = dCollection.title
        Glide.with(homeFragment).load(dCollection.cover).into(holder.dCollectionCover)
    }

    override fun getItemCount() = dCollectionList.size




}