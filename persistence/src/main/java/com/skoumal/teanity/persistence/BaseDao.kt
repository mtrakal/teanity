package com.skoumal.teanity.persistence

import androidx.room.*

interface BaseDao<T> {

    @Delete
    suspend fun delete(obj: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(obj: T): Long

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(objects: List<T>): List<Long>

    @Update
    suspend fun update(obj: T): Int

    @Transaction
    @Update
    suspend fun update(objects: List<T>): Int

}