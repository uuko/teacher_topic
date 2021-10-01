package com.example.linteacher.ui.main.listannounce

import androidx.lifecycle.MutableLiveData
import com.example.linteacher.api.RetrofitManager
import com.example.linteacher.api.pojo.artical.ArticalGetResponse
import com.example.linteacher.api.pojo.artical.LatestAllArticleResponse
import com.example.linteacher.api.pojo.artical.LatestArticleResponse
import com.example.linteacher.util.Config
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class ListAnnounceRepository {
    fun getImportantList(): MutableLiveData<LatestAllArticleResponse> {
        val data = MutableLiveData<LatestAllArticleResponse>()
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.getLatestAllList(Config.GET_ARTICLE_ALL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<LatestAllArticleResponse>() {
                    override fun onNext(t: LatestAllArticleResponse) {
                        data.value = t
                    }

                    override fun onError(e: Throwable) {
                        data.value = LatestAllArticleResponse(mutableListOf())
                    }

                    override fun onComplete() {

                    }

                })
        )
        return data
    }


    fun getLatestList(): MutableLiveData<LatestAllArticleResponse> {
        val data = MutableLiveData<LatestAllArticleResponse>()
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.getLatestAllList(Config.GET_LATEST_ARTICLE_ALL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<LatestAllArticleResponse>() {
                    override fun onNext(t: LatestAllArticleResponse) {
                        data.value = t
                    }

                    override fun onError(e: Throwable) {
                        data.value = LatestAllArticleResponse(mutableListOf())
                    }

                    override fun onComplete() {

                    }

                })
        )
        return data
    }
}