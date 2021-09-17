package com.example.linteacher.api.pojo.teacherdata

import com.example.linteacher.util.Config

data class ExpEditData(
    var expNumber:Int=0,
    override var company:String="",
    override var job:String="",
    override var itemType:Int= Config.EdIT_VIEW_TYPE,
    override var startDate:String="",
    override var endDate:String="",
    override var expType:String="",
    override var coopAgency:String="",
    override var isPublic:Boolean=false,
    var isPartTime: String=""
):ExpBaseData()