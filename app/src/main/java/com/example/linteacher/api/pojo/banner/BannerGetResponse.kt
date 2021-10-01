package com.example.linteacher.api.pojo.banner

data class BannerGetResponse(
    val bannerResponseList: List<BannerResponse> = mutableListOf(),
    val totalCount: Int = 0
)

data class BannerResponse(
    val articleId: Int,
    val picId: Int,
    val picUrl: String
)