package com.example.linteacher.ui.managearticle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.linteacher.api.pojo.artical.ArticalGetResponse
import com.example.linteacher.api.pojo.artical.Response
import com.example.linteacher.ui.main.listannounce.FeedDataFactory
import com.example.linteacher.util.NetworkState
import java.util.concurrent.Executors

class EditArticleViewModel() : ViewModel() {
    private var networkState: LiveData<NetworkState>? = null
    private var articleLiveData: LiveData<PagedList<Response>>? = null
    private fun init() {
        val executor = Executors.newFixedThreadPool(5)
        val feedDataFactory = FeedDataFactory()
        networkState = Transformations.switchMap(
            feedDataFactory.getMutableLiveData()
        ) { dataSource -> dataSource.networkState }
        val pagedListConfig: PagedList.Config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(10)
            .setPageSize(15).build()
        articleLiveData = LivePagedListBuilder(feedDataFactory, pagedListConfig)
            .setFetchExecutor(executor)
            .build()
    }

    fun getNetworkState(): LiveData<NetworkState>? {
        return networkState
    }

    fun getArticleLiveData(): LiveData<PagedList<Response>>? {
        return articleLiveData
    }

    init {
        init()
    }
}