package com.example.linteacher.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.linteacher.api.pojo.login.LoginAllResponse
import com.example.linteacher.api.pojo.login.LoginRequest
import com.example.linteacher.ui.main.teacherline.TeacherLineAllResponse
import com.example.linteacher.ui.main.teacherline.TeacherLineRepository

class LoginViewModel constructor(var loginRepository: LoginRepository) : ViewModel() {
    val isLoading: MutableLiveData<Boolean> = MutableLiveData(true)


    private val accountLiveData = MutableLiveData<LoginRequest>()

    private val passwordLiveData = MutableLiveData<String>()
    val response: LiveData<LoginAllResponse> =
        Transformations.switchMap(accountLiveData) { address ->
            loginRepository.login(address)
        }

    //    val user = Transformations.switchMap(userId) { id -> getUser(id) }
//    : MutableLiveData<LoginAllResponse>
    fun postLogin(account: String, pwd: String) {
        isLoading.value = (true)
        accountLiveData.value = LoginRequest(account, pwd)
//        passwordLiveData.value = pwd


//        val request = LoginRequest(account, pwd)
//        return loginRepository.login(request)
    }
}