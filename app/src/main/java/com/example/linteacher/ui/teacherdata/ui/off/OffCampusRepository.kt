package com.example.linteacher.ui.teacherdata.ui.off

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.linteacher.api.RetrofitManager
import com.example.linteacher.api.pojo.UnitResponse
import com.example.linteacher.api.pojo.teacherdata.adamic.AcademicChangeVisibleRequest
import com.example.linteacher.api.pojo.teacherdata.off.*
import com.example.linteacher.util.Config
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class OffCampusRepository {

    fun getOneData(loginId: String): MutableLiveData<OffAllOneResponse> {
        val data = MutableLiveData<OffAllOneResponse>()
        val url=String.format(Config.GET_ONE_PRO,loginId);
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.getOneOffData(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<OffOneGetResponse>() {
                    override fun onNext(t: OffOneGetResponse) {
                        data.value = OffAllOneResponse(t, Config.RESULT_OK)
                    }

                    override fun onError(e: Throwable) {
                        data.value = OffAllOneResponse(OffOneGetResponse(), e.toString())
                    }

                    override fun onComplete() {

                    }

                })
        )
        return data
    }

    fun getData(loginId: String): MutableLiveData<OffGetAllResponse> {
        val data = MutableLiveData<OffGetAllResponse>()
        val url=String.format(Config.GET_PRO,loginId);
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.getOffData(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<OffGetResponse>>() {
                    override fun onNext(t: List<OffGetResponse>) {
                        data.value = OffGetAllResponse(t, Config.RESULT_OK)
                    }

                    override fun onError(e: Throwable) {
                        data.value = OffGetAllResponse(arrayListOf(), e.toString())
                    }

                    override fun onComplete() {

                    }

                })
        )
        return data
    }

    //
    fun deleteData(loginId: String): MutableLiveData<UnitResponse> {
        val data = MutableLiveData<UnitResponse>()
        val request = OffDeleteRequest(loginId.toInt())

        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.deleteOffData(request)
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
    fun postData(request: OffPostRequest): MutableLiveData<UnitResponse> {
        val data= MutableLiveData<UnitResponse>()
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.postOffData(Config.POST_PRO,request)
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
    fun updateData(request: OffUpdateRequest): MutableLiveData<UnitResponse> {
        val data= MutableLiveData<UnitResponse>()
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.updateOffData(Config.UPDATE_PRO,request)
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
            RetrofitManager.apiServices.changeVisibleAdemicEventData(Config.CHANGE_VISIBLE_PRO,request)
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