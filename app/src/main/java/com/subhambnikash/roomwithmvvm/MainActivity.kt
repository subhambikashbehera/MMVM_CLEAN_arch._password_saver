package com.subhambnikash.roomwithmvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.subhambnikash.roomwithmvvm.databinding.ActivityMainBinding
import com.subhambnikash.roomwithmvvm.db.DataBaseOperationClass
import com.subhambnikash.roomwithmvvm.db.PasswordTable
import com.subhambnikash.roomwithmvvm.recyclerviewSetUp.RecyclerviewAdapter
import com.subhambnikash.roomwithmvvm.repo.MainActivityViewModel
import com.subhambnikash.roomwithmvvm.repo.RepositoryClass
import com.subhambnikash.roomwithmvvm.repo.ViewModelFactory

class MainActivity : AppCompatActivity() {


    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding
    lateinit var adapter: RecyclerviewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao=DataBaseOperationClass.generateDbInstances(application).dao
        val repository=RepositoryClass(dao)
        viewModelFactory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this,viewModelFactory)[MainActivityViewModel::class.java]
        binding.viewModel=viewModel
        binding.lifecycleOwner=this
        setUpData()

        viewModel.message.observe(this){
            it.getContentIfNotHandled().let { it2->
                Toast.makeText(this,it2,Toast.LENGTH_SHORT).show()
            }
        }


        
    }

    private fun setUpData(){
        binding.apply {
            dataRecyclerView.layoutManager=LinearLayoutManager(this@MainActivity)
            adapter= RecyclerviewAdapter { onItemSelected:PasswordTable->selectedItem(onItemSelected) }
            dataRecyclerView.adapter=adapter
        }
        getUserData()
    }

    private fun getUserData(){
        viewModel.allData.observe(this){
           adapter.newList(it)
            adapter.notifyDataSetChanged()
        }
    }

    private fun selectedItem(passwordTable: PasswordTable){
        viewModel.inItDeleteUpdate(passwordTable)
    }

}