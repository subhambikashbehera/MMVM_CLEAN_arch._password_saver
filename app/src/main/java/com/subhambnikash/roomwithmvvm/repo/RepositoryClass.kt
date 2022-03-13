package com.subhambnikash.roomwithmvvm.repo

import com.subhambnikash.roomwithmvvm.db.OpearationQuries
import com.subhambnikash.roomwithmvvm.db.PasswordTable

class RepositoryClass(private val opearationQuries: OpearationQuries) {

    val allData=opearationQuries.getAllData()

    suspend fun update(passwordTable: PasswordTable):Int{
        return opearationQuries.update(passwordTable)
    }
    suspend fun delete(id:Int):Int {
       return opearationQuries.deleteSpecific(id)
    }
    suspend fun insert(passwordTable: PasswordTable):Long{
       return opearationQuries.insert(passwordTable)
    }
    suspend fun deleteAll():Int{
       return opearationQuries.allDelete()
    }
}