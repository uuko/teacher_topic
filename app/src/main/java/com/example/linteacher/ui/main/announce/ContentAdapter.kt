package com.example.linteacher.ui.main.announce

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.example.linteacher.databinding.ItemCarsoulInnerBinding
import com.example.linteacher.util.BaseViewHolder
import com.example.linteacher.util.Config
import java.util.*

class ContentAdapter(
    private var list: ArrayList<Content.Response>
) : ListAdapter<Content.Response, RecyclerView.ViewHolder>(DiffCallback()) {
    lateinit var listener: ContentListener.View
    fun setViewListener(listener: ContentListener.View) {
        this.listener = listener
    }

    fun setDataList(list: ArrayList<Content.Response>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val itemBinding =
            ItemCarsoulInnerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding, parent.context)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(getItem(position), position, listener)
    }


    class ViewHolder(
        private val binding: ItemCarsoulInnerBinding, private val context: Context
    ) : BaseViewHolder(binding.root) {
        fun bind(
            items: Content.Response,
            position: Int,
            listener: ContentListener.View
        ) {

            with(binding) {
                articleTitle.text = items.articleTitle
                articleTag.text = items.articleTag
                binding.articleDate.text = pareDate(items.modifyDate)

                itemView.setOnClickListener {
                    listener.onItemClick(items.articleId)
                }
                val mainView = binding.contentView
                mainView.html = items.articleContent
            }

        }


    }

    private class DiffCallback : DiffUtil.ItemCallback<Content.Response>() {
        //1
        override fun areItemsTheSame(
            oldItem: Content.Response,
            newItem: Content.Response
        ): Boolean {
            return oldItem.articleId == newItem.articleId
        }

        //2
        override fun areContentsTheSame(
            oldItem: Content.Response,
            newItem: Content.Response
        ): Boolean {
            return oldItem == newItem
        }
    }
}