package com.example.linteacher.ui.main.announce

import com.example.linteacher.util.Config

sealed class Content(open val itemType: Int) {
    data class Banner(
        override val itemType: Int = Config.BANNER_VIEW_TYPE,
        val bannerResponseList: List<BannerResponse>,
        val totalCount: Int = 0
    ) : Content(itemType)

    data class ImportantAnnounce(
        override val itemType: Int = Config.IMPORTANT_ANNOUNCE_VIEW_TYPE,
        var list: List<ImportantInnerAnnounce>
    ) : Content(itemType)

    data class Announce(
        override val itemType: Int = Config.ANNOUNCE_VIEW_TYPE,
        val pageTotalCount: Int = 0,
        val responses: List<Response>,
        val totalCount: Int = 0
    ) : Content(itemType)

    data class ImportantInnerAnnounce(
        var articleImportant: String = "",
        var articleTag: String = "",
        var articleTitle: String = "",
        var articleContent: String = "",
        var modifyDate: String = ""
    )

    data class BannerResponse(
        val articleId: Int = 0,
        val picId: Int = 0,
        val picUrl: String = ""
    )


    data class ImportantHeader(
        var articleId: Int = 0, var articleTitle: String = "", var date: String = ""
    )

    data class ContentData(
        val data: String = "",
        val type: Int = 0
    )

    data class Response(
        val articleContent: String = "",
        val articleId: Int = 0,
        val articleImportant: String = "",
        val articleTag: String = "",
        val articleTitle: String = "",
        val modifyDate: String = ""
    )

}


