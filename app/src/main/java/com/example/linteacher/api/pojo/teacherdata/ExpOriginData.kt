package com.example.linteacher.api.pojo.teacherdata

import com.example.linteacher.util.Config

class ExpOriginData (
    override var company:String="",
    override var job:String="",
    override var itemType:Int=Config.ORIGIN_VIEW_TYPE,
    override var startDate:String="",
    override var endDate:String="",
    override var expType:String="",
    override var coopAgency:String="",
    override var isPublic:Boolean=false,
):ExpBaseData()