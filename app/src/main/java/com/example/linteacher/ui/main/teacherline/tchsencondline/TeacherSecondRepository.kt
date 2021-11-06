package com.example.linteacher.ui.main.teacherline.tchsencondline

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.linteacher.api.RetrofitManager
import com.example.linteacher.api.pojo.artical.ArticleResponse
import com.example.linteacher.api.pojo.teacherline.TeacherSecondLineResponse
import com.example.linteacher.util.Config
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class TeacherSecondRepository {
    fun getTeacherLineData(id: String): MutableLiveData<TeacherSecondLineResponse> {
        val data = MutableLiveData<TeacherSecondLineResponse>()
        val url = String.format(Config.GET_TEACHER_LINE_INNER, id)
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.getTeacherLineInnerList(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<TeacherSecondLineResponse>() {
                    override fun onNext(t: TeacherSecondLineResponse) {
                        data.value = t
                        Log.d("lineOnetec", "onNext: "+t.licList)

                    }

                    override fun onError(e: Throwable) {
                        data.value = TeacherSecondLineResponse(result = e.toString())
                        Log.d("lineOnetec", "onError: ")

                    }

                    override fun onComplete() {

                    }

                })
        )
        return data
    }
}