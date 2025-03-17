package com.project.community.repository

import com.project.community.entity.Board
import com.project.community.entity.Category
import com.project.community.entity.Member
import com.project.community.entity.RoleType
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component


@Component
@Profile(value = ["default"])
class DataInitializer(
    private val categoryRepository: CategoryRepository,
    private val memberRepository: MemberRepository,
    private val boardRepository: BoardRepository
) {
    @PostConstruct
    fun initializeData(){
        println("테스트 데이터 초기화 시작")

        //카테고리 데이터 초기화
        val categoryNotice = categoryRepository.save(Category(name = "NOTICE"))
        val categoryGeneral = categoryRepository.save(Category(name = "GENERAL"))

        //멤버 데이터 초기화
        val admin01 = memberRepository.save(
            Member(
                loginId = "admin01",
                password = "a1234",
                nickname = "admin01",
                role = RoleType.ADMIN
            )
        )
        val user01 = memberRepository.save(
            Member(
                loginId = "user01",
                password = "a1234",
                nickname = "user01",
                role = RoleType.USER
            )
        )
        val user02 = memberRepository.save(
            Member(
                loginId = "user02",
                password = "a1234",
                nickname = "user02",
                role = RoleType.USER
            )
        )

        val boardList = mutableListOf<Board>(
            Board(
                title = "공지글",
                content = "이 글은 공지글 입니다.",
                member = admin01,
                category = categoryNotice
            ),
            Board(
                title = "일반글1",
                content = "이 글은 일반글1 입니다.",
                member = user01,
                category = categoryGeneral
            ),
            Board(
                title = "일반글2",
                content = "이 글은 일반글2 입니다.",
                member = user02,
                category = categoryGeneral
            ),
            Board(
                title = "일반글3",
                content = "이 글은 일반글3 입니다.",
                member = user02,
                category = categoryGeneral
            ),
            Board(
                title = "일반글4",
                content = "이 글은 일반글4 입니다.",
                member = user02,
                category = categoryGeneral
            )

        )

        boardRepository.saveAll(boardList)

        println("테스트 데이터 초기화 완료")

    }
}