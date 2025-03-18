package com.project.community.controller

import com.project.community.dto.BoardCreateDTO
import com.project.community.dto.BoardDetailsDTO
import com.project.community.dto.BoardListDTO
import com.project.community.entity.Member
import com.project.community.service.BoardService
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
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

    // TODO :: valid도 넣어야징..
    @PostMapping("/community/{memberId}")
    fun createCommunityPost(@RequestBody boardCreateDTO: BoardCreateDTO, @PathVariable memberId: Long): String{
        boardService.createCommunityPost(boardCreateDTO, memberId)
        return "Success"
    }

    @PostMapping("/notice/{memberId}")
    fun createNotice(@RequestBody boardCreateDTO: BoardCreateDTO, @PathVariable memberId: Long): String{
        boardService.createNotice(boardCreateDTO, memberId)
        return "Success"
    }



    //IllegalStateException 발생 시 400으로 반환
    @ExceptionHandler(IllegalStateException::class)
    fun handlerIllegalStateException(ex: IllegalStateException): ResponseEntity<String>{
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.message)
    }
}