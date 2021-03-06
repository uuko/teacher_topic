package com.example.linteacher.ui.main.listannounce

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.linteacher.databinding.ActivityListAnnounceBinding
import com.example.linteacher.ui.main.announceinner.AnnounceInnerActivity
import com.example.linteacher.util.ActivityNavigator
import com.example.linteacher.util.ArticleInnerListener

class ListAnnounceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListAnnounceBinding
    private val viewModel: FeedViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListAnnounceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecycleView()
        binding.announceListRefreshlayout.setOnRefreshListener {
            binding.announceListRefreshlayout.isRefreshing = false
            viewModel.refresh()
        }


    }

    private fun initRecycleView() {
        binding.listFeed.layoutManager = LinearLayoutManager(this)
        val adapter = FeedListAdapter(this, object : ArticleInnerListener {
            override fun onItemClick(article: Int) {
                val bundle = Bundle()
                bundle.putSerializable("articleId", article.toString())

                ActivityNavigator.startActivityWithData(
                    AnnounceInnerActivity::class.java,
                    bundle,
                    this@ListAnnounceActivity
                )

            }

        })

        viewModel.getArticleLiveData()?.observe(this) { pagedList ->
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
}