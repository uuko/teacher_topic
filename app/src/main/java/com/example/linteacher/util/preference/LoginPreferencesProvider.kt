package com.example.linteacher.util.preference

interface LoginPreferencesProvider {
    fun setTeacherId(mTeacherId: String)
    fun getTeacherId(): String

    fun setTeacherGrade(mTeacherGrade: String)
    fun getTeacherGrade(): String
}