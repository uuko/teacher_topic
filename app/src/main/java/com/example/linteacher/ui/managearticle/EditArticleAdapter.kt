package com.example.linteacher.ui.managearticle

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.example.linteacher.api.pojo.artical.Response
import com.example.linteacher.databinding.ItemCarsoulInnerBinding
import com.example.linteacher.databinding.ItemEditArticleBinding
import com.example.linteacher.databinding.ItemNetworkBinding
import com.example.linteacher.ui.main.announce.Content
import com.example.linteacher.util.BaseViewHolder
import com.example.linteacher.util.Config
import com.example.linteacher.util.NetworkState
import java.util.ArrayList

class EditArticleAdapter(private val context: Context, val listener: EditListener.View) :
    PagedListAdapter<Response, RecyclerView.ViewHolder?>(
        DIFF_CALLBACK
    ) {
    private var networkState: NetworkState? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_PROGRESS) {
            val headerBinding: ItemNetworkBinding =
                ItemNetworkBinding.inflate(layoutInflater, parent, false)
            NetworkStateItemViewHolder(headerBinding)
        } else {
            val itemBinding: ItemEditArticleBinding =
                ItemEditArticleBinding.inflate(layoutInflater, parent, false)
            ArticleItemViewHolder(itemBinding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ArticleItemViewHolder) {
            holder.bindTo(getItem(position))
        } else {
            (holder as NetworkStateItemViewHolder).bindView(networkState)
        }
    }

    private fun hasExtraRow(): Boolean {
        return if (networkState != null && networkState !== NetworkState.LOADED) {
            true
        } else {
            false
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            TYPE_PROGRESS
        } else {
            TYPE_ITEM
        }
    }

    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState = networkState
        val previousExtraRow = hasExtraRow()
        networkState = newNetworkState
        val newExtraRow = hasExtraRow()
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(itemCount)
            } else {
                notifyItemInserted(itemCount)
            }
        } else if (newExtraRow && previousState !== newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    inner class ArticleItemViewHolder(binding: ItemEditArticleBinding) :
        BaseViewHolder(binding.getRoot()) {
        private val binding: ItemEditArticleBinding
        private val contentLst: MutableList<Content.ContentData> = ArrayList()
        fun bindTo(article: Response?) {
            binding.articleTitle.text = article!!.articleTitle
            binding.articleTag.text = article.articleTag
            binding.modifyDate.text = pareDate(article.modifyDate)
            binding.checkBox.isChecked = article.isChecked
            binding.checkBox.setOnClickListener {
                article.isChecked = !article.isChecked
                binding.checkBox.isChecked = article.isChecked
            }

            val mainView = binding.contentView
            mainView.html = article.articleContent

            itemView.setOnClickListener {
                listener.onItemClick(article.articleId)
            }
        }


        init {
            this.binding = binding
        }
    }

    inner class NetworkStateItemViewHolder(binding: ItemNetworkBinding) :
        RecyclerView.ViewHolder(binding.getRoot()) {
        private val binding: ItemNetworkBinding
        fun bindView(networkState: NetworkState?) {
            if (networkState != null && networkState.status == NetworkState.Status.RUNNING) {
                binding.progressBar.setVisibility(View.VISIBLE)
            } else {
                binding.progressBar.setVisibility(View.GONE)
            }
            if (networkState != null && networkState.status == NetworkState.Status.FAILED) {
                binding.errorMsg.setVisibility(View.VISIBLE)
                binding.errorMsg.setText(networkState.msg)
            } else {
                binding.errorMsg.setVisibility(View.GONE)
            }
        }

        init {
            this.binding = binding
        }
    }

    companion object {
        private const val TYPE_PROGRESS = 0
        private const val TYPE_ITEM = 1
        var DIFF_CALLBACK: DiffUtil.ItemCallback<Response> =
            object : DiffUtil.ItemCallback<Response>() {
                override fun areItemsTheSame(oldItem: Response, newItem: Response): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(oldItem: Response, newItem: Response): Boolean {
                    val b =
                        oldItem.isChecked == newItem.isChecked && oldItem.articleId == newItem.articleId
                    oldItem.articleContent == newItem.articleContent && oldItem.articleTitle == newItem.articleTitle
                            && oldItem.articleImportant == newItem.articleImportant && oldItem.articleTag == newItem.articleTag
                    return false
                }
            }
    }

}