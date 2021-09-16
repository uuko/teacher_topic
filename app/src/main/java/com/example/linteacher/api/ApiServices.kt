package com.example.linteacher.api

import com.example.linteacher.api.pojo.TeacherLineResponse
import com.example.linteacher.api.pojo.admin.changeautority.AdminChangeAuthorityRequest
import com.example.linteacher.api.pojo.admin.list.AdminListTeacherResponse
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
    //管理員列出使用者
    @POST
    fun adminListUser(
        @Url url: String,
    ): Observable<List<AdminListTeacherResponse>>

    @POST
    fun adminChangeUserAuthority(
        @Url url: String,
        @Body adminChangeAuthorityRequest: AdminChangeAuthorityRequest
    ): Observable<Unit>
}