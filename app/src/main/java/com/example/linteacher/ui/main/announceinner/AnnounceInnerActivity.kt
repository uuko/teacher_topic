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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.example.linteacher.R
import com.example.linteacher.api.pojo.artical.ArticleResponse
import com.example.linteacher.databinding.ActivityAnnounceInnerBinding
import com.example.linteacher.databinding.ActivityMainBinding
import com.example.linteacher.ui.articletag.ArticleTagActivity
import com.example.linteacher.ui.main.announce.AnnounceViewModel
import com.example.linteacher.ui.main.announce.Content
import com.example.linteacher.ui.teacherdata.ui.experience.ExpRepository
import com.example.linteacher.ui.teacherdata.ui.experience.ExpViewModel
import com.example.linteacher.ui.teacherdata.ui.experience.ExpViewModelFactory
import com.example.linteacher.util.ActivityNavigator
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
        initData(id)
        binding.articleTag.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("articleTag", binding.articleTag.text.toString())

            this?.let {
                ActivityNavigator.startActivityWithData(
                    ArticleTagActivity::class.java,
                    bundle,
                    it
                )
            }
        }

        binding.mSwipeRefreshLayout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            binding.mSwipeRefreshLayout.isRefreshing = false
            initData(id)
        })


    }

    private fun initData(id: String) {
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

                            val mainView = binding.contentView
                            mainView.html = it
                        }
                    }
                }

            })
    }


    private fun handleContent(articleContent: String): List<Content.ContentData> {
        val contentLst = mutableListOf<Content.ContentData>()
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
        return contentLst
    }

}