package com.starlab.dailycollection.ui.bottom_nav.create

import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.FileUtils
import android.provider.MediaStore
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.starlab.dailycollection.R
import com.starlab.dailycollection.logic.viewmodel.CustomViewModelProvider
import com.starlab.dailycollection.ui.collection_details.DetailsActivity
import com.starlab.dailycollection.ui.collection_details.DetailsAdapter
import kotlinx.android.synthetic.main.activity_details.*
import java.text.SimpleDateFormat
import java.util.*


class CreateActivity : AppCompatActivity() {

    private lateinit var adapter: DetailsAdapter

    private var coverChange = false

    private val createViewModel: CreateViewModel by viewModels {
        CustomViewModelProvider.getCreateViewModel(this)
    }

    companion object {
        private const val MENU_GALLERY_COVER = 1
        private const val MENU_GALLERY_ADDTION = 2
        private const val MENU_TAKE_COVER = 3
        private const val MENU_TAKE_ADDTION = 4
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        initView()

        adapter = DetailsAdapter(this, createViewModel.additionalList)
        DcCombine.combine(this, adapter)
        dCollectionCover.setOnClickListener {
            DcCombine.bigImageView(this, createViewModel.dCollection.cover)
        }

        collectionCreate.setOnClickListener {
            if (titleEdit.text.toString().trim().length == 0) {
                Toast.makeText(this, "这, Title都没啊 ?", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (coverChange) {
                createViewModel.dCollection.apply {
                    title = titleEdit.text.toString()
                    storyline = storyLineEdit.text.toString()
                    notes = notesEdit.text.toString()
                    source = sourceEdit.text.toString()
                    posts = createViewModel.additionalList
                    createDate = DcCombine.getCurrentTime()
                }
                this.finish()
                createViewModel.create(this)
            }else {
                Toast.makeText(this, "找个封面吧", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


        }


        adapter.setOnItemClickListener(object : DetailsAdapter.OnItemClickListener {
            override fun onItemLongClick(view: View?, pos: Int) {
                val popupMenu = view?.let { PopupMenu(it.context, it) }
                popupMenu?.let {
                    it.menuInflater.inflate(R.menu.details_nav_menu, it.menu)
                    it.setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.detial_delete -> {
                                createViewModel.additionalList.removeAt(pos)
                                additionalAdd.let {
                                    if (!it.isVisible) it.visibility = View.VISIBLE
                                    if (createViewModel.additionalList.size == 0) {
                                        adlRecyclerView.visibility = View.GONE
                                        additionalAdd.visibility = View.GONE
                                    }
                                }
                                adapter.notifyDataSetChanged()
                            }
                        }
                        true
                    }
                    if (additionalAdd.isEnabled) it.show()
                }
            }
        })

    }

    override fun onBackPressed() {
        val dialog = AlertDialog.Builder(this)
        dialog.apply {
            setTitle("Attention")
            setMessage("未创建, 确认离开吗 ?")
            setIcon(R.drawable.ic_home_black_24dp)

            setPositiveButton("确定") {_, _ ->
                finish()
            }

            setNegativeButton("取消") { _, _ ->

            }
            create()
            show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            MENU_GALLERY_COVER -> {
                val uri: Uri? = data?.data
                if (uri != null) {
                    Glide.with(this).load(uri.toString()).into(dCollectionCover)
                    createViewModel.dCollection.cover = uri.toString()
                    coverChange = true
                }
            }
            MENU_GALLERY_ADDTION -> {
                val uri: Uri? = data?.data
                if (uri != null) {
                    createViewModel.additionalList.add(uri.toString())
                    adapter.notifyDataSetChanged()
                }
            }

            MENU_TAKE_COVER -> {
                Toast.makeText(this, "功能未实现", Toast.LENGTH_SHORT).show()
            }

            MENU_TAKE_ADDTION -> {
                Toast.makeText(this, "功能未实现", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun initView() {

        floatViewEdit.visibility = View.GONE
        floatEdit.visibility = View.GONE
        createDate.visibility = View.GONE
        titleClick.visibility = View.VISIBLE

        val editTextList: List<EditText> = listOf(storyLineEdit, notesEdit, sourceEdit)
        changeEditSatus(editTextList, true)
        additionalAdd.isEnabled = true

        collectionDelete.apply {
            visibility = View.GONE
            text = "创建"
            setTextColor(Color.WHITE)
        }
        collectionCreate.visibility = View.VISIBLE

        Glide.with(this).load(R.drawable.ic_category_black_24dp).into(dCollectionCover)

    }

    private fun changeEditSatus (list: List<EditText>, boolean: Boolean) {

        for (editText in list) {
            editText.isFocusable = boolean
            editText.isFocusableInTouchMode = boolean
        }
    }
}