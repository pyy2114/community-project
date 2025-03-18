package com.project.community.controller

import org.assertj.core.api.Assertions.assertThat
import org.json.JSONArray
import org.json.JSONObject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.assertj.MockMvcTester.MockMvcRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.nio.charset.StandardCharsets

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation::class) //테스트 순서 지정 활성화
@DisplayName("[Board 컨트롤러 테스트]")
class BoardControllerTest(
    @Autowired private val mockMvc: MockMvc
){

    private fun performGet(uri:String):MvcResult{
        return mockMvc
            .perform(MockMvcRequestBuilders.get(uri))
            .andDo(MockMvcResultHandlers.print())
            .andReturn()
    }

    @Test
    @Order(1)
    @DisplayName("게시글 목록 조회")
    fun getBoardListTest(){
        //given
        val uri = "/api/v1/board"

        //when
        val mvcResult = performGet(uri)
        val contentAsString = mvcResult.response.getContentAsString(StandardCharsets.UTF_8)
        val jsonArray = JSONArray(contentAsString)

        //then
        assertThat(jsonArray.length()).isEqualTo(5)
    }

    @Test
    @Order(2)
    @DisplayName("게시글 상세 조회")
    fun getBoardDetailsTest(){
        //given
        val boardId = 1L
        val uri = "/api/v1/board/${boardId}"

        //when
        val mvcResult = performGet(uri)
        val contentAsString = mvcResult.response.getContentAsString(StandardCharsets.UTF_8)
        val jsonObject = JSONObject(contentAsString)

        //then
        assertThat(jsonObject.getLong("boardId")).isEqualTo(boardId)
    }

    @Test
    @Order(3)
    @DisplayName("게시글 상세 조회_존재하지 않는 아이디")
    fun getBoardDetailsIdExceptionTest(){
        //given-> 존재하지 않는 아이디 전달
        val uri = "/api/v1/board/999"

        //when
        val mvcResult = mockMvc
            .perform(MockMvcRequestBuilders.get(uri))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andReturn()

        //then --> 예외메세지 확인
        val contentAsString = mvcResult.response.getContentAsString(StandardCharsets.UTF_8)
        assertThat(contentAsString).contains("존재하지 않는 게시글 입니다.")
    }


    @Test
    @Order(4)
    @DisplayName("일반 게시글 삽입")
    fun createCommunityPostTest(){
        //given
        val memberId = 2L
        val uri = "/api/v1/board/community/${memberId}"
        val requestBody = """
                {
                    "title" : "테스트 제목",
                    "content" : "테스트 내용물"
                }
        """.trimIndent()

        //when
        val mvcResult = mockMvc
            .perform(
                MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
            )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andReturn()

        //then
        val responseContent = mvcResult.response.getContentAsString(StandardCharsets.UTF_8)
        assertThat(responseContent).isEqualTo("Success")
    }

    @Test
    @Order(5)
    @DisplayName("공지 게시글 삽입")
    fun createNoticeTest(){
        //given
        val memberId = 1L
        val uri = "/api/v1/board/notice/${memberId}"
        val requestBody = """
                {
                    "title" : "공지 제목",
                    "content" : "공지 내용물"
                }
        """.trimIndent()

        //when
        val mvcResult = mockMvc
            .perform(
                MockMvcRequestBuilders.post(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody)
            )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andReturn()

        //then
        val responseContent = mvcResult.response.getContentAsString(StandardCharsets.UTF_8)
        assertThat(responseContent).isEqualTo("Success")
    }

    @Test
    @Order(6)
    @DisplayName("공지 게시글 삽입_권한이 없는 사용자")
    fun createNoticeRoleExceptionTest(){
        //given
        val memberId = 2L
        val uri = "/api/v1/board/notice/${memberId}"
        val requestBody = """
                {
                    "title" : "공지 제목",
                    "content" : "공지 내용물"
                }
        """.trimIndent()

        //when
        val mvcResult = mockMvc
            .perform(
                MockMvcRequestBuilders.post(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody)
            )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andReturn()

        //then
        val responseContent = mvcResult.response.getContentAsString(StandardCharsets.UTF_8)
        assertThat(responseContent).isEqualTo("작성 권한이 없습니다.")
    }



}