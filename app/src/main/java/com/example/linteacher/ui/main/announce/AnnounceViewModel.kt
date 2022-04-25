package com.example.linteacher.ui.main.announce

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.linteacher.api.pojo.artical.ArticalGetResponse
import com.example.linteacher.api.pojo.artical.LatestArticleResponse
import com.example.linteacher.api.pojo.banner.BannerGetResponse
import com.example.linteacher.api.pojo.login.LoginAllResponse
import com.example.linteacher.api.pojo.login.LoginRequest


class AnnounceViewModel constructor(var repository: AnnounceRepository) : ViewModel() {

    private val importantPageLiveData = MutableLiveData<Int>()
    private val pageLiveData = MutableLiveData<Int>()
    private val bannerGoLiveData = MutableLiveData<Boolean>()
    val latestLiveData: LiveData<ArticalGetResponse> =
        Transformations.switchMap(pageLiveData) { address ->
            repository.getLatestList(address)
        }
    val importantLiveData: LiveData<ArticalGetResponse> =
        Transformations.switchMap(importantPageLiveData) { address ->
            repository.getImportantList(address)
        }
    val bannerLiveData: LiveData<BannerGetResponse> =
        Transformations.switchMap(bannerGoLiveData) { address ->
            repository.getBannerList()
        }


    init {
        Log.e("AnnounceViewModel", "init: ", )
        getImportantList(0)
        getBannerList()
        getImportantList(0)
    }

    fun getBannerList() {
        bannerGoLiveData.value=bannerGoLiveData.value
    }

    fun getImportantList(page: Int){
        importantPageLiveData.value=page
    }

    fun getLatestList(page: Int) {
         pageLiveData.value=page
    }

    override fun onCleared() {
        Log.e("AnnounceViewModel", "onCleared: ", )
        super.onCleared()
    }
}