package com.cs304.csfunding.controller;

import com.cs304.csfunding.api.ResearchGroup_FundDTO;
import com.cs304.csfunding.api.Result;
import com.cs304.csfunding.service.ResearchGroup_FundService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

//@WebMvcTest(ResearchGroup_FundController.class)
public class ResearchGroup_FundControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ResearchGroup_FundService researchGroup_fundService;

    @InjectMocks
    private ResearchGroup_FundController researchGroup_fundController;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(researchGroup_fundController).build();
    }

    @Test
    public void testAddResearchGroupFund() throws Exception {
        ResearchGroup_FundDTO researchGroup_fundDTO = new ResearchGroup_FundDTO();
        researchGroup_fundDTO.setFundUUID(1);
        researchGroup_fundDTO.setResearchGroupUUID(1);
        // 设置 researchGroup_fundDTO 对象的属性
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(researchGroup_fundDTO);

        when(researchGroup_fundService.testInsert(researchGroup_fundDTO)).thenReturn("");

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/register/research-group_fund")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value(new Result("")))
                .andDo(print());

        verify(researchGroup_fundService, times(1)).testInsert(researchGroup_fundDTO);
        verifyNoMoreInteractions(researchGroup_fundService);
    }

//    @Test
//    public void testGetFundByResearchGroup() throws Exception {
//        int gid = 1;
//        List<Result> fundList = List.of(/* 预期的返回结果 */);
//
//        when(researchGroup_fundService.testQueryByResearchGroup(gid)).thenReturn(fundList);
//
//        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/fund-research-group")
//                .param("gid", String.valueOf(gid))
//                .contentType(MediaType.APPLICATION_JSON));
//
//        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value(fundList))
//                .andDo(print());
//
//        verify(researchGroup_fundService, times(1)).testQueryByResearchGroup(gid);
//        verifyNoMoreInteractions(researchGroup_fundService);
//    }
//
//    // 辅助方法，用于将对象转换为 JSON 字符串
//    private static String asJsonString(final Object obj) {
//        try {
//            final ObjectMapper mapper = new ObjectMapper();
//            final String jsonContent = mapper.writeValueAsString(obj);
//            return jsonContent;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
}
