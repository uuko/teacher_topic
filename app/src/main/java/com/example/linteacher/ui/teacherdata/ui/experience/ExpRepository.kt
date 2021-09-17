package com.example.linteacher.ui.teacherdata.ui.experience

import androidx.lifecycle.MutableLiveData
import com.example.linteacher.api.RetrofitManager
import com.example.linteacher.api.pojo.UnitResponse
import com.example.linteacher.api.pojo.admin.addteacher.AddTeacherAllResponse
import com.example.linteacher.api.pojo.admin.addteacher.AddTeacherRequest
import com.example.linteacher.api.pojo.teacherdata.ExpGetAllResponse
import com.example.linteacher.api.pojo.teacherdata.exp.*
import com.example.linteacher.util.Config
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class ExpRepository {
    fun updateExp(request: ExpUpdateRequest): MutableLiveData<UnitResponse> {
        val data= MutableLiveData<UnitResponse>()
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.updateExpData(Config.UPDATE_EXP,request)
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
    fun addExp(request: ExpAddRequest): MutableLiveData<UnitResponse> {
        val data= MutableLiveData<UnitResponse>()
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.postExpData(Config.POST_EXP,request)
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
    fun deleteExp(request:ExpDeleteRequest): MutableLiveData<UnitResponse> {
        val data= MutableLiveData<UnitResponse>()
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.deleteExpData(request)
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
    fun getExpData(loginId:String): MutableLiveData<ExpGetAllResponse> {
        val data= MutableLiveData<ExpGetAllResponse>()
        val url=String.format(Config.GET_EXP,loginId);
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.getExpData(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<ExpGetResponse>>(){
                    override fun onNext(t: List<ExpGetResponse>) {
                        data.value= ExpGetAllResponse(t)
                    }

                    override fun onError(e: Throwable) {
                        data.value= ExpGetAllResponse(arrayListOf(),e.toString())
                    }

                    override fun onComplete() {

                    }

                })
        )
        return data
    }


    fun getExpDataByTchNumber(expNumber:String): MutableLiveData<ExpOneGetResponse> {
        val data= MutableLiveData<ExpOneGetResponse>()
        val url=String.format(Config.GET_EXP_BY_EXP_NUMBER,expNumber);
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.getExpDataByExpNumber(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<ExpOneGetResponse>(){
                    override fun onNext(t: ExpOneGetResponse) {
                        data.value= t
                    }

                    override fun onError(e: Throwable) {
                        data.value= ExpOneGetResponse()
                    }

                    override fun onComplete() {

                    }

                })
        )
        return data
    }
}