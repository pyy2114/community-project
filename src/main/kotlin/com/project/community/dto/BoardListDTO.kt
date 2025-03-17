package com.project.community.dto

data class BoardListDTO(
    val boardId: Long,
    val title: String,
    val nickname: String,
    val views: Long,
    val categoryType: String,
    val updatedAt: String
){}
