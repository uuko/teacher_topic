package com.example.linteacher.ui.managearticle

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.linteacher.databinding.ActivityEditArticleBinding
import com.example.linteacher.ui.addarticle.AddArticleActivity
import com.example.linteacher.ui.managearticle.editinner.EditInnerActivity
import com.example.linteacher.util.ActivityNavigator

class EditArticleActivity : AppCompatActivity(), EditListener.View {
    private lateinit var binding: ActivityEditArticleBinding
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private val viewModel: EditArticleViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecycleView()
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    Log.d("resultLauncher", "onCreate: ")
                    viewModel.invalidate()
                }
            }
        binding.addBtn.setOnClickListener {
            ActivityNavigator
                .openActivity(
                    resultLauncher,
                    AddArticleActivity::class.java,
                    this
                )
        }
    }

    private fun initRecycleView() {
        binding.listFeed.layoutManager = LinearLayoutManager(this)
        val adapter = EditArticleAdapter(this, this)
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
        val bundle = Bundle()
        bundle.putSerializable("articleId", aritcleId.toString())
        ActivityNavigator.openActivityWithData(
            resultLauncher,
            EditInnerActivity::class.java,

            this,
            bundle,
        )

    }

}