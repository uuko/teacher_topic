package com.example.linteacher.ui.addarticle

import android.graphics.drawable.Drawable

data class UrlDrawableResponse(
    val index: Int = 0,
    val picUrl: String = "",
    val content: String = "",
    val isDrawable: Boolean = false,
    var drawable: Drawable? = null,
    var picName: String = "",
)