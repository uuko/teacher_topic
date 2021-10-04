package com.example.linteacher.ui.articletag

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.linteacher.api.pojo.artical.ArticleResponse

class TagDataFactory : DataSource.Factory<Long, ArticleResponse>() {
    private val mutableLiveData: MutableLiveData<TagDataSource> =
        MutableLiveData<TagDataSource>()
    lateinit var feedDataSource: TagDataSource
    override fun create(): DataSource<Long, ArticleResponse> {
        feedDataSource = TagDataSource(searchQuery)
        mutableLiveData.postValue(feedDataSource)
        return feedDataSource
    }

    private var searchQuery = ""
    fun setSearchQuery(query: String) {
        searchQuery = query
    }

    fun getMutableLiveData(): MutableLiveData<TagDataSource> {
        return mutableLiveData
    }

    fun invalidate() {

        feedDataSource.invalidate()
    }
}