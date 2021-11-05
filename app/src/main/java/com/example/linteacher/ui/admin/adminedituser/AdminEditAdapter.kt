package com.example.linteacher.ui.admin.adminedituser

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.linteacher.R
import com.example.linteacher.api.pojo.admin.list.AdminListTeacherResponse
import com.example.linteacher.databinding.ItemEdiitAdminBinding
import com.example.linteacher.databinding.ItemTeacherlineBinding

class AdminEditAdapter(
    val items: MutableList<AdminListTeacherResponse>,
    val listener: AdminEditActivity.OnHideClickListener
) : RecyclerView.Adapter<AdminEditAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("onBindViewHolder", "onCreateViewHolder: ")
        val itemBinding =
            ItemEdiitAdminBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("onBindViewHolder", "onBindViewHolder: " + items.get(position).teacherName)
        val itemData = items[position]
        holder.bind(itemData,listener,position)
    }

    override fun getItemCount(): Int {
        return if (items.isNotEmpty()) items.size else 0
    }

    fun clearItems() {
        val size = items.size
        items.clear()
        notifyItemRangeRemoved(0, size)
    }
    fun changeItemColors(position: Int,isVisible: Boolean){

        val mNewList= mutableListOf<AdminListTeacherResponse>()
        for (r:AdminListTeacherResponse in items){
            var n:AdminListTeacherResponse;
            Log.d("dd", "changeItemColors: ${r.picUrl} ")

                 n=AdminListTeacherResponse(r.account,r.grade,
                    r.loginId,r.picUrl,r.teacherName,r.isVisible)



            mNewList.add(n)
        }

        Log.d("isVisble", "onClick:2222 "+mNewList[position].isVisible+ items[position].isVisible)
        mNewList[position].isVisible=isVisible
        Log.d("isVisble", "onClick:33333 "+mNewList[position].isVisible + items[position].isVisible)
        val result: DiffUtil.DiffResult
                = DiffUtil.calculateDiff(RepoDiffCallBack(items,mNewList))
        items.clear()
        items.addAll(mNewList)
        result.dispatchUpdatesTo(this)
    }
    fun swapItems(mNewList: List<AdminListTeacherResponse>){

        val result: DiffUtil.DiffResult
                = DiffUtil.calculateDiff(RepoDiffCallBack(items,mNewList))
        items.clear()
        items.addAll(mNewList)
        result.dispatchUpdatesTo(this)
    }
    class ViewHolder(private val itemBinding: ItemEdiitAdminBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(
            items: AdminListTeacherResponse,
            listener: AdminEditActivity.OnHideClickListener,
            position: Int
        ) {
            Glide.with(itemBinding.root)
                .load(items.picUrl)
                    .apply(RequestOptions.bitmapTransform( CircleCrop()))
                .into(itemBinding.teacherTchPicUrl)
            itemBinding.teacherContent.text = items.teacherName

            if (items.isVisible){
                itemBinding.tchItemView.setBackgroundResource(R.drawable.tchlistitem_visible)

//                itemBinding.tchItemView.setBackgroundColor(Color.parseColor("#FFFFFF"))
//                itemView.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
            else{
                itemBinding.tchItemView.setBackgroundResource(R.drawable.tch_listitem_notvisible)

//                itemBinding.tchItemView.setBackgroundColor(Color.parseColor("#BEBEBE"))
//                itemView.setBackgroundColor(Color.parseColor("#BEBEBE"))
            }
            itemView.setOnClickListener(object : View.OnClickListener{
                override fun onClick(v: View?) {
                    listener.onHideClick(items,position)
                }
            })
            itemBinding.teacherMore.setOnClickListener {
                listener.onMoreClick(items,position)
            }
//            itemBinding.desc.text=items.description
//            itemBinding.stars.text=items.forks_count.toString()
        }
    }
}

class RepoDiffCallBack constructor(
    private val mOldList: List<AdminListTeacherResponse>,
    private val mNewList: List<AdminListTeacherResponse>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return if (mOldList.isNotEmpty()) mOldList.size else 0
    }

    override fun getNewListSize(): Int {
        return if (mNewList.isNotEmpty()) mNewList.size else 0
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldId = mOldList[oldItemPosition].loginId
        val newId = mNewList[newItemPosition].loginId

        return oldId == newId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldRepo: AdminListTeacherResponse = mOldList[oldItemPosition]
        val newRepo: AdminListTeacherResponse = mNewList[newItemPosition]
        return oldRepo == newRepo
    }

}