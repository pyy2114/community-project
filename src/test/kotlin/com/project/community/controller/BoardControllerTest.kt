package com.project.community.controller

import org.assertj.core.api.Assertions.assertThat
import org.json.JSONArray
import org.json.JSONObject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.assertj.MockMvcTester.MockMvcRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.nio.charset.StandardCharsets

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("[Board 컨트롤러 테스트]")
class BoardControllerTest(
    @Autowired private val mockMvc: MockMvc
){

    @Test
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

    private fun performGet(uri:String):MvcResult{
        return mockMvc
            .perform(MockMvcRequestBuilders.get(uri))
            .andDo(MockMvcResultHandlers.print())
            .andReturn()
    }

}