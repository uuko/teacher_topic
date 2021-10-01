package com.example.linteacher.api.pojo.artical

data class LatestAllArticleResponse(
    val list: List<LatestAllArticleResponseItem> = mutableListOf()
)

data class LatestAllArticleResponseItem(
    val articleContent: String = "",
    val articleId: Int = 0,
    val articleImportant: String = "",
    val articleTag: String = "",
    val articleTitle: String = "",
    val modifyDate: String = ""
)