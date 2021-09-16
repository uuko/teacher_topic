package com.example.linteacher.util

object Config {
    const val BASE_URL="http://192.168.1.135:8080/"
    const val GITHUB_URL="https://api.github.com/"
    const val ANNOUNCE_FRAGMENT="ANNOUNCE_FRAGMENT"
    const val PROFILE_FRAGMENT="PROFILE_FRAGMENT"
    const val TEACHER_FRAGMENT="TEACHER_FRAGMENT"
    const val TEACHER_SECOND_FRAGMENT="TEACHER_SECOND_FRAGMENT"
    const val ADMIN= "A"
    const val TEACHER= "B"
    const val ADDUSER="ADDUSER"
    //api
    const val LOGIN_URL="teacher/login"
    const val ADMIN_LIST_USER="/admin/teacher/list"
    const val ADMIN_CHANGE_USER_AUTHORITY="/admin/change/user/authority"
    const val RESULT_OK="200"
    const val ADMIN_REGISTER_="/teacher/register"
}