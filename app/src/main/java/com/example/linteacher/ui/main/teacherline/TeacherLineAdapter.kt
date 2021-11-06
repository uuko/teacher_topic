package com.example.linteacher.ui.main.teacherline

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.linteacher.api.pojo.teacherline.TeacherLineResponse
import com.example.linteacher.databinding.ItemTeacherlineBinding

class TeacherLineAdapter constructor(
    val items: MutableList<TeacherLineResponse>,
    val listener: TeacherFragment.OnItemClickListener
) : RecyclerView.Adapter<TeacherLineAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("onBindViewHolder", "onCreateViewHolder: ")
        val itemBinding =
            ItemTeacherlineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("onBindViewHolder", "onBindViewHolder: " + items.get(position).tchName)
        val itemData= items[position]
        holder.bind(itemData, listener)
    }

    override fun getItemCount(): Int {
        return if (items.isNotEmpty()) items.size else 0
    }

    fun clearItems() {
        val size = items.size
        items.clear()
        notifyItemRangeRemoved(0, size)
    }

    fun swapItems(mNewList: List<TeacherLineResponse>){

        val result: DiffUtil.DiffResult
                = DiffUtil.calculateDiff(RepoDiffCallBack(items,mNewList))
        items.clear()
        items.addAll(mNewList)
        result.dispatchUpdatesTo(this)
    }
    class ViewHolder(private val itemBinding: ItemTeacherlineBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(items: TeacherLineResponse, listener: TeacherFragment.OnItemClickListener) {
            if (items.tchPicUrl != "") {
                Glide.with(itemBinding.root)
                    .load(items.tchPicUrl)
                        .centerCrop()
                        .into(itemBinding.teacherTchPicUrl)
            }



            itemBinding.tchName.text = items.tchName
            itemBinding.tchMainDepartment.text = items.tchMainDepartment
            itemBinding.tchRireRank.text = items.tchRireRank
            itemBinding.tchSchool.text = items.tchSchool
            itemBinding.tchNameEN.text =items.tchNameEN
            itemBinding.tchDiploma.text = items.tchDiploma
            itemBinding.tchDepartment.text = items.tchDepartment
            itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    listener.onItemClick(items)
                }
            })
//            itemBinding.desc.text=items.description
//            itemBinding.stars.text=items.forks_count.toString()
        }
    }
}

class RepoDiffCallBack constructor(
    private val mOldList: List<TeacherLineResponse>,
    private val mNewList: List<TeacherLineResponse>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return if (mOldList.isNotEmpty()) mOldList.size else 0
    }

    override fun getNewListSize(): Int {
        return if (mNewList.isNotEmpty()) mNewList.size else 0
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldId = mOldList[oldItemPosition].tchNumber
        val newId = mNewList[newItemPosition].tchNumber
        return oldId == newId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldRepo: TeacherLineResponse = mOldList[oldItemPosition]
        val newRepo: TeacherLineResponse = mNewList[newItemPosition]
        return oldRepo == newRepo
    }

}
