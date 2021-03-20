package com.lytasky.dailyrecord.base.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RecordEntity::class], version = 2)
abstract class RecordDatabase : RoomDatabase() {
    abstract fun recordDao(): RecordDao
}