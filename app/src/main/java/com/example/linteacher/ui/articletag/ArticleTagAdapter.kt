package com.example.linteacher.ui.articletag

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.linteacher.api.pojo.artical.ArticleResponse
import com.example.linteacher.databinding.ItemCarsoulInnerBinding
import com.example.linteacher.databinding.ItemNetworkBinding
import com.example.linteacher.util.ArticleInnerListener
import com.example.linteacher.util.BaseViewHolder
import com.example.linteacher.util.NetworkState

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
        return networkState != null && networkState !== NetworkState.LOADED
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
        BaseViewHolder(binding.getRoot()) {
        private val binding: ItemCarsoulInnerBinding
        fun bindTo(items: ArticleResponse?) {

            with(binding) {
                articleTitle.text = items!!.articleTitle
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
                    params.width = 500
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
                    params.width = ViewGroup.LayoutParams.WRAP_CONTENT
                    params.startToStart = guideline19.id
                    params.topToTop = guideline21.id
                    contentText.requestLayout()
                    imageView.visibility = View.GONE
                }
                binding.contentText.text = result.content

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
                    return oldItem.articleId == newItem.articleId
                }

                override fun areContentsTheSame(
                    oldItem: ArticleResponse,
                    newItem: ArticleResponse
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}