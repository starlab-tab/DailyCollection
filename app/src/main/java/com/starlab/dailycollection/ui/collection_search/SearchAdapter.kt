package com.starlab.dailycollection.ui.collection_search

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.starlab.dailycollection.R
import com.starlab.dailycollection.logic.model.DCollection
import com.starlab.dailycollection.ui.collection_details.DetailsActivity

class SearchAdapter(private val context: Context, private val dCollectionList: List<DCollection>) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val collectionTitle: TextView = view.findViewById(R.id.collectionItemTitle)
        val collectionCover: ImageView = view.findViewById(R.id.collectionItemCover)
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
            context.startActivity(intent)
        }
        return holder

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val dCollection = dCollectionList[position]
        holder.collectionTitle.text = dCollection.title

        //string转uri
        Glide.with(context).load(dCollection.cover).into(holder.collectionCover)

    }

    override fun getItemCount() = dCollectionList.size
}