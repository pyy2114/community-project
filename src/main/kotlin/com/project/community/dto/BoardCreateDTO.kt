package com.project.community.dto

data class BoardCreateDTO(
    val memberId: Long,
    val title: String,
    val content: String
)
