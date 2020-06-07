package com.starlab.dailycollection.logic.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "user")
@Parcelize
data class User(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "user_code_name") var codeName: String = "STAR_LAB",
    @ColumnInfo(name = "user_pwd") var pwd: String = "STAR_LAB",
    @ColumnInfo(name = "user_word") var word: String = "Cause We Are Young And Beautiful",
    @ColumnInfo(name = "user_avatar") var avatar: String = "S",
    @ColumnInfo(name = "user_collections") var count: Int = 0

) : Parcelable {

}