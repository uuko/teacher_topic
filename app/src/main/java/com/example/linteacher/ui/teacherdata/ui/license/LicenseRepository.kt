package com.example.linteacher.ui.teacherdata.ui.license

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.linteacher.api.RetrofitManager
import com.example.linteacher.api.pojo.UnitResponse
import com.example.linteacher.api.pojo.teacherdata.adamic.AcademicChangeVisibleRequest
import com.example.linteacher.api.pojo.teacherdata.license.*
import com.example.linteacher.api.pojo.teacherdata.license.all.LicAllOneResponse
import com.example.linteacher.api.pojo.teacherdata.license.all.LicenseAllResponse
import com.example.linteacher.api.pojo.teacherdata.off.*
import com.example.linteacher.util.Config
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class LicenseRepository {
    //回傳一個老師的證照資料
    fun getOneData(loginId: String): MutableLiveData<LicAllOneResponse> {
        //得到data = LicAllOneResponse
        val data = MutableLiveData<LicAllOneResponse>()
        //url= /teacher/license/licId/+loginId
        val url=String.format(Config.GET_ONE_LICENSE,loginId);
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.getOneLicData(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<LicOneResponse>() {
                    override fun onNext(t: LicOneResponse) {
                        //view model  data = LicAllOneResponse
                        data.value = LicAllOneResponse(t, Config.RESULT_OK)
                    }

                    override fun onError(e: Throwable) {
                        data.value = LicAllOneResponse(LicOneResponse(), e.toString())
                    }

                    override fun onComplete() {

                    }

                })
        )
        return data
    }
    fun getData(loginId:String): MutableLiveData<LicenseAllResponse> {
        val data= MutableLiveData<LicenseAllResponse>()
        val url=String.format(Config.GET_LICENSE,loginId);
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.getLicenseData(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<LicenseResponse>>(){
                    override fun onNext(t: List<LicenseResponse>) {
                        data.value= LicenseAllResponse(t, Config.RESULT_OK)
                    }

                    override fun onError(e: Throwable) {
                        data.value= LicenseAllResponse(arrayListOf(),e.toString())
                    }

                    override fun onComplete() {

                    }

                })
        )
        return data
    }
    fun deleteData(loginId: String): MutableLiveData<UnitResponse> {
        val data = MutableLiveData<UnitResponse>()
        val request = LicDeleteRequest(loginId.toInt())

        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.deleteLicData(request)
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
    fun postData(request: LicPostRequest): MutableLiveData<UnitResponse> {
        //MutableLiveData->UnitResponse
        val data= MutableLiveData<UnitResponse>()
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.postLicData(Config.POST_LIC,request)
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
    fun updateData(request: LicUpdateRequest): MutableLiveData<UnitResponse> {
        val data= MutableLiveData<UnitResponse>()
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.updateLicData(Config.UPDATE_LIC,request)
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


    //改變item是否公開
    fun changeVisible(request: AcademicChangeVisibleRequest): MutableLiveData<UnitResponse>  {
        Log.d("whaaaa", "updateData: "+request.id)
        val data= MutableLiveData<UnitResponse>()
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.changeVisibleAdemicEventData(Config.CHANGE_VISIBLE_LIC,request)
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

