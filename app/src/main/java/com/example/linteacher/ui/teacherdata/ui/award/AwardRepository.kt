package com.example.linteacher.ui.teacherdata.ui.award

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.linteacher.api.RetrofitManager
import com.example.linteacher.api.pojo.UnitResponse
import com.example.linteacher.api.pojo.teacherdata.award.*

import com.example.linteacher.util.Config
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class AwardRepository {
    //回傳一個老師的證照資料
    fun getOneData(loginId: String): MutableLiveData<AwardAllOneResponse> {
        //得到data = AwardAllOneResponse
        val data = MutableLiveData<AwardAllOneResponse>()
        //url= /teacher/Awardense/AwardId/+loginId
        val url=String.format(Config.GET_ONE_AWA,loginId);
        RetrofitManager.compositeDisposable.add(
                RetrofitManager.apiServices.getOneAwardData(url)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableObserver<AwardResponse>() {
                            override fun onNext(t: AwardResponse) {
                                //view model  data = AwardAllOneResponse
                                data.value = AwardAllOneResponse(t, Config.RESULT_OK)
                            }

                            override fun onError(e: Throwable) {
                                data.value = AwardAllOneResponse(AwardResponse(), e.toString())
                            }

                            override fun onComplete() {

                            }

                        })
        )
        return data
    }
    fun getData(loginId:String): MutableLiveData<AwardAllResponse> {
        val data= MutableLiveData<AwardAllResponse>()
        val url=String.format(Config.GET_AWA,loginId);
        RetrofitManager.compositeDisposable.add(
                RetrofitManager.apiServices.getAwardData(url)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableObserver<List<AwardResponse>>(){
                            override fun onNext(t: List<AwardResponse>) {
                                data.value= AwardAllResponse(t, Config.RESULT_OK)
                            }

                            override fun onError(e: Throwable) {
                                data.value= AwardAllResponse(arrayListOf(),e.toString())
                            }

                            override fun onComplete() {

                            }

                        })
        )
        return data
    }
    fun deleteData(loginId: String): MutableLiveData<UnitResponse> {
        val data = MutableLiveData<UnitResponse>()
        val request = AwardDeleteRequest(loginId.toInt())
        Log.d("awaID", "deleteData: "+request.awaId)

        RetrofitManager.compositeDisposable.add(
                RetrofitManager.apiServices.deleteAwardData(request)
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
    fun postData(request: AwardPostRequest): MutableLiveData<UnitResponse> {
        //MutableLiveData->UnitResponse
        val data= MutableLiveData<UnitResponse>()
        RetrofitManager.compositeDisposable.add(
                RetrofitManager.apiServices.postAwardData(Config.POST_AWA,request)
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
    fun updateData(request: AwardUpdateRequest): MutableLiveData<UnitResponse> {
        Log.d("whaaaa", "updateData: "+request.awaName)
        val data= MutableLiveData<UnitResponse>()
        RetrofitManager.compositeDisposable.add(
                RetrofitManager.apiServices.updateAwardData(Config.UPDATE_AWA,request)
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