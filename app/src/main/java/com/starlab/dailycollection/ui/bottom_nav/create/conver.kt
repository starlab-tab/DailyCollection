package com.starlab.dailycollection.ui.bottom_nav.create

import android.widget.Toast


//const val RC_TAKE_PHOTO = 1
//
//private var mTempPhotoPath: String? = null
//private var imageUri: Uri? = null
//
//private open fun takePhoto() {
//    val intentToTakePhoto = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//    val fileDir =
//        File(Environment.getExternalStorageDirectory() + File.separator.toString() + "photoTest" + File.separator)
//    if (!fileDir.exists()) {
//        fileDir.mkdirs()
//    }
//    val photoFile = File(fileDir, "photo.jpeg")
//    mTempPhotoPath = photoFile.getAbsolutePath()
//    imageUri = FileProvider7.getUriForFile(this, photoFile)
//    intentToTakePhoto.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
//    startActivityForResult(intentToTakePhoto, RC_TAKE_PHOTO)
//}
