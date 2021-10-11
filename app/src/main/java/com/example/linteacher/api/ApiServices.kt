package com.example.linteacher.api

import com.example.linteacher.api.pojo.teacherline.TeacherLineResponse
import com.example.linteacher.api.pojo.admin.addteacher.AddTeacherRequest
import com.example.linteacher.api.pojo.admin.changeautority.AdminChangeAuthorityRequest
import com.example.linteacher.api.pojo.admin.list.AdminListTeacherResponse
import com.example.linteacher.api.pojo.artical.*
import com.example.linteacher.api.pojo.banner.BannerGetResponse
import com.example.linteacher.api.pojo.banner.BannerUpdateRequest
import com.example.linteacher.api.pojo.banner.ResponseContent
import com.example.linteacher.api.pojo.login.LoginRequest
import com.example.linteacher.api.pojo.login.LoginResponse
import com.example.linteacher.api.pojo.teacherdata.adamic.AdemicEventDeleteRequest
import com.example.linteacher.api.pojo.teacherdata.adamic.AcademicPostRequest
import com.example.linteacher.api.pojo.teacherdata.adamic.AdemicEventResponse
import com.example.linteacher.api.pojo.teacherdata.award.AwardDeleteRequest
import com.example.linteacher.api.pojo.teacherdata.award.AwardPostRequest
import com.example.linteacher.api.pojo.teacherdata.award.AwardResponse
import com.example.linteacher.api.pojo.teacherdata.award.AwardUpdateRequest
import com.example.linteacher.api.pojo.teacherdata.book.BookDeleteRequest
import com.example.linteacher.api.pojo.teacherdata.book.BookPostRequest
import com.example.linteacher.api.pojo.teacherdata.book.BookResponse
import com.example.linteacher.api.pojo.teacherdata.book.BookUpdateRequest
import com.example.linteacher.api.pojo.teacherdata.dis.*
import com.example.linteacher.api.pojo.teacherdata.exp.*
import com.example.linteacher.api.pojo.teacherdata.gov.GovDeleteRequest
import com.example.linteacher.api.pojo.teacherdata.gov.GovPostRequest
import com.example.linteacher.api.pojo.teacherdata.gov.GovResponse
import com.example.linteacher.api.pojo.teacherdata.gov.GovUpdateRequest
import com.example.linteacher.api.pojo.teacherdata.license.*
import com.example.linteacher.api.pojo.teacherdata.off.*
import com.example.linteacher.api.pojo.teacherdata.paper.*
import com.example.linteacher.api.pojo.teacherdata.patent.PatentDeleteRequest
import com.example.linteacher.api.pojo.teacherdata.patent.PatentPostRequest
import com.example.linteacher.api.pojo.teacherdata.patent.PatentResponse
import com.example.linteacher.api.pojo.teacherdata.patent.PatentUpdateRequest
import com.example.linteacher.api.pojo.teacherdata.profile.TeacherProfileResponse
import com.example.linteacher.api.pojo.teacherdata.profile.pic.ProfilePicResponse
import com.example.linteacher.api.pojo.teacherdata.profile.update.TeacherUpdateRequest
import com.example.linteacher.api.pojo.teacherline.TeacherSecondLineResponse
import com.example.linteacher.util.Config
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiServices {
    //   教師資料 第一層第二層
    @GET("teacher/teacherLine/list")
    fun getTeacherLineList(): Observable<List<TeacherLineResponse>>

    @GET
    fun getTeacherLineInnerList(@Url string: String): Observable<TeacherSecondLineResponse>

    //dis
    @HTTP(method = "DELETE", path = Config.DEL_DIS, hasBody = true)
    fun deleteDisData(
            @Body deleteRequest: DisDelRequest
    ): Observable<Unit>

    @GET
    fun getDisList(@Url string: String): Observable<List<DisGetResponse>>

    @GET
    fun getDisListById(@Url string: String): Observable<DisGetOneResponse>

    @POST
    fun postDisData(@Url string: String, @Body request: DisPostRequest): Observable<Unit>

    @POST
    fun updateDisData(@Url string: String, @Body request: DisUpdateRequest): Observable<Unit>

    //banner & article
    @GET(Config.GET_BANNER)
    fun getBannerList(): Observable<BannerGetResponse>

    @POST
    fun updateBanner(@Url string: String, @Body request: BannerUpdateRequest)
            : Observable<ResponseContent>


    @POST
    fun deleteBanner(@Url string: String): Observable<ResponseContent>

    @GET
    fun getArticleAllTags(@Url string: String): Observable<ArticleListTagResponse>


    @GET
    fun getArticle(@Url string: String): Observable<ArticleResponse>

    @GET
    fun getArticleByTag(@Url string: String): Observable<ArticleTagResponse>

    @POST
    fun deleteArticle(
            @Url string: String,
            @Body request: List<DeleteArticleRequest>
    ): Observable<Unit>

    @POST
    fun updateArticle(
            @Url string: String,
            @Body requestBody: ArticleUpdateRequest
    ): Observable<String>

    @POST
    fun postArticle(
            @Url string: String,
            @Body requestBody: ArticlePostRequest
    ): Observable<ArticlePostResponse>

    @GET
    fun getImportantList(@Url string: String): Observable<ArticalGetResponse>

    @GET
    fun getLatestList(@Url string: String): Observable<ArticalGetResponse>

    //選擇頭像
    @POST
    @JvmSuppressWildcards
    @Multipart
    fun uploadArticlePic(
            @Url url: String,
            @PartMap params: Map<String, RequestBody>
    ): Observable<ArticlePicResponse>

    @GET
    fun getLatestAllList(@Url string: String): Observable<LatestAllArticleResponse>

    @GET
    fun getImportantAllList(@Url string: String): Observable<LatestAllArticleResponse>

    //教師個資
    @GET
    fun getTeacherProfileData(@Url string: String): Observable<TeacherProfileResponse>

    @POST
    fun postTeacherProfileData(
            @Url string: String, @Body requestBody: TeacherProfileResponse
    ): Observable<Unit>

    @POST
    fun updateTeacherProfileData(
            @Url url: String,
            @Body requestBody: TeacherUpdateRequest
    ): Observable<Unit>

    //選擇頭像
    @POST
    @JvmSuppressWildcards
    @Multipart
    fun uploadPic(
            @Url url: String,
            @PartMap params: Map<String, RequestBody>
    ): Observable<ProfilePicResponse>

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
    fun getPaperData(@Url string: String): Observable<List<PaperResponse>>

    @HTTP(method = "DELETE", path = Config.DEL_PAPER, hasBody = true)
    fun deletePaperData(
            @Body deleteRequest: PaperDeleteRequest
    ): Observable<Unit>

    @GET
    fun getPaperOneData(@Url string: String): Observable<PaperOneResponse>

    @POST
    fun postPaperData(
            @Url url: String,
            @Body addTeacherRequest: PaperPostRequest
    ): Observable<Unit>

    @POST
    fun updatePaperData(@Url url: String, @Body requestBody: PaperUpdateRequest): Observable<Unit>


    /**
     * 教師證照
     * */

    @GET
    fun getLicenseData(@Url string: String): Observable<List<LicenseResponse>>

    @GET
    fun getOneLicData(@Url string: String): Observable<LicOneResponse>

    @HTTP(method = "DELETE", path = Config.DEL_LIC, hasBody = true)
    fun deleteLicData(
            @Body deleteRequest: LicDeleteRequest
    ): Observable<Unit>

    @POST
    fun postLicData(
            @Url url: String,
            @Body addTeacherRequest: LicPostRequest
    ): Observable<Unit>

    @POST
    fun updateLicData(@Url url: String, @Body requestBody: LicUpdateRequest): Observable<Unit>

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
    fun updateOffData(@Url url: String, @Body requestBody: OffUpdateRequest): Observable<Unit>
    /**
     * ademic_event
     * */

    @GET
    fun getAdemicEventData(@Url string: String): Observable<List<AdemicEventResponse>>

    @GET
    fun getOneAdemicEventData(@Url string: String): Observable<AdemicEventResponse>

    @HTTP(method = "DELETE", path = Config.DEL_EVE, hasBody = true)
    fun deleteAdemicEventData(
            @Body deleteRequest: AdemicEventDeleteRequest
    ): Observable<Unit>

    @POST
    fun postAdemicEventData(
            @Url url: String,
            @Body addTeacherRequest: AcademicPostRequest
    ): Observable<Unit>

    @POST
    fun updateAdemicEventData(@Url url: String, @Body requestBody: AcademicPostRequest): Observable<Unit>

    /**
     * Award
     * */

    //參數隨便
    @GET
    fun getAwardData(@Url string: String): Observable<List<AwardResponse>>

    //參數隨便
    @GET
    fun getOneAwardData(@Url string: String): Observable<AwardResponse>


    @HTTP(method = "DELETE", path = Config.DEL_AWA, hasBody = true)
    fun deleteAwardData(
            @Body deleteRequest: AwardDeleteRequest
    ): Observable<Unit>

    @POST
    fun postAwardData(
            @Url url: String,
            @Body addTeacherRequest: AwardPostRequest
    ): Observable<Unit>

    @POST
    fun updateAwardData(@Url url: String, @Body requestBody: AwardUpdateRequest): Observable<Unit>

    /**
     * Gov
     * */
    @GET
    fun getGovData(@Url string: String): Observable<List<GovResponse>>

    @GET
    fun getOneGovData(@Url string: String): Observable<GovResponse>

    @HTTP(method = "DELETE", path = Config.DEL_GOV, hasBody = true)
    fun deleteGovData(
            @Body deleteRequest: GovDeleteRequest
    ): Observable<Unit>

    @POST
    fun postGovData(
            @Url url: String,
            @Body addTeacherRequest: GovPostRequest
    ): Observable<Unit>

    @POST
    fun updateGovData(@Url url: String, @Body requestBody: GovUpdateRequest): Observable<Unit>



    /**
     * Book
     * */

    @GET
    fun getBookData(@Url string: String): Observable<List<BookResponse>>

    @GET
    fun getOneBookData(@Url string: String): Observable<BookResponse>

    @HTTP(method = "DELETE", path = Config.DEL_BOOK, hasBody = true)
    fun deleteBookData(
            @Body deleteRequest: BookDeleteRequest
    ): Observable<Unit>

    @POST
    fun postBookData(
            @Url url: String,
            @Body addTeacherRequest: BookPostRequest
    ): Observable<Unit>

    @POST
    fun updateBookData(@Url url: String, @Body requestBody: BookUpdateRequest): Observable<Unit>


    /**
     * Patent
     * */

    @GET
    fun getPatentData(@Url string: String): Observable<List<PatentResponse>>

    @GET
    fun getOnePatentData(@Url string: String): Observable<PatentResponse>

    @HTTP(method = "DELETE", path = Config.DEL_PANTENT, hasBody = true)
    fun deletePatentData(
            @Body deleteRequest: PatentDeleteRequest
    ): Observable<Unit>

    @POST
    fun postPatentData(
            @Url url: String,
            @Body addTeacherRequest: PatentPostRequest
    ): Observable<Unit>

    @POST
    fun updatePatentData(@Url url: String, @Body requestBody: PatentUpdateRequest): Observable<Unit>

}