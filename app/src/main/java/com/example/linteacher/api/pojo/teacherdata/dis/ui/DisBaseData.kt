package com.example.linteacher.api.pojo.teacherdata.dis.ui

import com.example.linteacher.api.BaseData
import com.example.linteacher.util.Config

abstract class DisBaseData(
    open var dismain_thesisName: String = "",
    open var disSeminarName: String = "",
    open var disFD: String = "",
    open var disED: String = "",
    override var itemType: Int = Config.ORIGIN_VIEW_TYPE,
) : BaseData()