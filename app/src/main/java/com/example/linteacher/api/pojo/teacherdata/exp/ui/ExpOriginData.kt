package com.example.linteacher.api.pojo.teacherdata.exp.ui

import com.example.linteacher.util.Config

class ExpOriginData (
    var expNumber:Int=0,
    override var company:String="",
    override var job:String="",
//    override var itemType:Int=Config.ORIGIN_VIEW_TYPE,
    override var startDate:String="",
    override var endDate:String="",
    override var expType:String="",
    override var coopAgency:String="",
//    override var isPublic:Boolean=false,

    open var loginId: Int=0,
    override var `public`: Boolean=false,
    open var tchSemester: Int=0,
    open var tchYear: Int=0
): ExpBaseData()