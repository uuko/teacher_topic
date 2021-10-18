package com.example.linteacher.ui.teacherdata.ui.technologytrans.editinner

import androidx.lifecycle.MutableLiveData
import com.example.linteacher.api.RetrofitManager
import com.example.linteacher.api.pojo.UnitResponse
import com.example.linteacher.api.pojo.teacherdata.techtransfer.*
import com.example.linteacher.util.Config
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class TechEditInnnerRepository {

    fun getData(loginId:String): MutableLiveData<TechInnerResponse> {
        val data= MutableLiveData<TechInnerResponse>()
        val url=String.format(Config.GET_TECHCHANGE_ONE,loginId);
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.getTechSecondOne(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<TechInnerResponse>(){
                    override fun onNext(t: TechInnerResponse) {
                        t.result=Config.RESULT_OK
                        data.value= t
                    }

                    override fun onError(e: Throwable) {
                        data.value= TechInnerResponse(result = e.toString())
                    }

                    override fun onComplete() {

                    }

                })
        )
        return data
    }

    fun deleteData(request: TechInnerDeleteRequest): MutableLiveData<UnitResponse> {
        val data= MutableLiveData<UnitResponse>()

        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.deleteTechData(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Unit>(){
                    override fun onNext(t: Unit) {
                        data.value=UnitResponse(Config.RESULT_OK)
                    }

                    override fun onError(e: Throwable) {
                        data.value=UnitResponse(e.toString())
                    }

                    override fun onComplete() {

                    }
                })
        )
        return data
    }

    fun postData(request: TechInnerPostOneRequest): MutableLiveData<UnitResponse> {
        val data= MutableLiveData<UnitResponse>()
        val url=Config.POST_TECHCHANGE_ONE
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.postTechSecondOne(url,request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<TechPostResponse>(){
                    override fun onNext(t: TechPostResponse) {
                        data.value=UnitResponse(Config.RESULT_OK)
                    }

                    override fun onError(e: Throwable) {
                        data.value=UnitResponse(e.toString())
                    }

                    override fun onComplete() {

                    }

                })
        )
        return data
    }


    fun updateData(request: TechInnerUpdateRequest): MutableLiveData<UnitResponse> {
        val data= MutableLiveData<UnitResponse>()
        val url=Config.UPDATE_TECHCHANGE_ONE
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.updateTechSecondOne(url,request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Unit>(){
                    override fun onNext(t: Unit) {
                        data.value=UnitResponse(Config.RESULT_OK)
                    }

                    override fun onError(e: Throwable) {
                        data.value=UnitResponse(e.toString())
                    }

                    override fun onComplete() {

                    }

                })
        )
        return data
    }
}