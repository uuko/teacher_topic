package com.example.linteacher.ui.main.announce

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.linteacher.api.pojo.artical.ArticalGetResponse
import com.example.linteacher.api.pojo.artical.LatestArticleResponse
import com.example.linteacher.api.pojo.banner.BannerGetResponse


class AnnounceViewModel constructor(var repository: AnnounceRepository) : ViewModel() {

    fun getBannerList(): MutableLiveData<BannerGetResponse> {
        return repository.getBannerList()
    }

    fun getImportantList(page: Int): MutableLiveData<ArticalGetResponse> {
        return repository.getImportantList(page)
    }

    fun getLatestList(page: Int): MutableLiveData<ArticalGetResponse> {
        return repository.getLatestList(page)
    }

}