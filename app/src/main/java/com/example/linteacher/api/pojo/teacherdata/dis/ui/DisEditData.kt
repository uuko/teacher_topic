package com.example.linteacher.api.pojo.teacherdata.dis.ui

import com.example.linteacher.util.Config

data class DisEditData(
    override var itemType: Int = Config.EdIT_VIEW_TYPE,
    override var dismain_thesisName: String = "",
    override var disSeminarName: String = "",
    override var disFD: String = "",
    override var disED: String = "",
    var disId: Int = 0,
    var disProject: String = "",
    var disCorreAuthor: String = "",
    var disReviewer: String = "",
    var disHostCity: String = "",
    var disHostCountry: String = "",
    var disPublishY: String = "",
    var disAuthor: String = "",
    open var `public`: Boolean=false,

    ) : DisBaseData()