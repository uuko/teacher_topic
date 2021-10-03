package com.example.linteacher.ui.main.listannounce

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.linteacher.api.pojo.artical.Response

class FeedDataFactory : DataSource.Factory<Long, Response>() {
    private val mutableLiveData: MutableLiveData<FeedDataSource> =
        MutableLiveData<FeedDataSource>()
    lateinit var feedDataSource: FeedDataSource
    override fun create(): DataSource<Long, Response> {
        feedDataSource = FeedDataSource()
        mutableLiveData.postValue(feedDataSource)
        return feedDataSource
    }

    fun getMutableLiveData(): MutableLiveData<FeedDataSource> {
        return mutableLiveData
    }

    fun invalidate() {
        feedDataSource.invalidate()
    }
}