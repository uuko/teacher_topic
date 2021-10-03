package com.example.linteacher.api.pojo.artical

data class ArticlePostRequest(
    val articleContent: String,
    val articleImportant: String,
    val articleTag: String,
    val articleTitle: String
)