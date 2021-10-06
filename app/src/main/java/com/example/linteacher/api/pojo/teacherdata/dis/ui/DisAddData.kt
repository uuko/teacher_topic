package com.example.linteacher.api.pojo.teacherdata.dis.ui

import com.example.linteacher.util.Config

data class DisAddData(
    override var itemType: Int = Config.ADD_VIEW_TYPE,
    override var dismain_thesisName: String = "",
    override var disSeminarName: String = "",
    override var disFD: String = "",
    override var disED: String = "",
    var disProject: String = "",
    var disCorreAuthor: String = "",
    var disReviewer: String = "",
    var disHostCity: String = "",
    var disHostCountry: String = "",
    var disPublishY: String = "",
    var disAuthor: String = "",
) : DisBaseData()