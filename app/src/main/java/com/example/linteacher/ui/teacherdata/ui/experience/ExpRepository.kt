package com.example.linteacher.ui.teacherdata.ui.experience

import androidx.lifecycle.MutableLiveData
import com.example.linteacher.api.RetrofitManager
import com.example.linteacher.api.pojo.UnitResponse
import com.example.linteacher.api.pojo.admin.addteacher.AddTeacherAllResponse
import com.example.linteacher.api.pojo.admin.addteacher.AddTeacherRequest
import com.example.linteacher.api.pojo.teacherdata.ExpGetAllResponse
import com.example.linteacher.api.pojo.teacherdata.exp.ExpAddRequest
import com.example.linteacher.api.pojo.teacherdata.exp.ExpGetResponse
import com.example.linteacher.util.Config
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class ExpRepository {
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
}