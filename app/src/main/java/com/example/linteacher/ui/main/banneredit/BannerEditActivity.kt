package com.example.linteacher.ui.main.banneredit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.linteacher.api.pojo.banner.BannerUpdateRequest
import com.example.linteacher.databinding.ActivityBannerEditBinding
import com.example.linteacher.ui.managearticle.editinner.EditInnerActivity
import com.example.linteacher.util.ActivityNavigator
import com.example.linteacher.util.Config

class BannerEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBannerEditBinding
    private val factory = BannerEditViewModelFactory(BannerEditRepository())
    private val viewModel: BannerEditViewModel by viewModels {
        factory
    }
    lateinit var resultLauncher: ActivityResultLauncher<Intent>
    lateinit var adapter: BannerEditAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBannerEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecycleView()
        getBannerList()
        binding.mSwipeRefreshLayout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            binding.mSwipeRefreshLayout.isRefreshing = false
            getBannerList()
        })
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    Log.d("resultLauncher", "onCreate: ")
                    getBannerList()
                }
            }


    }

    private fun getBannerList() {
        viewModel.getBannerList()
            .observe(this, {
                if (it.totalCount > 0) {
                    adapter.setDataList(it.bannerResponseList)
                }
            })
    }

    private fun initRecycleView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        layoutManager.reverseLayout = false
        binding.bannerRecycleView.layoutManager = layoutManager
        adapter = BannerEditAdapter(arrayListOf(), object : Listener {
            override fun onItemClick(aritcleId: Int) {
                val bundle = Bundle()
                bundle.putSerializable("articleId", aritcleId.toString())
                ActivityNavigator.openActivityWithData(
                    resultLauncher,
                    EditInnerActivity::class.java,

                    this@BannerEditActivity,
                    bundle,
                )
            }

            override fun onRemoveBannerClick(id: String) {
                deleteBanner(id)
            }

        })
        binding.bannerRecycleView.adapter = adapter
    }

    private fun deleteBanner(id: String) {

        viewModel.deleteBanner(id)
            .observe(this, {
                if (it.responseContent == Config.RESULT_OK) {
                    getBannerList()
                }
            })
    }


    interface Listener {
        fun onItemClick(id: Int)
        fun onRemoveBannerClick(picUrl: String)
    }
}