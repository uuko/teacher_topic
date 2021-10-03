package com.example.linteacher.ui.addarticle

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.linteacher.api.RetrofitManager
import com.example.linteacher.api.pojo.UnitResponse
import com.example.linteacher.api.pojo.artical.*
import com.example.linteacher.api.pojo.teacherdata.profile.TeacherProfileResponse
import com.example.linteacher.util.Config
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.util.*
import kotlin.collections.HashMap


class AddArticleRepository {
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
                        data.value = ArticleAllPicResponse(t, Config.RESULT_OK)
                    }

                    override fun onError(e: Throwable) {
                        data.value = ArticleAllPicResponse(ArticlePicResponse(), e.toString())
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

    fun postArticleData(request: ArticlePostRequest): MutableLiveData<ArticlePostResponse> {
        val data = MutableLiveData<ArticlePostResponse>()
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.postArticle(Config.POST_ARTICLE, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<ArticlePostResponse>() {
                    override fun onNext(t: ArticlePostResponse) {
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

    fun createPartFromString(string: String?): RequestBody {
        return string!!.toRequestBody(MultipartBody.FORM)
    }
}