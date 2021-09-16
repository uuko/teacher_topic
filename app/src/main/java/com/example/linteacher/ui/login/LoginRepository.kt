package com.example.linteacher.ui.login

import androidx.lifecycle.MutableLiveData
import com.example.linteacher.api.RetrofitManager
import com.example.linteacher.api.pojo.TeacherLineResponse
import com.example.linteacher.api.pojo.login.LoginAllResponse
import com.example.linteacher.api.pojo.login.LoginRequest
import com.example.linteacher.api.pojo.login.LoginResponse
import com.example.linteacher.ui.main.teacherline.TeacherLineAllResponse
import com.example.linteacher.util.Config
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class LoginRepository {
    fun login(request:LoginRequest): MutableLiveData<LoginAllResponse> {
        val data= MutableLiveData<LoginAllResponse>()
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.login(Config.LOGIN_URL,request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<LoginResponse>(){
                    override fun onNext(t: LoginResponse) {
                        data.value= LoginAllResponse(t.tchNumber)
                    }

                    override fun onError(e: Throwable) {
                        data.value= LoginAllResponse(-1,e.toString())
                    }

                    override fun onComplete() {

                    }

                })
        )
        return data
    }
}