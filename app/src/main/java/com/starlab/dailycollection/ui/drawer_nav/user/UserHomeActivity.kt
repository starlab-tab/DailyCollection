package com.starlab.dailycollection.ui.drawer_nav.user

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.starlab.dailycollection.R
import com.starlab.dailycollection.logic.model.DCollection
import com.starlab.dailycollection.logic.model.User
import com.starlab.dailycollection.logic.viewmodel.CustomViewModelProvider
import com.starlab.dailycollection.logic.viewmodel.UserViewModel
import com.starlab.dailycollection.ui.bottom_nav.create.DcCombine
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_nav_header.*
import kotlinx.android.synthetic.main.fragment_user_home.*

class UserHomeActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by viewModels {
        CustomViewModelProvider.getUserViewModel(this)
    }

    companion object {
        private const val MENU_GALLERY_COVER = 1
        private const val MENU_GALLERY_ADDTION = 2
        private const val MENU_TAKE_COVER = 3
        private const val MENU_TAKE_ADDTION = 4
        private const val UPDATE_FLAG = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_home)
        setSupportActionBar(userToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val user: User = intent.getParcelableExtra("user_parcelize") as User
        userViewModel.userCopy = user.copy()
        userViewModel.userNew = user.copy()
        if (user.avatar.length > 1) Glide.with(this).load(user.avatar).into(userHomeAvatar)
        codeNameEdit.setText(user.codeName)
        wordEdit.setText(user.word)
        totalCount.text = "${user.count}"
        userHomeAvatar.setOnLongClickListener {
            if (userFloatEdit.isVisible) DcCombine.showPopupMenu(userHomeAvatar, this, 1)
            true
        }

        userHomeAvatar.setOnClickListener {
            DcCombine.bigImageView(this, userViewModel.userNew.avatar)
        }

        val editList = listOf(codeNameEdit, wordEdit)
        userFloatViewEdit.setOnClickListener {
            userFloatEdit.visibility= View.VISIBLE
            userFloatViewEdit.visibility= View.GONE
            changeEditSatus(editList, true)
            Snackbar.make(it, "编辑状态", Snackbar.LENGTH_LONG)
                .setAction("Undo", null).show()

        }

        userFloatEdit.setOnClickListener {
            userFloatEdit.visibility= View.GONE
            userFloatViewEdit.visibility= View.VISIBLE
            changeEditSatus(editList, false)
            saveChange()
            if (userViewModel.userNew != userViewModel.userCopy) {
                userViewModel.updateUser()
                Snackbar.make(it, "已保存", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }else {
                Snackbar.make(it, "无更改", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
        }
    }

    override fun onBackPressed() {
        leaveCheck()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            android.R.id.home -> {
                leaveCheck()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            MENU_GALLERY_COVER -> {
                val uri: Uri? = data?.data
                if (uri != null) {
                    Glide.with(this).load(uri).into(userHomeAvatar)
                    userViewModel.userNew.avatar = uri.toString()
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

    private fun leaveCheck() {

        if (userFloatEdit.isVisible) {
            saveChange()
            if (userViewModel.userNew != userViewModel.userCopy) {
                val dialog = AlertDialog.Builder(this)
                dialog.apply {
                    setTitle("Attention")
                    setMessage("未保存 ")
                    setIcon(R.drawable.ic_home_black_24dp)
                    setPositiveButton("保存") {_, _ ->
                        userViewModel.updateUser()
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
        userViewModel.userNew.codeName = codeNameEdit.text.toString()
        userViewModel.userNew.word = wordEdit.text.toString()
    }
}