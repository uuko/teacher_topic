package com.example.linteacher.api.pojo.teacherdata.license



data class LicenseResponse(
    val licId: Int=0,
    val licName: String="",
    val licNumber: Int=0,
    val licService: String="",
    val licType: String="",
    val loginId: Int=0,
    val `public`: Boolean=false,
    val tchSemester: Int=0,
    val tchYear: Int=0
)