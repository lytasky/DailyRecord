package com.lytasky.dailyrecord.base.database

import android.content.Context
import android.util.Log
import androidx.room.Room

object DatabaseHelper {

    private lateinit var recordDatabase: RecordDatabase;

    fun initRecordDatabase(context: Context): RecordDatabase {
        recordDatabase = Room.databaseBuilder(
            context,
            RecordDatabase::class.java, "database-record"
        ).allowMainThreadQueries()
            .build()
        return recordDatabase;
    }

    fun getAllRecord(): List<RecordEntity> {
        return recordDatabase.recordDao().getAllRecord()
    }

    fun getDurRecord(timeBegin: String, timeEnd: String): List<RecordEntity> {
        return recordDatabase.recordDao()
            .findByCreateTime("2021-03-18 16:16:30", "2021-03-18 16:30:33");
    }

    fun updateRecord(recordEntity: RecordEntity): Int {
        return recordDatabase.recordDao().update(recordEntity)
    }

    fun deleteRecord(recordEntity: RecordEntity) {
        return recordDatabase.recordDao().delete(recordEntity)
    }

    fun insertRecord(recordContent: RecordContent) {
        return recordDatabase.recordDao().insertBatch(recordContent)
    }

    fun destroyRecordDatabase() {
        if (recordDatabase.isOpen) {
            recordDatabase.close();
        }
    }
}