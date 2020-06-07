package com.starlab.dailycollection.ui.collection_details

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.starlab.dailycollection.R
import com.starlab.dailycollection.logic.model.DCollection
import com.starlab.dailycollection.logic.viewmodel.CustomViewModelProvider
import com.starlab.dailycollection.ui.bottom_nav.create.DcCombine
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.fragment_user_home.*


class DetailsActivity : AppCompatActivity() {

    private val detailsViewModel: DetailsViewModel by viewModels {
        CustomViewModelProvider.getDetailsViewModel(this)
    }

    private lateinit var adapter: DetailsAdapter

    companion object {
        private const val MENU_GALLERY_COVER = 1
        private const val MENU_GALLERY_ADDTION = 2
        private const val MENU_TAKE_COVER = 3
        private const val MENU_TAKE_ADDTION = 4
        private const val UPDATE_FLAG = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setSupportActionBar(detailsToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val dCollection = intent.getParcelableExtra("dcollection_parcelize") as DCollection
        detailsViewModel.dCollectionOrg = dCollection.copy()
        detailsViewModel.dCollectionNew = dCollection

        collapsingToolbar.title = dCollection.title
        Glide.with(this).load(dCollection.cover).into(dCollectionCover)
        createDateText.text = dCollection.createDate
        storyLineEdit.setText( dCollection.storyline)
        notesEdit.setText( dCollection.notes)
        sourceEdit.setText( dCollection.source)

        additionalAdd.isEnabled = false

        detailsViewModel.additionalList.addAll(dCollection.posts)
        adapter = DetailsAdapter(this, detailsViewModel.additionalList)

        DcCombine.combine(this, adapter)


        dCollectionCover.setOnClickListener {
            DcCombine.bigImageView(this, dCollection.cover)
        }

        storyLineEdit.setOnLongClickListener {
            if (!additionalAdd.isEnabled) {
                detailsCopy(storyLineEdit)
            }
            true
        }



        notesEdit.setOnLongClickListener {
            if (!additionalAdd.isEnabled) {
                detailsCopy(notesEdit)
            }
            true
        }

        sourceEdit.setOnLongClickListener {
            if (!additionalAdd.isEnabled) {
                detailsCopy(sourceEdit)
            }
            true
        }

        val editTextList: List<EditText> = listOf(titleEdit, storyLineEdit, notesEdit, sourceEdit)
        floatViewEdit.setOnClickListener {
            floatEdit.visibility = View.VISIBLE
            floatViewEdit.visibility = View.GONE
            changeEditSatus(editTextList, true)
            additionalAdd.isEnabled = true
            titleClick.visibility = View.VISIBLE
            titleEdit.setText(detailsViewModel.dCollectionNew.title)
            Snackbar.make(it, "编辑状态", Snackbar.LENGTH_LONG)
                .setAction("Undo", {
                    Snackbar.make(it, "已撤销", Snackbar.LENGTH_SHORT).show();
                }).show()

        }

        floatEdit.setOnClickListener {
            floatEdit.visibility = View.GONE
            floatViewEdit.visibility = View.VISIBLE
            changeEditSatus(editTextList, false)
            additionalAdd.isEnabled = false
            titleClick.visibility = View.GONE
            saveChange()
            if (checkIsUpdate()) {
                detailsViewModel.updateDCollection()
                Snackbar.make(it, "已保存", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }else {
                Snackbar.make(it, "无更改", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }

        }

        collectionDelete.setOnClickListener {

            val dialog = AlertDialog.Builder(this)
            dialog.apply {
                setTitle("Attention")
                setMessage("确定删除吗 ?")
                setIcon(R.drawable.ic_home_black_24dp)

                setPositiveButton("确定") {_, _ ->
                    finish()
                    detailsViewModel.deleteDCollection(dCollection)
                }

                setNegativeButton("取消") { _, _ ->

                }
                create()
                show()
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
                                detailsViewModel.additionalList.apply {
                                    removeAt(pos)
                                    detailsViewModel.dCollectionNew.posts = this
                                }
                                additionalAdd.let {
                                    if (!it.isVisible) it.visibility = View.VISIBLE
                                    if (detailsViewModel.additionalList.size == 0) {
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {

            android.R.id.home -> {
                leaveCheck()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        leaveCheck()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            MENU_GALLERY_COVER -> {

                val uri: Uri? = data?.data
                if (uri != null) {
                    Glide.with(this).load(uri).into(dCollectionCover)
                    detailsViewModel.dCollectionNew.cover = uri.toString()
                }
            }
            MENU_GALLERY_ADDTION -> {
                val uri = data?.data
                if (uri != null) {
                    detailsViewModel.additionalList.apply {
                        add(uri.toString())
                        detailsViewModel.dCollectionNew.posts = this
                    }
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

    private fun changeEditSatus (list: List<EditText>, boolean: Boolean) {

        for (editText in list) {
            editText.isFocusable = boolean
            editText.isFocusableInTouchMode = boolean
        }
    }

    private fun detailsCopy(editText: EditText) {
        val clipboard: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText("EditText", editText.text.toString())
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this, "已复制", Toast.LENGTH_SHORT).show()
    }

    private fun leaveCheck() {

        if (floatEdit.isVisible) {
            saveChange()
            if (checkIsUpdate()) {
                val dialog = AlertDialog.Builder(this)
                dialog.apply {
                    setTitle("Attention")
                    setMessage("未保存 ")
                    setIcon(R.drawable.ic_home_black_24dp)
                    setPositiveButton("保存") {_, _ ->
                        detailsViewModel.updateDCollection()
                        finish()
                    }
                    setNegativeButton("放弃修改") { _, _ ->
                        finish()
                    }
                    create()
                    show()
                }
            }else {
                finish()
            }
        }else {
            finish()
        }
    }

    private fun saveChange() {
        collapsingToolbar.title = titleEdit.text.toString()
        detailsViewModel.dCollectionNew.title = titleEdit.text.toString()
        detailsViewModel.dCollectionNew.storyline = storyLineEdit.text.toString()
        detailsViewModel.dCollectionNew.notes = notesEdit.text.toString()
        detailsViewModel.dCollectionNew.source = sourceEdit.text.toString()
    }

    private fun checkIsUpdate(): Boolean {
        return detailsViewModel.dCollectionOrg != detailsViewModel.dCollectionNew
    }





/*    titleEdit.setOnFocusChangeListener {_, hasFocus ->
        if (!hasFocus) {
            Log.d("update1", "更新title")
            val newTitle = titleEdit.text.toString()
            collapsingToolbar.title = newTitle
            detailsViewModel.updateTitle(newTitle)
        }
    }

    storyLineEdit.setOnFocusChangeListener {_, hasFocus ->
        if (!hasFocus) {
            Log.d("update1", "更新story")
            detailsViewModel.updateStoryLine(storyLineEdit.text.toString())
        }
    }

    notesEdit.setOnFocusChangeListener {_, hasFocus ->
        if (!hasFocus) {
            Log.d("update1", "更新Note")
            detailsViewModel.updateNotes(notesEdit.text.toString())
        }
    }


    sourceEdit.setOnFocusChangeListener {_, hasFocus ->
        if (!hasFocus) {
            Log.d("update1", "更新source")
            detailsViewModel.updateSource(sourceEdit.text.toString())
        }
    }*/
}