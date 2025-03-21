package com.project.community.controller

import com.project.community.dto.BoardCreateDTO
import com.project.community.dto.BoardDetailsDTO
import com.project.community.dto.BoardListDTO
import com.project.community.entity.Member
import com.project.community.service.BoardService
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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
    @PostMapping("/community")
    fun createCommunityPost(@RequestBody boardCreateDTO: BoardCreateDTO): ResponseEntity<String>{
        boardService.createCommunityPost(boardCreateDTO)
        return ResponseEntity.status(HttpStatus.CREATED).body("Success")
    }

    @PostMapping("/notice")
    fun createNotice(@RequestBody boardCreateDTO: BoardCreateDTO): ResponseEntity<String>{
        boardService.createNotice(boardCreateDTO)
        return ResponseEntity.status(HttpStatus.CREATED).body("Success")
    }

    @PutMapping("/community/{boardId}")
    fun updateCommunityPost(@PathVariable boardId: Long, @RequestBody boardCreateDTO: BoardCreateDTO): ResponseEntity<String>{
        boardService.updateCommunityPost(boardId, boardCreateDTO)
        return ResponseEntity.status(HttpStatus.OK).body("Success")
    }

    //IllegalStateException 발생 시 400으로 반환
    @ExceptionHandler(IllegalStateException::class)
    fun handlerIllegalStateException(ex: IllegalStateException): ResponseEntity<String>{
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.message)
    }
}