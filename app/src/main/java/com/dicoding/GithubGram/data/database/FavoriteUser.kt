package com.dicoding.GithubGram.data.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
class FavoriteUser (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "name_user")
    var username : String = "",
    @ColumnInfo(name = "avatar")
    var avatarUrl : String? = null
) : Parcelable