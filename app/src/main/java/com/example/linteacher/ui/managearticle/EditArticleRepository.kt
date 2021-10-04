package com.example.linteacher.ui.managearticle

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.linteacher.api.RetrofitManager
import com.example.linteacher.api.pojo.UnitResponse
import com.example.linteacher.api.pojo.artical.ArticalGetResponse
import com.example.linteacher.api.pojo.artical.DeleteArticleRequest
import com.example.linteacher.util.Config
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class EditArticleRepository {

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