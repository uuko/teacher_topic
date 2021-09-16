package com.example.linteacher.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.linteacher.api.pojo.login.LoginAllResponse
import com.example.linteacher.api.pojo.login.LoginRequest
import com.example.linteacher.ui.main.teacherline.TeacherLineAllResponse
import com.example.linteacher.ui.main.teacherline.TeacherLineRepository

class LoginViewModel constructor(var loginRepository: LoginRepository): ViewModel(){
    val isLoading: MutableLiveData<Boolean> = MutableLiveData(true)

    fun postLogin(account:String,pwd:String):MutableLiveData<LoginAllResponse>{
        isLoading.value=(true)
        val request = LoginRequest(account,pwd)
        return loginRepository.login(request)
    }
}