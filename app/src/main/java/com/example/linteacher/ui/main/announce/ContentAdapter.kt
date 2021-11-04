package com.example.linteacher.ui.main.announce

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.linteacher.databinding.ItemCarsoulInnerBinding
import com.example.linteacher.util.BaseViewHolder
import java.util.*
import androidx.constraintlayout.widget.ConstraintSet

import android.R
import androidx.constraintlayout.widget.ConstraintLayout


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
                android.util.Log.d("articleTag", "bind: ${items.articleTag}")
                binding.articleDate.text = pareDate(items.modifyDate)

                itemView.setOnClickListener {
                    listener.onItemClick(items.articleId)
                }
                val result = handleCutStr(items.articleContent)

                if (result.picFirst.isNotEmpty()) {
                    imageView.visibility = View.VISIBLE
                    val params = contentText.layoutParams as ConstraintLayout.LayoutParams
                    params.width = 176
                    params.startToStart = guideline19.id
                    params.topToTop = guideline21.id
                    contentText.requestLayout()
                    Glide.with(binding.root.context)
                        .load(result.picFirst)
                        .apply(RequestOptions().override(113, 113))
                        .centerCrop()
                        .into(imageView)
                } else {


                    val params = contentText.layoutParams as ConstraintLayout.LayoutParams
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT
                    params.startToStart = guideline19.id
                    params.topToTop = guideline21.id
                    contentText.requestLayout()
                    imageView.visibility = View.GONE
                }
                binding.contentText.text = result.content

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