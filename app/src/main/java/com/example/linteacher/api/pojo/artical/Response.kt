package com.example.linteacher.api.pojo.artical

data class Response(
    val articleContent: String,
    val articleId: Int,
    val articleImportant: String,
    val articleTag: String,
    val articleTitle: String,
    val modifyDate: String,
    var isChecked: Boolean = false
)