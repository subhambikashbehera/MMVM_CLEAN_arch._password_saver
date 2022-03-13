package com.subhambnikash.roomwithmvvm.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "passwordTable")
data class PasswordTable(
    @ColumnInfo(name="id")
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    @ColumnInfo(name = "userId")
    var userId:String,
    @ColumnInfo(name = "password")
    var password:String
    )