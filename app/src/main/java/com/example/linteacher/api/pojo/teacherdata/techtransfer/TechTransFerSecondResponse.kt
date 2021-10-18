package com.example.linteacher.api.pojo.teacherdata.techtransfer

data class TechTransFerSecondResponse(
    val tecContentPatent: String,
    val tecId: Int,
    val tecTransfer: String,
    val tecTransferName: String,
    val techChgeCompanyModelList: List<TechChgeCompanyModel>,
    val tecNumber :String
)

data class TechChgeCompanyModel(
    val tecCompanyId: Int,
    val tecEndDate: String,
    val tecStratDate: String,
    val tecTransferAmount: String,
    val tecTransferNumber: String,
    val tecTransferVendor: String
)