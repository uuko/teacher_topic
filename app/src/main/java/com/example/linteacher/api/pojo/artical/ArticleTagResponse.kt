package com.example.linteacher.api.pojo.artical

data class ArticleTagResponse(
    val pageTotalCount: Int = 0,
    val responses: List<ArticleResponse> = mutableListOf(),
    val totalCount: Int = 0
)

