package com.starlab.dailycollection.ui.bottom_nav.create

import android.Manifest
import android.annotation.SuppressLint
import android.app.ActionBar
import android.app.Activity
import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.permissionx.guolindev.PermissionX
import com.starlab.dailycollection.R
import com.starlab.dailycollection.ui.collection_details.DetailsAdapter
import kotlinx.android.synthetic.main.activity_details.*
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

object DcCombine {

    private const val ADDITIONAL_MAX = 5
    private const val MENU_GALLERY_COVER = 1
    private const val MENU_GALLERY_ADDTION = 2
    private const val MENU_TAKE_COVER = 3
    private const val MENU_TAKE_ADDTION = 4

//    object DcCombine : View.OnClickListener{
    fun combine(activity: Activity, adapter: DetailsAdapter) {
        val layoutInflater = LinearLayoutManager(activity)
        layoutInflater.orientation = LinearLayoutManager.HORIZONTAL
        val adlRecyclerView: RecyclerView = activity.findViewById(R.id.adlRecyclerView)
        adlRecyclerView.layoutManager = layoutInflater
        adlRecyclerView.adapter = adapter
        val additionalAdd: Button = activity.findViewById(R.id.additionalAdd)
        val storylineClick: TextView = activity.findViewById(R.id.storyLineClick)
        storylineClick.setOnClickListener {
            val storylineEdit: EditText = activity.findViewById(R.id.storyLineEdit)
            if (!storylineEdit.isVisible) {
                storylineEdit.visibility = View.VISIBLE
                return@setOnClickListener
            }
            storylineEdit.visibility = View.GONE
        }

        val sourceClick: TextView = activity.findViewById(R.id.sourceClick)
        sourceClick.setOnClickListener {
            val sourceEdit: EditText = activity.findViewById(R.id.sourceEdit)

            if (!sourceEdit.isVisible) {
                sourceEdit.visibility = View.VISIBLE
                return@setOnClickListener
            }
            sourceEdit.visibility = View.GONE
        }

        val notesClick: TextView = activity.findViewById(R.id.notesClick)
        notesClick.setOnClickListener {
            val notesEdit: EditText = activity.findViewById(R.id.notesEdit)
            if (!notesEdit.isVisible) {
                notesEdit.visibility = View.VISIBLE
                return@setOnClickListener
            }
            notesEdit.visibility = View.GONE
        }

    val additionalClick: TextView = activity.findViewById(R.id.additionalClick)
        additionalClick.setOnClickListener {

            if (!additionalAdd.isVisible) {
                adlRecyclerView.visibility = View.VISIBLE
                additionalAdd.visibility = View.VISIBLE
            }else {
                adlRecyclerView.visibility = View.GONE
                additionalAdd.visibility = View.GONE
            }

        }

        val dCollectionCover: ImageView = activity.findViewById(R.id.dCollectionCover)
        val forCoverMenuShow: TextView = activity.findViewById(R.id.forCoverMenuShow)

        dCollectionCover.setOnLongClickListener {
            if (additionalAdd.isEnabled) showPopupMenu(forCoverMenuShow, activity, 1)
            true
        }

        additionalAdd.setOnClickListener {
            showPopupMenu(additionalAdd, activity, 2)
        }
    }

    fun showPopupMenu(view: View, activity: Activity, flag: Int) {
        // View当前PopupMenu显示的相对View的位置
        val popupMenu = PopupMenu(activity, view)
        popupMenu.getMenuInflater().inflate(R.menu.post_menu, popupMenu.getMenu())
        popupMenu.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.menu_gallery -> {
                    PermissionX.init(activity as FragmentActivity).permissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .request { allGranted, grantedList, deniedList ->

                            if (allGranted) {
                                val intent = Intent(Intent.ACTION_PICK, null)
                                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
                                when(flag) {
                                    1 -> activity.startActivityForResult(intent, MENU_GALLERY_COVER)
                                    2 -> activity.startActivityForResult(intent, MENU_GALLERY_ADDTION)
                                }
                            }else {
                                Toast.makeText(activity, "已拒绝访问相册权限, 无法选择", Toast.LENGTH_SHORT).show()
                            }
                        }
                }

                R.id.menu_camera -> {

                    PermissionX.init(activity as FragmentActivity).permissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .request { allGranted, grantedList, deniedList ->

                            if (allGranted) {

                                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                                if (view is ImageView) activity.startActivityForResult(intent, MENU_TAKE_COVER)
                                activity.startActivityForResult(intent, MENU_TAKE_ADDTION)

                            }else {
                                Toast.makeText(activity, "已拒绝访问相机权限, 无法拍照", Toast.LENGTH_SHORT).show()
                            }

                        }


                }
            }
            false
        }

        popupMenu.setOnDismissListener {

        }
        popupMenu.show()
    }

    fun getCurrentTime(): String{
        val date = Date()
        val formatted = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return formatted.format(date)
    }

    fun bigImageView(activity: Activity, imagePath: String?) {
        val dialog = Dialog(activity, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        val imageView = initView(activity)
        dialog.setContentView(imageView)
        Glide.with(activity).load(imagePath).into(imageView)
        dialog.show()
        imageView.setOnClickListener{
            dialog.dismiss()
        }
    }

    @SuppressLint("ResourceType")
    private fun initView(activity: Activity): ImageView {
        val imgView = ImageView(activity)
        imgView.setLayoutParams(
            ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT
            )
        )
        return imgView
    }
}