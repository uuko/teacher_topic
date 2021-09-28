package com.example.linteacher.api.pojo.teacherdata.off.ui

import com.example.linteacher.util.Config

data class OffOriginData(
        override var proId:Int=0,
        override var proVendor:String="",
        override var proNature:String="",
        override var itemType:Int= Config.ORIGIN_VIEW_TYPE,
) : OffBaseData()