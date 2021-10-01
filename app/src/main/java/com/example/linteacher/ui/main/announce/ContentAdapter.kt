package com.example.linteacher.ui.main.announce

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.example.linteacher.databinding.ItemCarsoulInnerBinding
import com.example.linteacher.util.Config
import java.util.*

class ContentAdapter(
    private var list: ArrayList<Content.Response>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
        (holder as ViewHolder).bind(list[position], position)
    }

    override fun getItemCount(): Int {
        return list.size;
    }

    class ViewHolder(
        private val binding: ItemCarsoulInnerBinding, private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            items: Content.Response,
            position: Int,

            ) {
            handleContent(items.articleContent)
            with(binding) {
                articleTitle.text = items.articleTitle
                articleTag.text = items.articleTag
                val mainView = binding.contentView
                mainView.removeAllViews()
                for (content in contentLst) {
                    Log.d("splitString", "bind: ${content.data}  type ${content.type}")
                    if (content.type == Config.PIC) {

                        val imageView = ImageView(context)
                        val vp: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                        imageView.layoutParams = vp
                        Glide.with(context)
                            .load(GlideUrl(content.data))
                            .into(imageView)
                        mainView.addView(imageView)
                    } else if (content.type == Config.TEXTVIEW) {
                        val textView = TextView(context)
                        textView.text = content.data
                        mainView.addView(textView)
                    }

                }
            }

        }

        val contentLst = mutableListOf<Content.ContentData>()
        private fun handleContent(articleContent: String) {
            contentLst.clear()
            if (articleContent.contains("<img>")) {
                val splitString = articleContent.split("<img>")
                Log.d("splitString", "handleContent: $splitString  size ${splitString.size}")
                for (i in splitString.indices) {
                    if (i % 2 == 1) {
                        contentLst.add(Content.ContentData(splitString[i], Config.PIC))
                    } else {
                        contentLst.add(Content.ContentData(splitString[i], Config.TEXTVIEW))
                    }
                }
            }
        }
    }
}