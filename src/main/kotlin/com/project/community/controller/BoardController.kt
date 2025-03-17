package com.project.community.controller

import com.project.community.dto.BoardDetailsDTO
import com.project.community.dto.BoardListDTO
import com.project.community.service.BoardService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/board")
class BoardController(
    private val boardService: BoardService
) {

    //게시판 목록 조회
    @GetMapping
    fun getBoardList(): List<BoardListDTO>{
        return boardService.getBoardList()
    }

    //게시글 상세 조회
    @GetMapping("/{boardId}")
    fun getBoardDetails(@PathVariable boardId: Long): BoardDetailsDTO{
        return boardService.getBoardDetails(boardId)
    }
}