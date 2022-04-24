package com.example.core.data.models

data class  Pagination<T>  (
    val page: Int,
    val total_pages: Int,
    val total_results: Int,
    val results:List<T>)