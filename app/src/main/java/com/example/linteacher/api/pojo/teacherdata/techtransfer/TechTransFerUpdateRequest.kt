package com.example.linteacher.api.pojo.teacherdata.techtransfer

data class TechTransFerUpdateRequest(
    var loginId: Int=0,
    var tecContentPatent: String="",
    var tecTransfer: String="",
    var tecTransferName: String="",
    var tecNumber: String = "",
    var tecId:Int=0
)