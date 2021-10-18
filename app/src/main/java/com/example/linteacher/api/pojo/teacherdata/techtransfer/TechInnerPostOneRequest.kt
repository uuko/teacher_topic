package com.example.linteacher.api.pojo.teacherdata.techtransfer

data class TechInnerPostOneRequest(
    var tecEndDate: String="",
    var tecId: Int=0,
    var tecStratDate: String="",
    var tecTransferAmount: String="",
    var tecTransferNumber: String="",
    var tecTransferVendor: String=""
)