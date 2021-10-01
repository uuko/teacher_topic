package com.example.linteacher.api.pojo.artical

data class LatestArticleResponse(
    val pageTotalCount: Int,
    val responses: List<Response>,
    val totalCount: Int
)

