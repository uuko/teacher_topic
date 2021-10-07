package com.example.linteacher.ui.teacherdata.ui.journal

import androidx.lifecycle.MutableLiveData
import com.example.linteacher.api.RetrofitManager
import com.example.linteacher.api.pojo.UnitResponse
import com.example.linteacher.api.pojo.teacherdata.dis.*
import com.example.linteacher.util.Config
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class JournalRepository {


    fun getDisList(loginId: String): MutableLiveData<DisGetAllResponse> {
        val data = MutableLiveData<DisGetAllResponse>()
        val url = String.format(Config.GET_DIS_BY_LOGINID, loginId);
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.getDisList(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<DisGetResponse>>() {
                    override fun onNext(t: List<DisGetResponse>) {
                        data.value = DisGetAllResponse(t, Config.RESULT_OK)
                    }

                    override fun onError(e: Throwable) {
                        data.value = DisGetAllResponse(arrayListOf(), e.toString())
                    }

                    override fun onComplete() {

                    }

                })
        )
        return data
    }

    fun getDisListById(disId: String): MutableLiveData<DisGetOneResponse> {
        val data = MutableLiveData<DisGetOneResponse>()
        val url = String.format(Config.GET_DIS_DISID, disId);
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.getDisListById(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<DisGetOneResponse>() {
                    override fun onNext(t: DisGetOneResponse) {
                        t.result = Config.RESULT_OK
                        data.value = t
                    }

                    override fun onError(e: Throwable) {
                        data.value = DisGetOneResponse(result = e.toString())
                    }

                    override fun onComplete() {

                    }

                })
        )
        return data
    }

    fun postDisData(request: DisPostRequest): MutableLiveData<UnitResponse> {
        val data = MutableLiveData<UnitResponse>()

        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.postDisData(Config.POST_DIS, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Unit>() {
                    override fun onNext(t: Unit) {
                        data.value = UnitResponse(Config.RESULT_OK)
                    }

                    override fun onError(e: Throwable) {
                        data.value = UnitResponse(result = e.toString())
                    }

                    override fun onComplete() {

                    }

                })
        )
        return data
    }

    fun updateDisData(request: DisUpdateRequest): MutableLiveData<UnitResponse> {
        val data = MutableLiveData<UnitResponse>()

        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.updateDisData(Config.UPDATE_DIS, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Unit>() {
                    override fun onNext(t: Unit) {
                        data.value = UnitResponse(Config.RESULT_OK)
                    }

                    override fun onError(e: Throwable) {
                        data.value = UnitResponse(result = e.toString())
                    }

                    override fun onComplete() {

                    }

                })
        )
        return data
    }

    fun deleteDisData(request: DisDelRequest): MutableLiveData<UnitResponse> {
        val data = MutableLiveData<UnitResponse>()

        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.deleteDisData(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Unit>() {
                    override fun onNext(t: Unit) {
                        data.value = UnitResponse(Config.RESULT_OK)
                    }

                    override fun onError(e: Throwable) {
                        data.value = UnitResponse(result = e.toString())
                    }

                    override fun onComplete() {

                    }

                })
        )
        return data
    }
}