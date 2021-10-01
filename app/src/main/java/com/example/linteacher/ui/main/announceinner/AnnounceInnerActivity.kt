package com.example.linteacher.ui.main.announceinner

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.example.linteacher.R
import com.example.linteacher.api.pojo.artical.ArticleResponse
import com.example.linteacher.databinding.ActivityAnnounceInnerBinding
import com.example.linteacher.databinding.ActivityMainBinding
import com.example.linteacher.ui.main.announce.AnnounceViewModel
import com.example.linteacher.ui.main.announce.Content
import com.example.linteacher.ui.teacherdata.ui.experience.ExpRepository
import com.example.linteacher.ui.teacherdata.ui.experience.ExpViewModel
import com.example.linteacher.ui.teacherdata.ui.experience.ExpViewModelFactory
import com.example.linteacher.util.Config

class AnnounceInnerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnnounceInnerBinding
    private val factory = AnnounceInnerViewModelFactory(AnnounceInnerRepository())
    private val viewModel: AnnounceInnerViewModel by viewModels {
        factory
    }
    private lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnnounceInnerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        context = this
        val id = intent.getSerializableExtra("articleId") as String
        viewModel.getArticle(id)
            .observe(this, object : Observer<ArticleResponse> {
                override fun onChanged(item: ArticleResponse?) {
                    with(binding) {
                        articleTitle.text = item?.articleTitle
                        var important = ""
                        if (item?.articleImportant == "U") {
                            important = "重要"
                            articleImportant.chipBackgroundColor = ColorStateList.valueOf(Color.RED)
                            articleImportant.text = important
                        } else if (item?.articleImportant == "O") {
                            important = "普通"
                            articleImportant.chipBackgroundColor =
                                ColorStateList.valueOf(Color.GREEN)
                            articleImportant.text = important
                        }
                        articleImportant.text = important
                        articleTag.text = item?.articleTag
                        modifyDate.text = item?.modifyDate
                        item?.articleContent?.let {
                            handleContent(it)
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
                                    textView.setTextColor(Color.WHITE)
                                    textView.textSize = 20F
                                    textView.text = content.data
                                    mainView.addView(textView)
                                }

                            }
                        }
                    }
                }

            })


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
        } else {
            contentLst.add(Content.ContentData(articleContent, Config.TEXTVIEW))
        }
    }

}