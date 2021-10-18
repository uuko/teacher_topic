package com.example.linteacher.ui.teacherdata.ui.paper

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.linteacher.api.RetrofitManager
import com.example.linteacher.api.pojo.UnitResponse
import com.example.linteacher.api.pojo.teacherdata.adamic.AcademicChangeVisibleRequest
import com.example.linteacher.api.pojo.teacherdata.license.*
import com.example.linteacher.api.pojo.teacherdata.license.all.LicAllOneResponse
import com.example.linteacher.api.pojo.teacherdata.license.all.LicenseAllResponse
import com.example.linteacher.api.pojo.teacherdata.paper.*
import com.example.linteacher.util.Config
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class PaperRepository {
    fun getOneData(loginId: String): MutableLiveData<PaperOneAllResponse> {
        val data = MutableLiveData<PaperOneAllResponse>()
        val url=String.format(Config.GET_ONE_PAPER,loginId);
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.getPaperOneData(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<PaperOneResponse>() {
                    override fun onNext(t: PaperOneResponse) {
                        data.value = PaperOneAllResponse(t, Config.RESULT_OK)
                    }

                    override fun onError(e: Throwable) {
                        data.value = PaperOneAllResponse(PaperOneResponse(), e.toString())
                    }

                    override fun onComplete() {

                    }

                })
        )
        return data
    }
    fun getData(loginId:String): MutableLiveData<PaperAllResponse> {
        val data= MutableLiveData<PaperAllResponse>()
        val url=String.format(Config.GET_PAPER,loginId);
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.getPaperData(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<PaperResponse>>(){
                    override fun onNext(t: List<PaperResponse>) {
                        data.value= PaperAllResponse(t, Config.RESULT_OK)
                    }

                    override fun onError(e: Throwable) {
                        data.value= PaperAllResponse(arrayListOf(),e.toString())
                    }

                    override fun onComplete() {

                    }

                })
        )
        return data
    }
    fun deleteData(loginId: String): MutableLiveData<UnitResponse> {
        val data = MutableLiveData<UnitResponse>()
        val request = PaperDeleteRequest(loginId.toInt())

        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.deletePaperData(request)
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
    fun postData(request: PaperPostRequest): MutableLiveData<UnitResponse> {
        val data= MutableLiveData<UnitResponse>()
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.postPaperData(Config.POST_PAPER,request)
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
    fun updateData(request: PaperUpdateRequest): MutableLiveData<UnitResponse> {
        val data= MutableLiveData<UnitResponse>()
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.updatePaperData(Config.UPDATE_PAPER,request)
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
            RetrofitManager.apiServices.changeVisibleAdemicEventData(Config.CHANGE_VISIBLE_PAPER,request)
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