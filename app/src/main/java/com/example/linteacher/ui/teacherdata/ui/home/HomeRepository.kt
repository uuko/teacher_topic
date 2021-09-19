package com.example.linteacher.ui.teacherdata.ui.home

import androidx.lifecycle.MutableLiveData
import com.example.linteacher.api.RetrofitManager
import com.example.linteacher.api.pojo.teacherdata.profile.TeacherProfileAllResponse
import com.example.linteacher.api.pojo.teacherdata.profile.TeacherProfileResponse
import com.example.linteacher.api.pojo.teacherdata.profile.pic.ProfilePicResponse
import com.example.linteacher.util.Config
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class HomeRepository {

    fun getTeacherProfileAllData(loginId:String): MutableLiveData<TeacherProfileAllResponse> {
        val data= MutableLiveData<TeacherProfileAllResponse>()
        val url=String.format(Config.GET_TEACHER_PROFILE,loginId);
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.getTeacherProfileData(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<TeacherProfileResponse>(){
                    override fun onNext(t: TeacherProfileResponse) {
                        data.value= TeacherProfileAllResponse(t,Config.RESULT_OK)
                    }

                    override fun onError(e: Throwable) {
                        data.value= TeacherProfileAllResponse(TeacherProfileResponse(),e.toString())
                    }

                    override fun onComplete() {

                    }

                })
        )
        return data
    }

    fun uploadFile(file: File,loginId: String):MutableLiveData<ProfilePicResponse>{
        val data= MutableLiveData<ProfilePicResponse>()
        val requestBody
             = file.asRequestBody("image/*".toMediaTypeOrNull())
        val partMap=HashMap<String,RequestBody>()
        partMap["file\"; filename=\""+file.name+"\""] = requestBody
        val url=String.format(Config.POST_PROFILE_PIC,loginId);
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.uploadPic(url,partMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<ProfilePicResponse>(){
                    override fun onNext(t: ProfilePicResponse) {
                        data.value= ProfilePicResponse(t.picUrl,Config.RESULT_OK)
                    }

                    override fun onError(e: Throwable) {
                        data.value= ProfilePicResponse("",e.toString())
                    }

                    override fun onComplete() {

                    }

                })
        )
        return data

    }
}