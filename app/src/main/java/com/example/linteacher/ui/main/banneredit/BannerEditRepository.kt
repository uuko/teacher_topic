package com.example.linteacher.ui.main.banneredit

import androidx.lifecycle.MutableLiveData
import com.example.linteacher.api.RetrofitManager
import com.example.linteacher.api.pojo.banner.BannerGetResponse
import com.example.linteacher.api.pojo.banner.BannerUpdateRequest
import com.example.linteacher.api.pojo.banner.ResponseContent
import com.example.linteacher.util.Config
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class BannerEditRepository {
    fun getBannerList(): MutableLiveData<BannerGetResponse> {
        val data = MutableLiveData<BannerGetResponse>()
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.getBannerList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<BannerGetResponse>() {
                    override fun onNext(t: BannerGetResponse) {
                        data.value = t
                    }

                    override fun onError(e: Throwable) {
                        data.value = BannerGetResponse(mutableListOf(), 0)
                    }

                    override fun onComplete() {

                    }

                })
        )
        return data
    }

    fun deleteBanner(id: String): MutableLiveData<ResponseContent> {
        val data = MutableLiveData<ResponseContent>()
        val url = String.format(Config.DEL_BANNER, id)
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.deleteBanner(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<ResponseContent>() {
                    override fun onNext(t: ResponseContent) {
                        data.value = ResponseContent(Config.RESULT_OK)
                    }

                    override fun onError(e: Throwable) {
                        data.value = ResponseContent(e.toString())
                    }

                    override fun onComplete() {

                    }

                })
        )
        return data
    }
}