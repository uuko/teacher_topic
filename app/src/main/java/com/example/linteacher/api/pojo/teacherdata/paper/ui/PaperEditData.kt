package com.example.linteacher.api.pojo.teacherdata.paper.ui

import com.example.linteacher.util.Config

data class PaperEditData(
    override var theId: Int = 0,
    override var themain_thesisName: String = "",
    override var theAuthor: String = "",
    override var itemType: Int = Config.EdIT_VIEW_TYPE,
    var theProject:String="",
    var theCorreAuthor: String = "",
    var theCollCategory: String = "",
    var thePubmain_licationName: String = "",
    var theCoupons: String = "",
    var thePubmain_licatinNumber: String = "",

    var thePublishYear: String = "",
    var thePublishMonth: String = "",
    var thePublishType: String = "",
    var theReviewer: String = "",
    var theTransCooperation: String = "",
    var thePublishingcountry: String = "",
    open var `public`: Boolean=false,



    ) : PaperBaseData()