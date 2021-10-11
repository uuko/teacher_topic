package com.example.linteacher.ui.teacherdata.ui.gov

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.linteacher.api.RetrofitManager
import com.example.linteacher.api.pojo.UnitResponse
import com.example.linteacher.api.pojo.teacherdata.gov.*
import com.example.linteacher.api.pojo.teacherdata.gov.GovAllResponse
import com.example.linteacher.api.pojo.teacherdata.gov.GovDeleteRequest
import com.example.linteacher.api.pojo.teacherdata.gov.GovPostRequest
import com.example.linteacher.api.pojo.teacherdata.gov.GovUpdateRequest
import com.example.linteacher.util.Config
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class GovRepository {
    //回傳一個老師的證照資料
    fun getOneData(loginId: String): MutableLiveData<GovAllOneResponse> {
        //得到data = GovAllOneResponse
        val data = MutableLiveData<GovAllOneResponse>()
        //url= /teacher/Govense/GovId/+loginId
        val url=String.format(Config.GET_ONE_GOV,loginId);
        RetrofitManager.compositeDisposable.add(
                RetrofitManager.apiServices.getOneGovData(url)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableObserver<GovResponse>() {
                            override fun onNext(t: GovResponse) {
                                //view model  data = GovAllOneResponse
                                data.value = GovAllOneResponse(t, Config.RESULT_OK)
                            }

                            override fun onError(e: Throwable) {
                                data.value = GovAllOneResponse(GovResponse(), e.toString())
                            }

                            override fun onComplete() {

                            }

                        })
        )
        return data
    }
    fun getData(loginId:String): MutableLiveData<GovAllResponse> {
        val data= MutableLiveData<GovAllResponse>()
        val url=String.format(Config.GET_GOV,loginId);
        RetrofitManager.compositeDisposable.add(
                RetrofitManager.apiServices.getGovData(url)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableObserver<List<GovResponse>>(){
                            override fun onNext(t: List<GovResponse>) {
                                data.value= GovAllResponse(t, Config.RESULT_OK)
                            }

                            override fun onError(e: Throwable) {
                                data.value= GovAllResponse(arrayListOf(),e.toString())
                            }

                            override fun onComplete() {

                            }

                        })
        )
        return data
    }
    fun deleteData(loginId: String): MutableLiveData<UnitResponse> {
        val data = MutableLiveData<UnitResponse>()
        val request = GovDeleteRequest(loginId.toInt())
        RetrofitManager.compositeDisposable.add(
                RetrofitManager.apiServices.deleteGovData(request)
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
    fun postData(request: GovPostRequest): MutableLiveData<UnitResponse> {
        //MutableLiveData->UnitResponse
        val data= MutableLiveData<UnitResponse>()
        RetrofitManager.compositeDisposable.add(
                RetrofitManager.apiServices.postGovData(Config.POST_GOV,request)
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
    fun updateData(request: GovUpdateRequest): MutableLiveData<UnitResponse> {
        val data= MutableLiveData<UnitResponse>()
        RetrofitManager.compositeDisposable.add(
                RetrofitManager.apiServices.updateGovData(Config.UPDATE_GOV,request)
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
    }}