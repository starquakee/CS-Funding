package com.cs304.csfunding.controller;

import com.cs304.csfunding.api.Fund_ApplyDTO;
import com.cs304.csfunding.api.Result;
import com.cs304.csfunding.controller.Fund_ApplyController;
import com.cs304.csfunding.service.Fund_ApplyService;
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

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class Fund_ApplyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private Fund_ApplyService fund_applyService;

    @InjectMocks
    private Fund_ApplyController fund_applyController;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(fund_applyController).build();
    }

    @Test
    public void testAddFundApply() throws Exception {
        Fund_ApplyDTO fund_applyDTO = new Fund_ApplyDTO();
        fund_applyDTO.setFundUUID(222);
        fund_applyDTO.setApplyUUID(333);

        // 设置fund_applyDTO的属性值

        when(fund_applyService.testInsert(fund_applyDTO)).thenReturn("");

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/register/fund_apply")
                .contentType(MediaType.APPLICATION_JSON)
                .content("")); // 将fund_applyDTO转换为JSON字符串并作为请求内容

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("success"));

        verify(fund_applyService, times(1)).testInsert(fund_applyDTO);
        verifyNoMoreInteractions(fund_applyService);
    }

    @Test
    public void testGetApplyByFund() throws Exception {
        int fid = 1;
        List<Integer> applyList = Arrays.asList(2); // 假设返回的申请列表

//        when(fund_applyService.testQueryByFund(fid)).thenReturn(applyList);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/apply-by-fund")
                .param("fid", String.valueOf(fid))
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andDo(print());

        verify(fund_applyService, times(1)).testQueryByFund(fid);
        verifyNoMoreInteractions(fund_applyService);
    }
}
