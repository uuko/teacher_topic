package com.example.linteacher.api

import com.example.linteacher.api.pojo.TeacherLineResponse
import com.example.linteacher.api.pojo.admin.addteacher.AddTeacherRequest
import com.example.linteacher.api.pojo.admin.changeautority.AdminChangeAuthorityRequest
import com.example.linteacher.api.pojo.admin.list.AdminListTeacherResponse
import com.example.linteacher.api.pojo.login.LoginRequest
import com.example.linteacher.api.pojo.login.LoginResponse
import com.example.linteacher.api.pojo.teacherdata.exp.*
import com.example.linteacher.api.pojo.teacherdata.license.LicenseResponse
import com.example.linteacher.api.pojo.teacherdata.off.*
import com.example.linteacher.api.pojo.teacherdata.profile.TeacherProfileResponse
import com.example.linteacher.api.pojo.teacherdata.profile.pic.ProfilePicResponse
import com.example.linteacher.api.pojo.teacherdata.profile.update.TeacherUpdateRequest
import com.example.linteacher.util.Config
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiServices {

    @GET("teacher/teacherLine/list")
    fun getTeacherLineList(): Observable<List<TeacherLineResponse>>




    //教師個資
    @GET
    fun getTeacherProfileData(@Url string: String): Observable<TeacherProfileResponse>
    @POST
    fun postTeacherProfileData(@Url string: String
    ,@Body requestBody: TeacherProfileResponse): Observable<Unit>
    @POST
    fun updateTeacherProfileData(@Url url: String,@Body requestBody: TeacherUpdateRequest):Observable<Unit>
    //選擇頭像
    @POST
    @JvmSuppressWildcards
    @Multipart
    fun uploadPic(@Url url: String,
                  @PartMap params: Map<String,RequestBody>
    ):Observable<ProfilePicResponse>
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


    /**
    * 教師證照
    * */

    @GET
    fun getLicenseData(@Url string: String): Observable<List<LicenseResponse>>

    /**
     * 校外服務經驗
     * */
    @GET
    fun getOneOffData(@Url string: String): Observable<OffOneGetResponse>
    @GET
    fun getOffData(@Url string: String): Observable<List<OffGetResponse>>
    @HTTP(method = "DELETE", path = Config.DEL_PRO, hasBody = true)
    fun deleteOffData(
        @Body deleteRequest: OffDeleteRequest
    ): Observable<Unit>
    @POST
    fun postOffData(
        @Url url: String,
        @Body addTeacherRequest: OffPostRequest
    ): Observable<Unit>
    @POST
    fun updateOffData(@Url url: String,@Body requestBody: OffUpdateRequest):Observable<Unit>
}