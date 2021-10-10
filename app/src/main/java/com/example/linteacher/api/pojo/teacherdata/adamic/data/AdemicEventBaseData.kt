package com.example.linteacher.api.pojo.teacherdata.adamic.data

import com.example.linteacher.api.BaseData
import com.example.linteacher.util.Config

class AdemicEventBaseData (
        override var itemType: Int = Config.ORIGIN_VIEW_TYPE,
    open var eveName:String="",
    open var eveOrganizer:String="",
    open var eveHours :String="",
    open var eveCategory :String="",
    open var eveSort :String="",
    open var eveLocation :String="",
    open var eveParticimainPation :String="",
    open var eveClassRelated :String="",
    open var eveStratDate  :String="",
    open var eveStopDate  :String="",
    open var eveStudyCertificate  :String="",
    open var eveCertificateNumber  :String="",
    open var eveSchSubsidy   :String="",

    open var eveNumber:Int=0,
    open var loginId: Int=0,
    open var `public`: Boolean=false,
    open var tchSemester: Int=0,
    open var tchYear: Int=0
):BaseData()