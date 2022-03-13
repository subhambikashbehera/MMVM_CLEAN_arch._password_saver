package com.subhambnikash.roomwithmvvm.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface OpearationQuries {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(insetData:PasswordTable):Long

    @Update
    suspend fun update(insetData:PasswordTable):Int

    @Delete
    suspend  fun delete(insetData:PasswordTable):Int

    @Query("DELETE FROM passwordTable")
    suspend fun allDelete():Int

    @Query("SELECT * FROM passwordTable")
    fun getAllData():LiveData<List<PasswordTable>>

    @Query("DELETE FROM passwordTable WHERE id = :id")
    suspend fun deleteSpecific(id:Int):Int

}