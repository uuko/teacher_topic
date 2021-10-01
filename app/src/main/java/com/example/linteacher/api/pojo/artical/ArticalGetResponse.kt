package com.example.linteacher.api.pojo.artical

data class ArticalGetResponse(
    val pageTotalCount: Int = 0,
    val responses: List<Response> = mutableListOf(),
    val totalCount: Int = 0
)

