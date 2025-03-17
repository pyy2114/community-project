package com.project.community.service

import com.project.community.dto.BoardDetailsDTO
import com.project.community.dto.BoardListDTO
import com.project.community.entity.Board
import com.project.community.repository.BoardRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardService(
    private val boardRepository: BoardRepository
) {

    fun getBoardList(): List<BoardListDTO>{

        val boardList = boardRepository.findAll()

        return boardList.map{ board ->
            BoardListDTO(
                boardId = board.id ?: throw IllegalStateException("존재하지 않는 게시글 입니다."),
                title = board.title,
                nickname = board.member.nickname,
                views = board.views,
                categoryType = board.category.name,
                updatedAt = board.updatedAt.toString()
            )
        }
    }

    @Transactional(readOnly = true)
    fun getBoardDetails(id: Long): BoardDetailsDTO{
        val board = boardRepository.findBoardById(id)
                        .orElseThrow{throw IllegalStateException("존재하지 않는 게시글 입니다.")}

        return BoardDetailsDTO(
            boardId = board.id ?: throw IllegalStateException("존재하지 않는 게시글 입니다."),
            title = board.title,
            nickname = board.member.nickname,
            content = board.content,
            views = board.views,
            categoryType = board.category.name,
            updatedAt = board.updatedAt.toString()
        )
    }
}