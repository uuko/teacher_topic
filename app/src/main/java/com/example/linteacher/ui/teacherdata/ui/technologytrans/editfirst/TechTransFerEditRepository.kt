package com.example.linteacher.ui.teacherdata.ui.technologytrans.editfirst

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.linteacher.api.RetrofitManager
import com.example.linteacher.api.pojo.UnitResponse
import com.example.linteacher.api.pojo.teacherdata.techtransfer.*
import com.example.linteacher.util.Config
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class TechTransFerEditRepository {
    fun getData(techId:String): MutableLiveData<TechTransFerSecondAllResponse> {
        val data= MutableLiveData<TechTransFerSecondAllResponse>()
        val url=String.format(Config.GET_TECHCHANGE_FIRST_BY_TECID,techId);
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.getTechSecondList(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<TechTransFerSecondResponse>() {
                    override fun onNext(t: TechTransFerSecondResponse) {
                        Log.d("getData", "onNext: "+t.tecNumber)
                        Log.d("getData", "onNext: "+t.tecTransfer)
                        data.value = TechTransFerSecondAllResponse(t, Config.RESULT_OK)
                    }

                    override fun onError(e: Throwable) {
                        data.value = TechTransFerSecondAllResponse(error = e.toString())
                    }

                    override fun onComplete() {

                    }

                })
        )
        return data
    }

    fun updateData(request: TechTransFerUpdateRequest): MutableLiveData<UnitResponse> {
        val data= MutableLiveData<UnitResponse>()
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.updateTechFist(Config.UPDATE_TECHCHANGE_FIRST,request)
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

    fun deleteData(request:TechInnerDeleteRequest): MutableLiveData<UnitResponse>{
        val data= MutableLiveData<UnitResponse>()

        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.deleteTechFirstData(request)
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
    fun postData(request: TechTransFerPostRequest): MutableLiveData<UnitResponse> {
        val data= MutableLiveData<UnitResponse>()
        val url=Config.POST_TECHCHANGE_FIRST;

        Log.d("Aadsa", "POST_TECHCHANGE_FIRST: "+url)
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.postTechFist(url,request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<TechPostResponse>(){
                    override fun onNext(t: TechPostResponse) {
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