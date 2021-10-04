package com.example.linteacher.ui.articletag

import androidx.lifecycle.MutableLiveData
import com.example.linteacher.api.RetrofitManager
import com.example.linteacher.api.pojo.artical.ArticleListTagResponse
import com.example.linteacher.api.pojo.banner.BannerGetResponse
import com.example.linteacher.util.Config
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class ArticleTagRepository {
    fun getArticleAllTags(): MutableLiveData<ArticleListTagResponse> {
        val data = MutableLiveData<ArticleListTagResponse>()
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.getArticleAllTags(Config.GET_ARTICLE_TAGS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<ArticleListTagResponse>() {
                    override fun onNext(t: ArticleListTagResponse) {
                        data.value = t
                    }

                    override fun onError(e: Throwable) {
                        data.value = ArticleListTagResponse()
                    }

                    override fun onComplete() {

                    }

                })
        )
        return data
    }
}