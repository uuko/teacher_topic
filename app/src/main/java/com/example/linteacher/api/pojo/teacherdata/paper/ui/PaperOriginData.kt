package com.example.linteacher.api.pojo.teacherdata.paper.ui

import com.example.linteacher.util.Config

data class PaperOriginData(
    override var theId: Int = 0,
    override var themain_thesisName: String = "",
    override var theAuthor: String = "",
    override var itemType: Int = Config.ORIGIN_VIEW_TYPE,
    var thePublishYear:String=""
) : PaperBaseData()