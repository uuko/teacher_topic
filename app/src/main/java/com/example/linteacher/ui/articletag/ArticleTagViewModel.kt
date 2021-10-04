package com.example.linteacher.ui.articletag

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.linteacher.api.pojo.artical.ArticleListTagResponse
import com.example.linteacher.api.pojo.artical.ArticleResponse
import com.example.linteacher.api.pojo.artical.Response
import com.example.linteacher.util.NetworkState
import java.util.concurrent.Executors

class ArticleTagViewModel(private val dataModel: ArticleTagRepository) : ViewModel() {
    fun getArticleAllTags(): MutableLiveData<ArticleListTagResponse> {
        return dataModel.getArticleAllTags()
    }

    private var networkState: LiveData<NetworkState>? = null
    private var articleLiveData: LiveData<PagedList<ArticleResponse>>? = null
    lateinit var feedDataFactory: TagDataFactory
    private fun init(tags: String) {
        val executor = Executors.newFixedThreadPool(5)
        feedDataFactory = TagDataFactory()
        feedDataFactory.setSearchQuery(tags)
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

    fun getArticleLiveData(): LiveData<PagedList<ArticleResponse>>? {
        return articleLiveData
    }

    fun refresh(tags: String) {
        feedDataFactory.setSearchQuery(tags)
        feedDataFactory.invalidate()
    }

    fun initData(tags: String) {
        init(tags)
    }

    init {

    }
}