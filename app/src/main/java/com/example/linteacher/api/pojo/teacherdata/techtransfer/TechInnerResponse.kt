package com.example.linteacher.api.pojo.teacherdata.techtransfer

data class TechInnerResponse(
    val tecCompanyId: Int=0,
    val tecEndDate: String="",
    val tecStratDate: String="",
    val tecTransferAmount: String="",
    val tecTransferNumber: String="",
    val tecTransferVendor: String="",
    var result:String="",
)