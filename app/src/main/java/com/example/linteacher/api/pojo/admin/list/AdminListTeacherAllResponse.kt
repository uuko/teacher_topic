package com.example.linteacher.api.pojo.admin.list

data class AdminListTeacherAllResponse(
    val list:List<AdminListTeacherResponse>,
    val error:String=""
)