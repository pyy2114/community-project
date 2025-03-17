package com.project.community.dto

data class BoardDetailsDTO(
    val boardId: Long,
    val title: String,
    val nickname: String,
    val content: String,
    val views: Long,
    val categoryType: String,
    val updatedAt: String
){}
