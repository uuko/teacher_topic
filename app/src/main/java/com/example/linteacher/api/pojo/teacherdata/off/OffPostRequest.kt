package com.example.linteacher.api.pojo.teacherdata.off

data class OffPostRequest(
    val loginId: Int=0,
    val proCaseName: String="",
    val proCaseNumber: String,
    val proContent: String,
    val proNature: String,
    val proRebate: String,
    val proRemarks: String="",
    val proSign: String,
    val proStartDate: String,
    val proStopDate: String,
    val proVendor: String,
    val proYear: String="",
    val `public`: Boolean
)