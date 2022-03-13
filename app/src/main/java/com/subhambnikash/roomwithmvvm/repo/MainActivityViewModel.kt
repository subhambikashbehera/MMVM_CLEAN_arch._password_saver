package com.subhambnikash.roomwithmvvm.repo

import android.os.PatternMatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subhambnikash.roomwithmvvm.Event
import com.subhambnikash.roomwithmvvm.db.PasswordTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel(private val repositoryClass: RepositoryClass):ViewModel() {

    var allData:LiveData<List<PasswordTable>>
    val userId=MutableLiveData<String?>()
    val password=MutableLiveData<String?>()
    val saveOrUpdateBtn=MutableLiveData<String>()
    val clearBtnTextBtn=MutableLiveData<String>()
    val titleText=MutableLiveData<String>()

    private val messageEvent=MutableLiveData<Event<String>>()

    val message:LiveData<Event<String>>
    get() = messageEvent


    private lateinit var updatedPasswordTable: PasswordTable
    private var updateOrDelete=false

    init {
        saveOrUpdateBtn.value="Save"
        clearBtnTextBtn.value="Clear All"
        allData=repositoryClass.allData
    }


    fun inItDeleteUpdate(passwordTable: PasswordTable){
        userId.value=passwordTable.userId
        password.value=passwordTable.password
        saveOrUpdateBtn.value="Update"
        clearBtnTextBtn.value="Delete"
        updatedPasswordTable=passwordTable
        updateOrDelete=true
    }

    fun saveOrUpdate(){

        if (userId.value==null){
            messageEvent.value= Event("Enter User Id")
        }else if (password.value==null){
            messageEvent.value= Event("enter password")
        }else{
            if (updateOrDelete){
                updatedPasswordTable.userId= userId.value.toString()
                updatedPasswordTable.password=password.value.toString()
                updateCred(updatedPasswordTable)
                userId.value=null
                password.value=null
                saveOrUpdateBtn.value="Save"
                clearBtnTextBtn.value="Clear All"
                updateOrDelete=false
            }else{
                insertCred(PasswordTable(0,userId.value.toString(),password.value.toString()))
                userId.value=null
                password.value=null
            }

        }






    }
    fun clearOrDelete(){
        if (updateOrDelete){
            deleteCred(updatedPasswordTable)
            userId.value=null
            password.value=null
            saveOrUpdateBtn.value="Save"
            clearBtnTextBtn.value="Clear All"
            updateOrDelete=false
        }else{
            deleteAllCred()
        }

    }

    private fun insertCred(passwordTable: PasswordTable): Job =viewModelScope.launch (Dispatchers.IO){
        val newRowId= repositoryClass.insert(passwordTable)
        withContext(Dispatchers.Main){
            if (newRowId>-1){
                messageEvent.value= Event("Successfully inserted $newRowId")
            }else
            {
                messageEvent.value=Event("error occurred")
            }
        }

    }

    private fun deleteCred(passwordTable: PasswordTable): Job =viewModelScope.launch (Dispatchers.IO){
        val newRowId= repositoryClass.delete(passwordTable.id)
        withContext(Dispatchers.Main){
            if (newRowId>-1){
                messageEvent.value= Event("Successfully deleted $newRowId ${passwordTable.id} ${passwordTable.userId}")
            }else
            {
                messageEvent.value=Event("error occurred")
            }
        }



    }

    private fun updateCred(passwordTable: PasswordTable): Job =viewModelScope.launch (Dispatchers.IO){
        val newRowId= repositoryClass.update(passwordTable)
        withContext(Dispatchers.Main){
            if (newRowId>-1){
                messageEvent.value= Event("Successfully updated $newRowId")
            }else
            {
                messageEvent.value=Event("error occurred")
            }

        }

    }

    private fun deleteAllCred(): Job =viewModelScope.launch (Dispatchers.IO){
        val newRowId=  repositoryClass.deleteAll()
        withContext(Dispatchers.Main){
            if (newRowId>-1){
                messageEvent.value= Event("All Successfully cleared $newRowId")
            }else
            {
                messageEvent.value=Event("error occurred")
            }
        }

    }


}