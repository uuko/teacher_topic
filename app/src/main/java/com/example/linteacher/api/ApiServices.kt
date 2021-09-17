package com.example.linteacher.api

import com.example.linteacher.api.pojo.TeacherLineResponse
import com.example.linteacher.api.pojo.admin.addteacher.AddTeacherRequest
import com.example.linteacher.api.pojo.admin.changeautority.AdminChangeAuthorityRequest
import com.example.linteacher.api.pojo.admin.list.AdminListTeacherResponse
import com.example.linteacher.api.pojo.login.LoginRequest
import com.example.linteacher.api.pojo.login.LoginResponse
import com.example.linteacher.api.pojo.teacherdata.exp.*
import com.example.linteacher.util.Config
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
    //管理員改單一使用者權限
    @POST
    fun adminChangeUserAuthority(
        @Url url: String,
        @Body adminChangeAuthorityRequest: AdminChangeAuthorityRequest
    ): Observable<Unit>

    //管理員改單一使用者權限
    @POST
    fun adminRegisterTeacher(
        @Url url: String,
        @Body addTeacherRequest: AddTeacherRequest
    ): Observable<Unit>


    //老師實務經驗
    @POST
    fun postExpData(
        @Url url: String,
        @Body addTeacherRequest: ExpAddRequest
    ): Observable<Unit>
    @POST
    fun updateExpData(
        @Url url: String,
        @Body addTeacherRequest: ExpUpdateRequest
    ): Observable<Unit>
    @HTTP(method = "DELETE", path = Config.POST_EXP, hasBody = true)
    fun deleteExpData(
        @Body deleteRequest: ExpDeleteRequest
    ): Observable<Unit>
    @GET
    fun getExpData(@Url string: String): Observable<List<ExpGetResponse>>
    @GET
    fun getExpDataByExpNumber(@Url string: String): Observable<ExpOneGetResponse>

}