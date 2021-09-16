package com.example.linteacher.api.pojo.admin.list



data class AdminListTeacherResponse(
    val account: String,
    val grade: String,
    val loginId: Int,
    val picUrl: String?="",
    val teacherName: String,
    var isVisible:Boolean
)