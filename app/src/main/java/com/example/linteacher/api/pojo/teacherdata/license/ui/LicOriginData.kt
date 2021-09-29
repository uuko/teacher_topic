package com.example.linteacher.api.pojo.teacherdata.license.ui

import com.example.linteacher.util.Config

data class LicOriginData (
    override var itemType:Int= Config.ORIGIN_VIEW_TYPE,
    override var licName:String="",
    override var licService:String="",
    override var licType :String="",
    override var licId:Int=0,
):LicBaseData()