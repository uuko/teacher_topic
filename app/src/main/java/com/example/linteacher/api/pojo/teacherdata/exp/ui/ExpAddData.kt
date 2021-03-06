package com.example.linteacher.api.pojo.teacherdata.exp.ui

import com.example.linteacher.util.Config

data class ExpAddData(
    override var company:String="",
    override var job:String="",
    override var itemType:Int=Config.ADD_VIEW_TYPE,
    override var startDate:String="",
    override var endDate:String="",
    override var expType:String="",
    override var coopAgency:String="",
    override var `public`:Boolean=false,
    var isPartTime: String=""
): ExpBaseData()