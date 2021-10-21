package com.example.linteacher.ui.teacherdata.ui.patent

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.linteacher.api.RetrofitManager
import com.example.linteacher.api.pojo.UnitResponse
import com.example.linteacher.api.pojo.teacherdata.patent.*
import com.example.linteacher.util.Config
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class PatentRepository {

//回傳一個老師的證照資料
fun getOneData(loginId: String): MutableLiveData<PatentAllOneResponse> {
    //得到data = PatentAllOneResponse
    val data = MutableLiveData<PatentAllOneResponse>()
    //url= /teacher/Patentense/PatentId/+loginId
    val url=String.format(Config.GET_ONE_PANTENT,loginId);
    RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.getOnePatentData(url)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableObserver<PatentResponse>() {
                        override fun onNext(t: PatentResponse) {
                            //view model  data = PatentAllOneResponse
                            data.value = PatentAllOneResponse(t, Config.RESULT_OK)
                        }

                        override fun onError(e: Throwable) {
                            data.value = PatentAllOneResponse(PatentResponse(), e.toString())
                        }

                        override fun onComplete() {

                        }

                    })
    )
    return data
}
fun getData(loginId:String): MutableLiveData<PatentAllResponse> {
    val data= MutableLiveData<PatentAllResponse>()
    val url=String.format(Config.GET_PANTENT,loginId);
    RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.getPatentData(url)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableObserver<List<PatentResponse>>(){
                        override fun onNext(t: List<PatentResponse>) {
                            data.value= PatentAllResponse(t, Config.RESULT_OK)
                        }

                        override fun onError(e: Throwable) {
                            data.value= PatentAllResponse(arrayListOf(),e.toString())
                        }

                        override fun onComplete() {

                        }

                    })
    )
    return data
}
fun deleteData(loginId: String): MutableLiveData<UnitResponse> {
    val data = MutableLiveData<UnitResponse>()
    val request = PatentDeleteRequest(loginId.toInt())

    RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.deletePatentData(request)
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
fun postData(request: PatentPostRequest): MutableLiveData<UnitResponse> {
    //MutableLiveData->UnitResponse
    val data= MutableLiveData<UnitResponse>()
    RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.postPatentData(Config.POST_PANTENT,request)
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
fun updateData(request: PatentUpdateRequest): MutableLiveData<UnitResponse> {
    val data= MutableLiveData<UnitResponse>()
    RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.updatePatentData(Config.UPDATE_PANTENT,request)
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