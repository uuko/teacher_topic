package com.example.linteacher.ui.teacherdata.ui.adamic

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.linteacher.api.RetrofitManager
import com.example.linteacher.api.pojo.UnitResponse
import com.example.linteacher.api.pojo.teacherdata.adamic.*
import com.example.linteacher.util.Config
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class AdemicEventRepository {
    //回傳一個老師的證照資料
    fun getOneData(loginId: String): MutableLiveData<AdemicEventAllOneResponse> {
        //得到data = AdemicEventAllOneResponse
        val data = MutableLiveData<AdemicEventAllOneResponse>()
        //url= /teacher/AdemicEventense/AdemicEventId/+loginId
        val url=String.format(Config.GET_ONE_EVE,loginId);
        RetrofitManager.compositeDisposable.add(
                RetrofitManager.apiServices.getOneAdemicEventData(url)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableObserver<AdemicEventResponse>() {
                            override fun onNext(t: AdemicEventResponse) {
                                //view model  data = AdemicEventAllOneResponse
                                data.value = AdemicEventAllOneResponse(t, Config.RESULT_OK)
                            }

                            override fun onError(e: Throwable) {
                                data.value = AdemicEventAllOneResponse(AdemicEventResponse(), e.toString())
                            }

                            override fun onComplete() {

                            }

                        })
        )
        return data
    }
    fun getData(loginId:String): MutableLiveData<AdemicEventAllResponse> {
        val data= MutableLiveData<AdemicEventAllResponse>()
        val url=String.format(Config.GET_EVE,loginId);
        RetrofitManager.compositeDisposable.add(
                RetrofitManager.apiServices.getAdemicEventData(url)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableObserver<List<AdemicEventResponse>>(){
                            override fun onNext(t: List<AdemicEventResponse>) {
                                data.value= AdemicEventAllResponse(t, Config.RESULT_OK)
                            }

                            override fun onError(e: Throwable) {
                                data.value= AdemicEventAllResponse(arrayListOf(),e.toString())
                            }

                            override fun onComplete() {

                            }

                        })
        )
        return data
    }
    fun deleteData(loginId: String): MutableLiveData<UnitResponse> {
        val data = MutableLiveData<UnitResponse>()
        val request = AdemicEventDeleteRequest(loginId.toInt())
        Log.d("EVEID", "deleteData: "+request.eveNumber)

        RetrofitManager.compositeDisposable.add(
                RetrofitManager.apiServices.deleteAdemicEventData(request)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableObserver<Unit>() {
                            override fun onNext(t: Unit) {
                                data.value = UnitResponse(Config.RESULT_OK)
                            }

                            override fun onError(e: Throwable) {
                                data.value = UnitResponse(e.toString())
                            }

                            override fun onComplete() {

                            }

                        })
        )
        return data
    }
    fun postData(request: AcademicPostRequest): MutableLiveData<UnitResponse> {
        //MutableLiveData->UnitResponse
        val data= MutableLiveData<UnitResponse>()
        RetrofitManager.compositeDisposable.add(
                RetrofitManager.apiServices.postAdemicEventData(Config.POST_EVE,request)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableObserver<Unit>(){
                            override fun onNext(t: Unit) {
                                //setValue ->觸發obsebever
                                data.value= UnitResponse(Config.RESULT_OK)
                            }

                            override fun onError(e: Throwable) {
                                data.value= UnitResponse(e.toString())
                            }

                            override fun onComplete() {

                            }

                        })
        )
        return data
    }
    fun updateData(request: AcademicPostRequest): MutableLiveData<UnitResponse> {
        Log.d("whaaaa", "updateData: "+request.eveNumber)
        val data= MutableLiveData<UnitResponse>()
        RetrofitManager.compositeDisposable.add(
                RetrofitManager.apiServices.updateAdemicEventData(Config.UPDATE_EVE,request)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableObserver<Unit>(){
                            override fun onNext(t: Unit) {
                                data.value= UnitResponse(Config.RESULT_OK)
                            }

                            override fun onError(e: Throwable) {
                                data.value= UnitResponse(e.toString())
                            }

                            override fun onComplete() {

                            }

                        })
        )
        return data
    }

}

