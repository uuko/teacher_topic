package com.example.linteacher.ui.articletag

import android.R
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.linteacher.databinding.ActivityArticleTagBinding
import com.example.linteacher.ui.main.announceinner.AnnounceInnerActivity
import com.example.linteacher.util.ActivityNavigator
import com.example.linteacher.util.ArticleInnerListener
import com.google.android.material.chip.Chip


class ArticleTagActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArticleTagBinding
    private val factory = ArticleTagViewModelFactory(ArticleTagRepository())
    private val viewModel: ArticleTagViewModel by viewModels {
        factory
    }
    private var nowTags = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleTagBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val tag = intent.getSerializableExtra("articleTag") as String
        nowTags = tag
        viewModel.initData(tag)
        getTagsBindView(tag)

        binding.mSwipeRefreshLayout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            binding.mSwipeRefreshLayout.isRefreshing = false
            getTagsBindView(tag)
            viewModel.refresh(nowTags)
        })
        /*
        *
        * */
        binding.listFeed.layoutManager = LinearLayoutManager(this)
        val adapter = ArticleTagAdapter(this, object : ArticleInnerListener {
            override fun onItemClick(article: Int) {
                val bundle = Bundle()
                bundle.putSerializable("articleId", article.toString())

                ActivityNavigator.startActivityWithData(
                    AnnounceInnerActivity::class.java,
                    bundle,
                    this@ArticleTagActivity
                )

            }

        })

        viewModel.getArticleLiveData()?.observe(this) { pagedList ->
            Log.d("TagDataSource", "onCreate: size ${pagedList.size}")
            adapter.submitList(
                pagedList
            )
        }
        viewModel.getNetworkState()?.observe(this) { networkState ->
            adapter.setNetworkState(
                networkState
            )
        }

        binding.listFeed.adapter = adapter
    }


    private fun getTagsBindView(tag: String) {
        val list = ArrayList<ChipData>()
        binding.chipsLayout.removeAllViews()
        viewModel.getArticleAllTags()
            .observe(this, {
                for (response in it.iterator()) {

                    val chip = Chip(this)
                    chip.text = response.tag
                    if (tag == response.tag) chip.setChipBackgroundColorResource(R.color.holo_red_dark)
                    else chip.setChipBackgroundColorResource(R.color.black)
                    chip.isCloseIconVisible = false
                    chip.setTextColor(resources.getColor(R.color.white))
                    val id = ViewCompat.generateViewId();
                    chip.id = id
                    list.add(ChipData(chip.id, chip.text.toString()))
                    chip.setOnClickListener {
                        Log.d(
                            "getTagsBindView", "getTagsBindView: ${response.tag} " +
                                    "size : ${list.size}"
                        )

                        for (minichip in list) {

                            if (minichip.text.toString() == chip.text.toString()) {
                                Log.d(
                                    "getTagsBindView", "minichip: ${minichip.text.toString()} " +
                                            "chip : ${chip.text.toString()}"
                                )
                                findViewById<Chip>(minichip.id).setChipBackgroundColorResource(R.color.holo_red_dark)
                            } else {
                                findViewById<Chip>(minichip.id).setChipBackgroundColorResource(R.color.black)
                            }

                        }
                        nowTags = chip.text.toString()
                        viewModel.refresh(chip.text.toString())
                    }
                    binding.chipsLayout.addView(chip);
                }
            })
    }
}