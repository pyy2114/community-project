package com.project.community.service

import com.project.community.dto.BoardCreateDTO
import com.project.community.dto.BoardDetailsDTO
import com.project.community.dto.BoardListDTO
import com.project.community.entity.Board
import com.project.community.entity.RoleType
import com.project.community.repository.BoardRepository
import com.project.community.repository.CategoryRepository
import com.project.community.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardService(
    private val boardRepository: BoardRepository,
    private val categoryRepository: CategoryRepository,
    private val memberRepository: MemberRepository
) {

    @Transactional(readOnly = true)
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

        // TODO :: 게시글 조회수 count 누락

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

    //일반글 작성
    @Transactional
    fun createCommunityPost(dto: BoardCreateDTO){

        val memberId = dto.memberId

        //멤버 객체 찾아오기(추후에 security로 대체)
        val member = memberRepository.findById(memberId)
            .orElseThrow{throw IllegalStateException("존재하지 않는 유저입니다.")}

        //println("멤버: ${member.nickname}")

        val category = categoryRepository.findById(2)
            .orElseThrow{throw IllegalStateException("존재하지 않는 카테고리입니다.")}

        //println("카테고리 타입: ${category.name}")
        //받아온 DTO 엔티티로 변환
        val boardEntity = Board(
            title = dto.title,
            content = dto.content,
            member = member,
            category = category
        )

        boardRepository.save(boardEntity)
    }

    //공지 작성
    @Transactional
    fun createNotice(dto: BoardCreateDTO){

        val memberId = dto.memberId

        //멤버 객체 찾아오기(추후에 security로 대체)
        val member = memberRepository.findById(memberId)
            .orElseThrow{throw IllegalStateException("존재하지 않는 유저입니다.")}

        //println("멤버: ${member.nickname}")

        if(member.role != RoleType.ADMIN){
            throw IllegalStateException("작성 권한이 없습니다.")
        }

        val category = categoryRepository.findById(1)
            .orElseThrow{throw IllegalStateException("존재하지 않는 카테고리입니다.")}

        //println("카테고리 타입: ${category.name}")
        //받아온 DTO 엔티티로 변환
        val boardEntity = Board(
            title = dto.title,
            content = dto.content,
            member = member,
            category = category
        )

        boardRepository.save(boardEntity)
    }

}