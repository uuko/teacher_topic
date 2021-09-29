package com.example.linteacher.util

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.linteacher.api.BaseData


open class BaseAdapter( var list:java.util.ArrayList<out BaseData>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    fun setDataList(mDataList: ArrayList<out BaseData>) {
        list = mDataList
        notifyDataSetChanged()
    }

    fun getDataList(): ArrayList< out BaseData> {
        return list
    }

    fun setOneData(mDataList: ArrayList< out BaseData>, position: Int) {
        list = mDataList
        notifyItemChanged(position)
    }
    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return list[position].itemType
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }


}