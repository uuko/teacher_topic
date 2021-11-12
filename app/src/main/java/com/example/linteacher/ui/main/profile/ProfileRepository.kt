package com.example.linteacher.ui.main.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.linteacher.api.RetrofitManager
import com.example.linteacher.api.pojo.TeacherBaseResponse
import com.example.linteacher.api.pojo.teacherline.TeacherSecondLineResponse
import com.example.linteacher.util.Config
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class ProfileRepository {
    fun getTeacherData(id: String): MutableLiveData<TeacherBaseResponse> {
        val data = MutableLiveData<TeacherBaseResponse>()
        val url = String.format(Config.GET_TEACHER_BASE, id)
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.getTeacherBase(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<TeacherBaseResponse>() {
                    override fun onNext(t: TeacherBaseResponse) {
                        data.value = t
                        Log.d("getTeacherLineData", "onNext: " + t.tchName)

                    }

                    override fun onError(e: Throwable) {
                        data.value = TeacherBaseResponse(error = e.toString())
                        Log.d("getTeacherLineData", "onError: ")

                    }

                    override fun onComplete() {

                    }

                })
        )
        return data
    }
}