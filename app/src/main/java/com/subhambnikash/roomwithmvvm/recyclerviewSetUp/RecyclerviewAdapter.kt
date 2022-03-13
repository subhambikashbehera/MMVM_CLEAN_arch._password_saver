package com.subhambnikash.roomwithmvvm.recyclerviewSetUp

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.subhambnikash.roomwithmvvm.R
import com.subhambnikash.roomwithmvvm.databinding.RecyclerItemBinding
import com.subhambnikash.roomwithmvvm.db.PasswordTable

class RecyclerviewAdapter(private val listener: (PasswordTable) -> Unit) :
    RecyclerView.Adapter<RecyclerviewAdapter.MyViewHolder>() {

    private val dataList= arrayListOf<PasswordTable>()



    inner class MyViewHolder(private val binding: RecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(passwordTable: PasswordTable,listener: (PasswordTable) -> Unit){
            binding.apply {
                userId.text=passwordTable.userId
                password.text=passwordTable.password
                itemLayout.setOnClickListener {

                    listener(passwordTable)
                    Log.d("checkMessage", "bind: ${passwordTable.userId}")
                }
            }
        }
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
       val binding:RecyclerItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.recycler_item, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(dataList[position],listener)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun newList(newDataList: List<PasswordTable>){
        dataList.clear()
        dataList.addAll(newDataList)
    }


//    override fun getItemViewType(position: Int): Int {
//        return position
//    }
}