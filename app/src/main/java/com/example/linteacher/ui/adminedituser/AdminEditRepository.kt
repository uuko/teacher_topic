package com.example.linteacher.ui.adminedituser

import androidx.lifecycle.MutableLiveData
import com.example.linteacher.api.RetrofitManager
import com.example.linteacher.api.pojo.admin.changeautority.AdminChangeAuthorityRequest
import com.example.linteacher.api.pojo.admin.changeautority.AdminChangeAuthorityResponse
import com.example.linteacher.api.pojo.admin.list.AdminListTeacherAllResponse
import com.example.linteacher.api.pojo.admin.list.AdminListTeacherResponse
import com.example.linteacher.util.Config
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class AdminEditRepository {
    fun adminListUsers(): MutableLiveData<AdminListTeacherAllResponse> {
        val data= MutableLiveData<AdminListTeacherAllResponse>()
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.adminListUser(Config.ADMIN_LIST_USER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<AdminListTeacherResponse>>(){
                    override fun onNext(t: List<AdminListTeacherResponse>) {
                        for (l :AdminListTeacherResponse in t){
                            l.isVisible = l.grade == "B"
                        }
                        data.value= AdminListTeacherAllResponse(t)
                    }

                    override fun onError(e: Throwable) {
                        data.value= AdminListTeacherAllResponse(mutableListOf(),e.toString())
                    }

                    override fun onComplete() {

                    }

                })
        )
        return data
    }

    fun changeUserAuthority(changeAuthorityRequest: AdminChangeAuthorityRequest): MutableLiveData<AdminChangeAuthorityResponse> {
        val data= MutableLiveData<AdminChangeAuthorityResponse>()
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices
                .adminChangeUserAuthority(Config.ADMIN_CHANGE_USER_AUTHORITY,
                    changeAuthorityRequest
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Unit>(){
                    override fun onNext(t:Unit) {
                        data.value= AdminChangeAuthorityResponse("200")
                    }

                    override fun onError(e: Throwable) {
                        data.value= AdminChangeAuthorityResponse(e.toString())
                    }

                    override fun onComplete() {

                    }

                })
        )
        return data
    }
}
