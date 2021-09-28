package com.example.linteacher.api.pojo.teacherdata.off.ui

import com.example.linteacher.util.Config

data class OffAddData (
    override var proVendor:String="",
    override var proNature:String="",
    override var itemType:Int= Config.ADD_VIEW_TYPE,
    override var proId:Int=0,
    var pro_pronature:String="",
    var proSign :String="",
    var proCaseNumber :String="",
    var proCaseName :String="",
    var proContent :String="",
    var proStartDate  :String="",
    var proStopDate  :String="",
    var proRebate  :String=""
    ):OffBaseData()