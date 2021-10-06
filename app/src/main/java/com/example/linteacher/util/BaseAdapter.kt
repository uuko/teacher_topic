package com.example.linteacher.util

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.linteacher.api.BaseData

//BaseAdpter繼承RecyclerView.Adapter, BaseAdpter list參數限制為list<BaseData>,其中BaseData畢竟要有ViewType
open class BaseAdapter( var list:java.util.ArrayList<out BaseData>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    // list =fragment傳入
    fun setDataList(mDataList: ArrayList<out BaseData>) {
        //list的item有個itemType可以判斷這筆資料是來自哪兒
        list = mDataList
        notifyDataSetChanged()
    }
//return list
    fun getDataList(): ArrayList< out BaseData> {
        return list
    }

    //set new list ,And notifyITemChanged by position
    fun setOneData(mDataList: ArrayList< out BaseData>, position: Int) {
        list = mDataList
        notifyItemChanged(position)
    }

    //get list size
    override fun getItemCount(): Int {
        return list.size
    }

    //改寫getItemViewType(原本return=0),現在可客制化,return list[position].itemType
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