package com.example.linteacher.ui.main.teacherline

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.linteacher.api.RetrofitManager
import com.example.linteacher.api.pojo.teacherline.TeacherLineResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class TeacherLineRepository {
    fun getTeacherLineList(): MutableLiveData<TeacherLineAllResponse> {
        val data=MutableLiveData<TeacherLineAllResponse>()
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.getTeacherLineList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<TeacherLineResponse>>(){
                    override fun onNext(t: List<TeacherLineResponse>)
                    {
                        Log.d("getTeacherLineList", "onNext: ")
                        data.value=TeacherLineAllResponse(t)
                    }

                    override fun onError(e: Throwable) {
                        Log.d("getTeacherLineList", "onError: ")
                        data.value=TeacherLineAllResponse(mutableListOf(),e.toString())
                    }

                    override fun onComplete() {

                    }

                })
        )
        return data
    }
}
