package com.example.linteacher.api.pojo.teacherdata.license.ui

import com.example.linteacher.api.BaseData
import com.example.linteacher.util.Config

abstract  class LicBaseData (
    open var licName:String="",
    open var licService:String="",
    open var licType :String="",
    open var licId:Int=0,
    override var itemType:Int= Config.ORIGIN_VIEW_TYPE,
): BaseData()