package com.example.linteacher.util.preference

import android.content.Context

class LoginPreferences(context: Context) : PreferencesHelper(context), LoginPreferencesProvider {

    override val className: String?
        get() = LoginPreferences::class.java.name
    private val TEACHER_ID = "TEACHER_ID"
    private val TEACHER_GRADE = "TEACHER_GRADE"
    private val TEACHER_TOKEN = "TEACHER_TOKEN"
    private val LOGIN_ID = "LOGIN_ID"

    override fun setTeacherId(mTeacherId: String) {
        save(Type.STRING, TEACHER_ID, mTeacherId)
    }

    override fun getTeacherId(): String {
        return get(Type.STRING, TEACHER_ID) as String
    }

    override fun setLoginId(mLoginId: String) {
        save(Type.STRING, LOGIN_ID, mLoginId)
    }

    override fun getLoginId(): String {
        return get(Type.STRING, LOGIN_ID) as String
    }

    override fun setTeacherGrade(mTeacherGrade: String) {
        save(Type.STRING, TEACHER_GRADE, mTeacherGrade)
    }

    override fun getTeacherGrade(): String {
        return get(Type.STRING, TEACHER_GRADE) as String
    }

    override fun setToken(mToken: String) {
        save(Type.STRING, TEACHER_TOKEN, mToken)
    }

    override fun getToken(): String {
        return get(Type.STRING, TEACHER_TOKEN) as String
    }
}