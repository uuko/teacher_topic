package com.example.linteacher.ui.main.announceinner

import androidx.lifecycle.MutableLiveData
import com.example.linteacher.api.RetrofitManager
import com.example.linteacher.api.pojo.artical.ArticleResponse
import com.example.linteacher.api.pojo.banner.BannerGetResponse
import com.example.linteacher.util.Config
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class AnnounceInnerRepository {
    fun getArticle(id: String): MutableLiveData<ArticleResponse> {
        val data = MutableLiveData<ArticleResponse>()
        val url = String.format(Config.GET_ARTICLE_ID, id)
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.getArticle(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<ArticleResponse>() {
                    override fun onNext(t: ArticleResponse) {
                        data.value = t
                    }

                    override fun onError(e: Throwable) {
                        data.value = ArticleResponse(result = e.toString())
                    }

                    override fun onComplete() {

                    }

                })
        )
        return data
    }
}