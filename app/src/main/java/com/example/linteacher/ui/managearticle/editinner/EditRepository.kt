package com.example.linteacher.ui.managearticle.editinner

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.linteacher.api.RetrofitManager
import com.example.linteacher.api.pojo.UnitResponse
import com.example.linteacher.api.pojo.artical.*
import com.example.linteacher.ui.addarticle.AddArticleRequest
import com.example.linteacher.util.BaseRepository
import com.example.linteacher.util.Config
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class EditRepository : BaseRepository() {
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

    fun uploadFile(file: File, request: AddArticleRequest): MutableLiveData<ArticleAllPicResponse> {
        val data = MutableLiveData<ArticleAllPicResponse>()
        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        val partMap = HashMap<String, RequestBody>()
        partMap["file\"; filename=\"${file.name}"] = requestBody

        if (request.haveArticleId) {
            val articleId: RequestBody =
                createPartFromString(request.articleId.toString())
            partMap["articleId"] = articleId
        }
        if (request.haveBanner) {
            val isBanner: RequestBody =
                createPartFromString(request.isBanner.toString())
            partMap["isBanner"] = isBanner
        }
        if (request.haveExist) {
            val exist: RequestBody =
                createPartFromString(request.exist.toString())
            partMap["exist"] = exist
        }
        val url = String.format(Config.POST_ARTICLE_PIC, partMap);
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.uploadArticlePic(url, partMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<ArticlePicResponse>() {
                    override fun onNext(t: ArticlePicResponse) {
                        data.value = ArticleAllPicResponse(t, picName = file.name, Config.RESULT_OK)
                    }

                    override fun onError(e: Throwable) {
                        data.value = ArticleAllPicResponse(
                            ArticlePicResponse(),
                            picName = file.name,
                            e.toString()
                        )
                    }

                    override fun onComplete() {

                    }

                })
        )
        return data

    }

    fun updateArticleData(request: ArticleUpdateRequest): MutableLiveData<ArticlePostResponse> {
        val data = MutableLiveData<ArticlePostResponse>()
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.updateArticle(Config.UPDATE_ARTICLE, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<String>() {
                    override fun onNext(t: String) {
                        data.value = ArticlePostResponse(Config.RESULT_OK)
                    }

                    override fun onError(e: Throwable) {
                        data.value = ArticlePostResponse(e.toString())
                    }

                    override fun onComplete() {

                    }

                })
        )
        return data
    }

    fun deleteArticle(request: List<DeleteArticleRequest>): MutableLiveData<UnitResponse> {
        val data = MutableLiveData<UnitResponse>()
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.deleteArticle(Config.DEL_ARTICLE, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Unit>() {
                    override fun onNext(t: Unit) {
                        data.value = UnitResponse(Config.RESULT_OK)
                    }

                    override fun onError(e: Throwable) {
                        Log.d("onError", "onError: $e")
                        data.value = UnitResponse(e.toString())
                    }

                    override fun onComplete() {

                    }

                })
        )
        return data
    }

}