package com.example.linteacher.ui.managearticle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.linteacher.databinding.ActivityEditArticleBinding
import com.example.linteacher.ui.main.listannounce.FeedListAdapter
import com.example.linteacher.ui.main.listannounce.FeedViewModel

class EditArticleActivity : AppCompatActivity(), EditListener.View {
    private lateinit var binding: ActivityEditArticleBinding

    private val viewModel: EditArticleViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecycleView()
    }

    private fun initRecycleView() {
        binding.listFeed.layoutManager = LinearLayoutManager(this)
        val adapter = EditArticleAdapter(this)
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

    override fun onItemClick(aritcleId: Int) {
        TODO("Not yet implemented")
    }

}