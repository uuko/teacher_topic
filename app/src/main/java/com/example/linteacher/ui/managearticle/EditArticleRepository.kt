package com.example.linteacher.ui.managearticle

import androidx.lifecycle.MutableLiveData
import com.example.linteacher.api.RetrofitManager
import com.example.linteacher.api.pojo.artical.ArticalGetResponse
import com.example.linteacher.util.Config
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class EditArticleRepository {
    fun getImportantList(page: Int): MutableLiveData<ArticalGetResponse> {
        val data = MutableLiveData<ArticalGetResponse>()
        val url = String.format(Config.GET_ARTICLE, page)
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.getImportantList(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<ArticalGetResponse>() {
                    override fun onNext(t: ArticalGetResponse) {
                        data.value = t
                    }

                    override fun onError(e: Throwable) {
                        data.value = ArticalGetResponse(0, mutableListOf(), 0)
                    }

                    override fun onComplete() {

                    }

                })
        )
        return data
    }


    fun getLatestList(page: Int): MutableLiveData<ArticalGetResponse> {
        val data = MutableLiveData<ArticalGetResponse>()
        val url = String.format(Config.GET_LATEST_ARTICLE, page)
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.getLatestList(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<ArticalGetResponse>() {
                    override fun onNext(t: ArticalGetResponse) {
                        data.value = t
                    }

                    override fun onError(e: Throwable) {
                        data.value = ArticalGetResponse(0, mutableListOf(), 0)
                    }

                    override fun onComplete() {

                    }

                })
        )
        return data
    }
}