package com.example.linteacher.api.pojo.artical

data class ArticleUpdateRequest(
    val articleContent: String,
    val articleId: Int,
    val articleImportant: String,
    val articleTag: String,
    val articleTitle: String
)