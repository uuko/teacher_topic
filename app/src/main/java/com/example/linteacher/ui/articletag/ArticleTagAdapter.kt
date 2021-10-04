package com.example.linteacher.ui.articletag

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
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.example.linteacher.api.pojo.artical.ArticleResponse
import com.example.linteacher.api.pojo.artical.Response
import com.example.linteacher.databinding.ItemCarsoulInnerBinding
import com.example.linteacher.databinding.ItemNetworkBinding
import com.example.linteacher.ui.main.announce.Content
import com.example.linteacher.util.ArticleInnerListener
import com.example.linteacher.util.Config
import com.example.linteacher.util.NetworkState
import java.util.ArrayList

class ArticleTagAdapter(private val context: Context, val listener: ArticleInnerListener) :
    PagedListAdapter<ArticleResponse, RecyclerView.ViewHolder?>(
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
            val itemBinding: ItemCarsoulInnerBinding =
                ItemCarsoulInnerBinding.inflate(layoutInflater, parent, false)
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

    inner class ArticleItemViewHolder(binding: ItemCarsoulInnerBinding) :
        RecyclerView.ViewHolder(binding.getRoot()) {
        private val binding: ItemCarsoulInnerBinding
        private val contentLst: MutableList<Content.ContentData> = ArrayList()
        fun bindTo(article: ArticleResponse?) {
            binding.articleTitle.setText(article!!.articleTitle)
            binding.articleTag.setText(article.articleTag)
            itemView.setOnClickListener {
                listener.onItemClick(article.articleId)
            }
            handleContent(article.articleContent)
            val mainView: LinearLayout = binding.contentView
            mainView.removeAllViews()
            for ((data, type) in contentLst) {
                Log.d("splitString", "bind: \${content.data}  type \${content.type}")
                if (type == Config.PIC) {
                    val imageView = ImageView(context)
                    val vp = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    imageView.layoutParams = vp
                    Glide.with(context)
                        .load(GlideUrl(data))
                        .into(imageView)
                    mainView.addView(imageView)
                } else if (type == Config.TEXTVIEW) {
                    val textView = TextView(context)
                    textView.setTextColor(Color.BLACK)
                    textView.textSize = 20f
                    textView.text = data
                    mainView.addView(textView)
                }
            }
        }

        private fun handleContent(articleContent: String) {
            contentLst.clear()
            if (articleContent.contains("<img>")) {
                val splitString = articleContent.split("<img>").toTypedArray()
                for (i in splitString.indices) {
                    if (i > 4) break
                    if (i % 2 == 1) {
                        contentLst.add(Content.ContentData(splitString[i], Config.PIC))
                    } else {
                        contentLst.add(Content.ContentData(splitString[i], Config.TEXTVIEW))
                    }
                }
            } else {
                contentLst.add(Content.ContentData(articleContent, Config.TEXTVIEW))
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
        var DIFF_CALLBACK: DiffUtil.ItemCallback<ArticleResponse> =
            object : DiffUtil.ItemCallback<ArticleResponse>() {
                override fun areItemsTheSame(
                    oldItem: ArticleResponse,
                    newItem: ArticleResponse
                ): Boolean {
                    return false
                }

                override fun areContentsTheSame(
                    oldItem: ArticleResponse,
                    newItem: ArticleResponse
                ): Boolean {
                    return false
                }
            }
    }
}