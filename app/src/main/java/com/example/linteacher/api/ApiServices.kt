package com.example.linteacher.api

import com.example.linteacher.api.pojo.TeacherLineResponse
import com.example.linteacher.api.pojo.login.LoginRequest
import com.example.linteacher.api.pojo.login.LoginResponse
import io.reactivex.Observable
import retrofit2.http.*

interface ApiServices {

    @GET("teacher/teacherLine/list")
    fun getTeacherLineList(): Observable<List<TeacherLineResponse>>

    //登入
    @POST
    fun login(
        @Url url: String,
        @Body loginRequest: LoginRequest
    ): Observable<LoginResponse>

}