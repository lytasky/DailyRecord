package com.lytasky.dailyrecord.base.database

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

class RecordContent(

    @ColumnInfo(name = "content")
    var content: String = "",

    @ColumnInfo(name = "pic_array")
    var picArray: String = "",

    @ColumnInfo(name = "extra")
    var extra: String = ""
)