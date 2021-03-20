package com.lytasky.dailyrecord.base.database

import androidx.room.*

@Dao
interface RecordDao {
    @Query("SELECT * FROM recordEntity ORDER BY ID DESC")
    fun getAllRecord(): List<RecordEntity>

    @Query("SELECT * FROM recordEntity WHERE id == :recordId")
    fun findById(recordId: Long): RecordEntity

    @Query("SELECT * FROM recordEntity WHERE createdTime between :createTimeBegin and :createTimeEnd")
    fun findByCreateTime(createTimeBegin: String, createTimeEnd: String): List<RecordEntity>

    @Query("SELECT * FROM recordEntity WHERE lastModifiedTime between :updateTimeBegin and :updateTimeEnd")
    fun findByUpdateTime(updateTimeBegin: String, updateTimeEnd: String): List<RecordEntity>

    @Insert(entity = RecordEntity::class)
    fun insertBatch(vararg recordContent: RecordContent)

    @Update(entity = RecordEntity::class)
    fun update(vararg RecordEntity: RecordEntity): Int

    @Delete
    fun delete(recordEntity: RecordEntity)

}