package com.project.community.repository

import com.project.community.entity.Board
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface BoardRepository: JpaRepository<Board, Long> {

    fun findBoardById(boardId: Long): Optional<Board>
}