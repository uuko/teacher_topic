package com.example.linteacher.ui.admin.adminaddteacher

import androidx.lifecycle.MutableLiveData
import com.example.linteacher.api.RetrofitManager
import com.example.linteacher.api.pojo.admin.addteacher.AddTeacherAllResponse
import com.example.linteacher.api.pojo.admin.addteacher.AddTeacherRequest
import com.example.linteacher.util.Config
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class AddTeacherRepository {

    fun registerTeacher(request: AddTeacherRequest): MutableLiveData<AddTeacherAllResponse> {
        val data= MutableLiveData<AddTeacherAllResponse>()
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.loginApiServices.adminRegisterTeacher(Config.ADMIN_REGISTER_,request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Unit>(){
                    override fun onNext(t: Unit) {
                        data.value= AddTeacherAllResponse(Config.RESULT_OK)
                    }

                    override fun onError(e: Throwable) {
                        data.value= AddTeacherAllResponse(e.toString())
                    }

                    override fun onComplete() {

                    }

                })
        )
        return data
    }
}