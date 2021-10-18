package com.example.linteacher.api.pojo.teacherdata.dis.ui

import com.example.linteacher.util.Config

data class DisOriginData(
    var disId: Int = 0,
    override var itemType: Int = Config.ORIGIN_VIEW_TYPE,
    override var dismain_thesisName: String = "",
    override var disSeminarName: String = "",
    override var disFD: String = "",
    override var disED: String = "",
    open var `public`: Boolean=false,

    ) : DisBaseData()