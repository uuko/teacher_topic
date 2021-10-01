package com.example.linteacher.api.pojo.artical

import com.example.linteacher.util.Config

data class ArticleResponse(
    val articleContent: String = "",
    val articleId: Int = 0,
    val articleImportant: String = "",
    val articleTag: String = "",
    val articleTitle: String = "",
    val modifyDate: String = "",
    val result: String = Config.RESULT_OK
)