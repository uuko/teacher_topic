package com.example.linteacher.api.pojo.teacherdata.off



data class OffGetResponse(
    val loginId: Int,
    val proCaseName: String,
    val proCaseNumber: String,
    val proContent: String,
    val proId: Int,
    val proNature: String,
    val proRebate: String,
    val proRemarks: String,
    val proSign: String,
    val proStartDate: String,
    val proStopDate: String,
    val proVendor: String,
    val proYear: String,
    val `public`: Boolean,
    val tchSemester: Int,
    val tchYear: Int
)