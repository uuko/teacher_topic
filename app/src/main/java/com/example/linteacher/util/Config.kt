package com.example.linteacher.util

object Config {
    const val BASE_URL = "http://192.168.1.206:8080/"
    const val GITHUB_URL = "https://api.github.com/"
    const val ANNOUNCE_FRAGMENT = "ANNOUNCE_FRAGMENT"
    const val PROFLE_SECOND_FRAGMENT = "PROFLE_SECOND_FRAGMENT"
    const val WORK_INFORM_FRAGMENT = "WORK_INFORM_FRAGMENT"
    const val TEACHER_DETAIL_FRAGMENT = "TEACHER_DETAIL_FRAGMENT"
    const val PROFILE_FRAGMENT = "PROFILE_FRAGMENT"
    const val TEACHER_FRAGMENT = "TEACHER_FRAGMENT"
    const val TEACHER_SECOND_FRAGMENT = "TEACHER_SECOND_FRAGMENT"
    const val ADMIN = "A"
    const val TEACHER = "B"
    const val ADDUSER = "ADDUSER"

    //viewType
    const val ORIGIN_VIEW_TYPE = 0
    const val ADD_VIEW_TYPE = 1
    const val EdIT_VIEW_TYPE = 2
    const val BANNER_VIEW_TYPE = 3
    const val IMPORTANT_HEADER_VIEW_TYPE = 4
    const val IMPORTANT_ANNOUNCE_VIEW_TYPE = 4
    const val ANNOUNCE_VIEW_TYPE = 5

    //enun
    const val TEXTVIEW = 8
    const val PIC = 44
    const val SELECT_PICTURE = 201

    //api
    const val LOGIN_URL = "teacher/login"
    const val ADMIN_LIST_USER = "/admin/teacher/list"
    const val ADMIN_CHANGE_USER_AUTHORITY = "/admin/change/user/authority"
    const val RESULT_OK = "200"
    const val ADMIN_REGISTER_ = "/teacher/register"
    const val POST_EXP = "/teacher/one_dash_two"
    const val UPDATE_EXP = "/teacher/update/one_dash_two"
    const val GET_EXP = "/teacher/one_dash_two/loginId/%s"
    const val GET_EXP_BY_EXP_NUMBER = "/teacher/one_dash_two/expNumber/%s"

    const val GET_TEACHER_PROFILE = "/teacher/%s"
    const val UPDATE_BY_THREE_DATE_TEACHER_PROFILE = "/teacher/%s/%s/%s"
    const val POST_PROFILE_PIC = "/teacher/uploadFile?tchNumber=%s"
    const val UPDATE_TEACHER_PROFILE = "/teacher/update/%s/%s/%s"
    const val POST_ARTICLE_PIC = "/article/uploadFile"
    const val GET_TEACHER_LINE_INNER = "/teacher/teacherLine/%s"

    //paper
    const val GET_PAPER = "/teacher/paper/loginId/%s"
    const val DEL_PAPER = "/teacher/paper"
    const val POST_PAPER = "/teacher/paper"
    const val GET_ONE_PAPER = "/teacher/paper/theId/%s"
    const val UPDATE_PAPER = "/teacher/update/paper"

    //license
    const val GET_LICENSE = "/teacher/license/loginId/%s"

    //    /teacher/license/licId/15
//    /teacher/license
    const val DEL_LIC = "/teacher/license"
    const val POST_LIC = "/teacher/license"
    const val GET_ONE_LICENSE = "/teacher/license/licId/%s"
    const val UPDATE_LIC = "/teacher/update/license"

    //pro
    const val GET_PRO = "/teacher/pro/loginId/%s"
    const val DEL_PRO = "/teacher/pro"
    const val POST_PRO = "/teacher/pro"
    const val GET_ONE_PRO = "/teacher/pro/theId/%s"
    const val UPDATE_PRO = "/teacher/update/pro"

    //    banner
    const val GET_BANNER = "/article/banner"
    const val UPDATE_BANNER = "/article/banner"
    const val DEL_BANNER = "/article/banner/%s"

    //article
    const val GET_ARTICLE_TAGS = "/article/tags"
    const val GET_LATEST_ARTICLE_ALL = "/article/latest/all"
    const val GET_ARTICLE_ALL = "/article/important/all"
    const val GET_ARTICLE = "/article/important?pageNo=%s"
    const val GET_LATEST_ARTICLE = "/article/latest?pageNo=%s"
    const val GET_ARTICLE_PAGING_TAGS = "/article/tag?pageNo=%s&tag=%s"
    const val GET_ARTICLE_ID = "/article/id/%s"
    const val POST_ARTICLE = "/article"
    const val UPDATE_ARTICLE = "/article/update"
    const val DEL_ARTICLE = "/article/delete"

    //ademic_event
    const val GET_EVE = "/teacher/ademic/loginId/%s"
    const val DEL_EVE = "/teacher/ademic"
    const val POST_EVE = "/teacher/ademic"
    const val GET_ONE_EVE = "/teacher/ademic/eveNumber/%s"
    const val UPDATE_EVE = "/teacher/update/ademic"

    //award
    const val GET_AWA = "/teacher/awards/loginId/%s"
    const val DEL_AWA = "/teacher/awards"
    const val POST_AWA = "/teacher/awards"
    const val GET_ONE_AWA = "/teacher/awards/awaId/%s"
    const val UPDATE_AWA = "/teacher/update/awards"

    //gov
    const val GET_GOV = "/teacher/gov/loginId/%s"
    const val DEL_GOV = "/teacher/gov"
    const val POST_GOV = "/teacher/gov"
    const val GET_ONE_GOV = "/teacher/gov/govId/%s"
    const val UPDATE_GOV = "/teacher/update/gov"

    //book
    const val GET_BOOK = "/teacher/book/loginId/%s"
    const val DEL_BOOK = "/teacher/book"
    const val POST_BOOK = "/teacher/book"
    const val GET_ONE_BOOK = "/teacher/book/infNumber/%s"
    const val UPDATE_BOOK = "/teacher/update/book"

    //patent
    const val GET_PANTENT = "/teacher/pat/loginId/%s"
    const val DEL_PANTENT = "/teacher/pat"
    const val POST_PANTENT = "/teacher/pat"
    const val GET_ONE_PANTENT = "/teacher/pat/theId/%s"
    const val UPDATE_PANTENT = "/teacher/update/pat"
}