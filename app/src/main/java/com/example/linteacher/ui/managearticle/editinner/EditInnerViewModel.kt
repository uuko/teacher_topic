package com.example.linteacher.ui.managearticle.editinner

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.linteacher.api.pojo.UnitResponse
import com.example.linteacher.api.pojo.artical.*
import com.example.linteacher.api.pojo.banner.BannerGetResponse
import com.example.linteacher.api.pojo.banner.BannerUpdateRequest
import com.example.linteacher.api.pojo.banner.ResponseContent
import com.example.linteacher.ui.addarticle.AddArticleRequest
import com.example.linteacher.ui.addarticle.UrlDrawableResponse
import com.example.linteacher.ui.main.announce.Content
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.InputStream
import java.net.URL


class EditInnerViewModel(val dataModel: EditRepository) : ViewModel() {
    var data = MutableLiveData<List<UrlDrawableResponse>>()

    fun getArticle(id: String): MutableLiveData<ArticleResponse> {
        return dataModel.getArticle(id)
    }

    fun uploadFile(file: File, request: AddArticleRequest): MutableLiveData<ArticleAllPicResponse> {
        return dataModel.uploadFile(file, request)
    }

    fun updateArticle(request: ArticleUpdateRequest): MutableLiveData<ArticlePostResponse> {
        return dataModel.updateArticleData(request)
    }

    fun deleteArticle(request: List<DeleteArticleRequest>): MutableLiveData<UnitResponse> {
        return dataModel.deleteArticle(request)
    }

    fun getBannerList(): MutableLiveData<BannerGetResponse> {
        return dataModel.getBannerList()
    }




    fun updateBanner(request: BannerUpdateRequest): MutableLiveData<ResponseContent> {
        return dataModel.updateBannerData(request)
    }

    private fun handleOneContentData(
        articleContent: ArrayList<UrlDrawableResponse>,
        listener: FinishedListener
    ) {

        Observable.fromIterable(articleContent)
            .filter { it.isDrawable }
            .flatMap {
                return@flatMap Observable.fromArray(
                    loadImageFromURL(
                        it.picUrl,
                        it.picUrl,
                        it.index
                    )
                )
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<DrawableIndex?> {
                override fun onSubscribe(d: Disposable) {
                    Log.d("handleOneContentData", "onSubscribe: ")
                }

                override fun onNext(t: DrawableIndex) {
                    val name = articleContent[t.index].picName.split("_")
                    articleContent[t.index].picName = name[name.size - 1]
                    articleContent[t.index].drawable = t.drawable

                    Log.d("handleOneContentData", "onNext: " + t)
                }

                override fun onError(e: Throwable) {
                    Log.d("handleOneContentData", "onError: ")
                }

                override fun onComplete() {
                    Log.d("handleOneContentData", "onComplete: ")

                    listener.isFinished(articleContent)
                }


            })

    }

    interface FinishedListener {
        fun isFinished(articleContent: ArrayList<UrlDrawableResponse>)
    }

    fun loadImageFromURL(url: String?, name: String?, index: Int): DrawableIndex? {
        return try {
            val inputStream: InputStream = URL(url).content as InputStream

            DrawableIndex(index, Drawable.createFromStream(inputStream, name))
        } catch (e: Exception) {
            null
        }
    }
}