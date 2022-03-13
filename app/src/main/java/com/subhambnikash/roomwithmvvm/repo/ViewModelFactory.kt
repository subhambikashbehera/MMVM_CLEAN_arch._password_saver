package com.subhambnikash.roomwithmvvm.repo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ViewModelFactory(private val repositoryClass: RepositoryClass):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
       return when(modelClass){
           MainActivityViewModel::class.java->{
               MainActivityViewModel(repositoryClass)
           }
           else->throw  IllegalArgumentException("error")
       } as T
    }


}