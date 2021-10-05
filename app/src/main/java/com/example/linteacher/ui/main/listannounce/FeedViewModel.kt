package com.example.linteacher.ui.main.listannounce

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.linteacher.api.pojo.artical.Response
import com.example.linteacher.util.NetworkState
import java.util.concurrent.Executors

class FeedViewModel : ViewModel() {

    private var networkState: LiveData<NetworkState>? = null
    private lateinit var feedDataFactory: FeedDataFactory
    private var articleLiveData: LiveData<PagedList<Response>>? = null
    private fun init() {
        val executor = Executors.newFixedThreadPool(5)
        feedDataFactory = FeedDataFactory()
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

    fun refresh() {
        feedDataFactory.invalidate()
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
