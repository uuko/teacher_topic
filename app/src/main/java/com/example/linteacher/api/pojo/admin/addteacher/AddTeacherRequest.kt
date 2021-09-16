package com.example.linteacher.api.pojo.admin.addteacher

data class AddTeacherRequest(
    val account: String,
    val email: String,
    val password: String,
    val tchName: String
)