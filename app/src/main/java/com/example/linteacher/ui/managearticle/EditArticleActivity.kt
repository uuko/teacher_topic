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
import com.example.linteacher.api.pojo.artical.DeleteArticleRequest
import com.example.linteacher.api.pojo.artical.Response
import com.example.linteacher.databinding.ActivityEditArticleBinding
import com.example.linteacher.ui.addarticle.AddArticleActivity
import com.example.linteacher.ui.addarticle.AddArticleRepository
import com.example.linteacher.ui.addarticle.AddArticleViewModel
import com.example.linteacher.ui.addarticle.AddArticleViewModelFactory
import com.example.linteacher.ui.managearticle.editinner.EditInnerActivity
import com.example.linteacher.util.ActivityNavigator
import com.example.linteacher.util.Config

class EditArticleActivity : AppCompatActivity(), EditListener.View {
    private lateinit var binding: ActivityEditArticleBinding
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private val factory = EditArticleViewModelFactory(EditArticleRepository())
    private val viewModel: EditArticleViewModel by viewModels {
        factory
    }
    lateinit var adapter: EditArticleAdapter
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
        binding.deleteBtn.setOnClickListener {
            val checkedList = ArrayList<DeleteArticleRequest>()
            val removeList = ArrayList<Response>()

            for (data in adapter.currentList!!) {
                if (data.isChecked) {
                    Log.d("isChecked", "onCreate: ${data.articleId}")
                    removeList.add(data)
                    checkedList.add(DeleteArticleRequest(data.articleId))
                }
            }
            if (checkedList.size > 0) {
                viewModel.deleteArticle(checkedList)
                    .observe(this, {
                        if (it.result == Config.RESULT_OK) {
                            viewModel.invalidate()
                        }
                    })
            }
        }
    }

    private fun initRecycleView() {
        binding.listFeed.layoutManager = LinearLayoutManager(this)
        adapter = EditArticleAdapter(this, this)
        viewModel.getArticleLiveData()?.observe(this) { pagedList ->
            for (response in pagedList) {
                response.isChecked = false
            }
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