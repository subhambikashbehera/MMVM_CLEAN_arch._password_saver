package com.subhambnikash.roomwithmvvm.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [PasswordTable::class], version = 1, exportSchema = true)
abstract class DataBaseOperationClass:RoomDatabase() {
    abstract val dao:OpearationQuries
    companion object{
        @Volatile
        private var dbInstance:DataBaseOperationClass?=null
        fun generateDbInstances(context:Context):DataBaseOperationClass{
            synchronized(this){
                if (dbInstance==null){
                    dbInstance=Room.databaseBuilder(context.applicationContext,DataBaseOperationClass::class.java,"passDb").fallbackToDestructiveMigration().build()
                }
            }
            return dbInstance!!
        }
    }

}