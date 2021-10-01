package com.example.linteacher.ui.main.listimportant

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.linteacher.api.pojo.artical.Response
import com.example.linteacher.ui.main.listannounce.FeedDataSource

class ListImportDataFactory : DataSource.Factory<Long, Response>() {
    private val mutableLiveData: MutableLiveData<ListDataSource> =
        MutableLiveData<ListDataSource>()

    override fun create(): DataSource<Long, Response> {
        val feedDataSource: ListDataSource = ListDataSource()
        mutableLiveData.postValue(feedDataSource)
        return feedDataSource
    }

    fun getMutableLiveData(): MutableLiveData<ListDataSource> {
        return mutableLiveData
    }

}