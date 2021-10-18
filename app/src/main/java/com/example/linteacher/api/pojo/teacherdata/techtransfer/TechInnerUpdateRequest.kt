package com.example.linteacher.api.pojo.teacherdata.techtransfer

data class TechInnerUpdateRequest(
    var tecCompanyId: Int=0,
    var tecEndDate: String="",
    var tecStratDate: String="",
    var tecTransferAmount: String="",
    var tecTransferNumber: String="",
    var tecTransferVendor: String=""
)