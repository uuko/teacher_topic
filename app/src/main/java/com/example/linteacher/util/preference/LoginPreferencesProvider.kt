package com.example.linteacher.util.preference

interface LoginPreferencesProvider {
    fun setTeacherId(mTeacherId: String)
    fun getTeacherId(): String

    fun setLoginId(mLoginId: String)
    fun getLoginId(): String

    fun setTeacherGrade(mTeacherGrade: String)
    fun getTeacherGrade(): String

    fun setToken(mToken:String)
    fun getToken():String
}