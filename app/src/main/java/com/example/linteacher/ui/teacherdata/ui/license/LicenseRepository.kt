package com.example.linteacher.ui.teacherdata.ui.license

import androidx.lifecycle.MutableLiveData
import com.example.linteacher.api.RetrofitManager
import com.example.linteacher.api.pojo.teacherdata.license.LicenseResponse
import com.example.linteacher.api.pojo.teacherdata.license.all.LicenseAllResponse
import com.example.linteacher.api.pojo.teacherdata.profile.TeacherProfileAllResponse
import com.example.linteacher.api.pojo.teacherdata.profile.TeacherProfileResponse
import com.example.linteacher.util.Config
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class LicenseRepository {

    fun getTeacherLicenseAllData(loginId:String): MutableLiveData<LicenseAllResponse> {
        val data= MutableLiveData<LicenseAllResponse>()
        val url=String.format(Config.GET_LICENSE,loginId);
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.getLicenseData(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<LicenseResponse>>(){
                    override fun onNext(t: List<LicenseResponse>) {
                        data.value= LicenseAllResponse(t, Config.RESULT_OK)
                    }

                    override fun onError(e: Throwable) {
                        data.value= LicenseAllResponse(arrayListOf(),e.toString())
                    }

                    override fun onComplete() {

                    }

                })
        )
        return data
    }
}