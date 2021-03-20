package com.lytasky.dailyrecord.base.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RecordEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo(name = "content")
    var content: String = "",

    @ColumnInfo(name = "pic_array")
    var picArray: String = "",

    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    val createdTime: String = "",

    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    val lastModifiedTime: String = "",

    @ColumnInfo(name = "extra")
    var extra: String = ""
)