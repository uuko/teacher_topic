package com.example.linteacher.ui.addarticle

class AddArticleRequest(
    val articleId: Int = 0,
    val exist: Boolean = false,
    val isBanner: Boolean = false,
    val haveExist: Boolean,
    val haveBanner: Boolean,
    val haveArticleId: Boolean,
)