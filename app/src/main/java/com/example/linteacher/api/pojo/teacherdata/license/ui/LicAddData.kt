package com.example.linteacher.api.pojo.teacherdata.license.ui

import com.example.linteacher.util.Config

data class LicAddData (
    override var itemType:Int= Config.ADD_VIEW_TYPE,
    override var licName:String="",
    override var licService:String="",
    override var licType :String="",
    override var licId:Int=0,
    var licNumber:String=""
):LicBaseData()