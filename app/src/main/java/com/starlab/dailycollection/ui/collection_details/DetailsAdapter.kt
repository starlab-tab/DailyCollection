package com.starlab.dailycollection.ui.collection_details

import android.annotation.SuppressLint
import android.app.ActionBar
import android.app.Activity
import android.app.Dialog
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.starlab.dailycollection.R
import com.starlab.dailycollection.ui.bottom_nav.create.DcCombine
import java.io.InputStream


class DetailsAdapter(private val activity: Activity, private val additionalList: List<String>) : RecyclerView.Adapter<DetailsAdapter.ViewHolder>() {


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val additionalCover: ImageView = view.findViewById(R.id.additionalCover)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(activity).inflate(R.layout.additional_item, parent, false)
        val holder = ViewHolder(view)
        holder.additionalCover.setOnClickListener {
            val position = holder.adapterPosition
            DcCombine.bigImageView(activity, additionalList[position])
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val additionalCoverId: String = additionalList[position]
        Glide.with(activity).load(additionalCoverId).into(holder.additionalCover)
        holder.additionalCover.setOnLongClickListener{
            onItemClickListener.onItemLongClick(holder.additionalCover, position)
            true
        }
    }

    override fun getItemCount() = additionalList.size

    private lateinit var onItemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemLongClick(view: View?, pos: Int)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        if (onItemClickListener != null) {
            this.onItemClickListener = onItemClickListener
        }
    }

}