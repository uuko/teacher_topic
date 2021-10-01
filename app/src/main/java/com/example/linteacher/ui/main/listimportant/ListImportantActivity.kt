package com.example.linteacher.ui.main.listimportant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.linteacher.R
import com.example.linteacher.databinding.ActivityListAnnounceBinding
import com.example.linteacher.databinding.ActivityListImportantBinding
import com.example.linteacher.ui.main.listannounce.FeedListAdapter
import com.example.linteacher.ui.main.listannounce.FeedViewModel

class ListImportantActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListImportantBinding
    private val viewModel: ListImportantViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListImportantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.listFeed.layoutManager = LinearLayoutManager(this)
        val adapter = FeedListAdapter(this)


        /*
         * Step 4: When a new page is available, we call submitList() method
         * of the PagedListAdapter class
         *
         * */
        viewModel.getArticleLiveData()?.observe(this) { pagedList ->
            adapter.submitList(
                pagedList
            )
        }


        /*
         * Step 5: When a new page is available, we call submitList() method
         * of the PagedListAdapter class
         *
         * */
        viewModel.getNetworkState()?.observe(this) { networkState ->
            adapter.setNetworkState(
                networkState
            )
        }

        binding.listFeed.adapter = adapter
    }
}