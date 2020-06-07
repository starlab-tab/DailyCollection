package com.starlab.dailycollection.logic.model

import android.net.Uri
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.starlab.dailycollection.logic.base.converters.CoverConverter
import com.starlab.dailycollection.logic.base.converters.PostsConverter
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "dcollection")
@TypeConverters(PostsConverter::class)
@Parcelize
data class DCollection(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "dcollection_title") var title: String = "No Title",
    @ColumnInfo(name = "dcollection_storyline") var storyline: String = "ImageYself",
    @ColumnInfo(name = "dcollection_notes") var notes: String = "Later",
    @ColumnInfo(name = "dcollection_source") var source: String = "No source",
    @ColumnInfo(name = "dcollection_create_date") var createDate: String = "",
    @ColumnInfo(name = "dcollection_cover") var cover: String = "no cover",
    @ColumnInfo(name = "dcollection_posts") var posts: List<String> = ArrayList()
) : Parcelable {

}