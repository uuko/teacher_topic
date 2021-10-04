package com.example.linteacher.api.pojo.artical

class ArticleListTagResponse : ArrayList<ArticleListTagResponseItem>()

data class ArticleListTagResponseItem(
    val tag: String
)