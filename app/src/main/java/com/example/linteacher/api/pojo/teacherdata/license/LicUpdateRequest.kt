package com.example.linteacher.api.pojo.teacherdata.license

data class LicUpdateRequest(
    val licId: Int,
    val licName: String,
    val licNumber: Int,
    val licService: String,
    val licType: String,
    val loginId: Int,
    var `public`: Boolean=false,

    )