package com.example.linteacher.ui.teacherdata.ui.home

import androidx.lifecycle.MutableLiveData
import com.example.linteacher.api.RetrofitManager
import com.example.linteacher.api.pojo.UnitResponse
import com.example.linteacher.api.pojo.teacherdata.profile.TeacherProfileAllResponse
import com.example.linteacher.api.pojo.teacherdata.profile.TeacherProfileResponse
import com.example.linteacher.api.pojo.teacherdata.profile.pic.ProfilePicResponse
import com.example.linteacher.api.pojo.teacherdata.profile.update.TeacherUpdateRequest
import com.example.linteacher.util.Config
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.util.*
import kotlin.collections.HashMap

class HomeRepository
{

    fun updateTeacherProfileData
                (teacherUpdateRequest: TeacherUpdateRequest
                 ,loginId: String,tchYear:String,tchSemester:String)
    :MutableLiveData<UnitResponse>{
        val data= MutableLiveData<UnitResponse>()
        val url=String.format(Config. UPDATE_TEACHER_PROFILE,loginId,tchYear,tchSemester);
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.updateTeacherProfileData(url,teacherUpdateRequest)
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

    fun postTeacherProfileData(request: TeacherProfileResponse
        ,loginId: String):MutableLiveData<UnitResponse>{
        val data= MutableLiveData<UnitResponse>()
//        /teacher/{tchNumber}/{tchYear}/{tchSemester}
        val year: Int = Calendar.getInstance().get(Calendar.YEAR) - 1911
        val month: Int = Calendar.getInstance().get(Calendar.MONTH)
        var semester = 0
        semester = if (month < 8 && month > 1) {
            1
        } else {
            2
        }
        val url=String.format(
            Config.UPDATE_BY_THREE_DATE_TEACHER_PROFILE
            ,loginId,year,semester);
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.postTeacherProfileData(url,request)
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