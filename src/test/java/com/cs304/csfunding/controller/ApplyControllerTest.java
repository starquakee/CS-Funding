package com.cs304.csfunding.controller;

import com.cs304.csfunding.api.ApplyDTO;
import com.cs304.csfunding.api.Result;
import com.cs304.csfunding.entity.Apply;
import com.cs304.csfunding.entity.Fund;
import com.cs304.csfunding.entity.ResearchGroup;
import com.cs304.csfunding.entity.User;
import com.cs304.csfunding.service.*;
import com.cs304.csfunding.util.HttpContextUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ApplyControllerTest {

    @Mock
    private ApplyService applyService;
    @Mock
    private UserService userService;
    @Mock
    private ResearchGroupService researchGroupService;
    @Mock
    private ResearchGroup_FundService researchGroupFundService;
    @Mock
    private FundService fundService;

    @InjectMocks
    private ApplyController applyController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(applyController).build();
    }

    @Test
    public void testAddApply() throws Exception {
        ApplyDTO applyDTO = new ApplyDTO();
        applyDTO.setUserID(1);

        when(applyService.testInsert(applyDTO)).thenReturn("success");

        mockMvc.perform(post("/register/testapply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userID\": 1}"))
                .andExpect(status().isOk())
                .andExpect(content().string("success"));

        verify(applyService, times(1)).testInsert(applyDTO);
        verifyNoMoreInteractions(applyService);
    }

    @Test
    public void testAddApply_shouldReturnForbidden_whenUserNotBelongToResearchGroup() throws Exception {
        ApplyDTO applyDTO = new ApplyDTO();
        applyDTO.setUserID(1);
        applyDTO.setResearchGroupID(2);

        User user = new User();
        user.setUuid(1);

        when(userService.queryByUuid(applyDTO.getUserID())).thenReturn(user);
        when(researchGroupFundService.testQueryByFund(applyDTO.getFundID())).thenReturn(Collections.singletonList(2));


        mockMvc.perform(post("/register/apply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userID\": 1, \"researchGroupID\": 2}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("user doesn't belong to the research group"));

        verify(userService, times(1)).queryByUuid(applyDTO.getUserID());
        verify(researchGroupService, times(1)).testQueryByUser(user.getUuid());
        verifyNoMoreInteractions(userService, researchGroupService);
    }


    @Test
    public void testAddApply_shouldReturnForbidden_whenExceedingFundBudget() throws Exception {
        ApplyDTO applyDTO = new ApplyDTO();
        applyDTO.setUserID(1);
        applyDTO.setResearchGroupID(2);
        applyDTO.setFundID(3);
        applyDTO.setMoney(1000);

        User user = new User();
        user.setUuid(1);

        ResearchGroup researchGroup = new ResearchGroup();
        researchGroup.setUuid(2);

        Fund fund = new Fund();
        fund.setSum(500);

        when(userService.queryByUuid(applyDTO.getUserID())).thenReturn(user);
        when(researchGroupService.testQueryByUser(user.getUuid())).thenReturn(Arrays.asList(researchGroup));
        when(researchGroupFundService.testQueryByFund(applyDTO.getFundID())).thenReturn(Arrays.asList(researchGroup.getUuid()));
        when(fundService.queryByID(applyDTO.getFundID())).thenReturn(fund);

        mockMvc.perform(post("/register/apply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userID\": 1, \"researchGroupID\": 2, \"fundID\": 3, \"money\": 1000}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("exceed fund budget"));

        verify(userService, times(1)).queryByUuid(applyDTO.getUserID());
        verify(researchGroupService, times(1)).testQueryByUser(user.getUuid());
        verify(researchGroupFundService, times(1)).testQueryByFund(applyDTO.getFundID());
        verify(fundService, times(1)).queryByID(applyDTO.getFundID());
        verifyNoMoreInteractions(userService, researchGroupService, researchGroupFundService, fundService);
    }

    @Test
    public void testAddApply_shouldReturnOk_whenValidData() throws Exception {
        ApplyDTO applyDTO = new ApplyDTO();
        applyDTO.setUserID(1);
        applyDTO.setResearchGroupID(2);
        applyDTO.setFundID(3);
        applyDTO.setMoney(500);

        User user = new User();
        user.setUuid(1);

        ResearchGroup researchGroup = new ResearchGroup();
        researchGroup.setUuid(2);

        Fund fund = new Fund();
        fund.setSum(1000);

        when(userService.queryByUuid(applyDTO.getUserID())).thenReturn(user);
        when(researchGroupService.testQueryByUser(user.getUuid())).thenReturn(Arrays.asList(researchGroup));
        when(researchGroupFundService.testQueryByFund(applyDTO.getFundID())).thenReturn(Arrays.asList(researchGroup.getUuid()));
        when(fundService.queryByID(applyDTO.getFundID())).thenReturn(fund);
//        doNothing().when(applyService).testInsert(applyDTO);

        mockMvc.perform(post("/register/apply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userID\": 1, \"researchGroupID\": 2, \"fundID\": 3, \"money\": 500}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("OK"));

        verify(userService, times(1)).queryByUuid(applyDTO.getUserID());
        verify(researchGroupService, times(1)).testQueryByUser(user.getUuid());
        verify(researchGroupFundService, times(1)).testQueryByFund(applyDTO.getFundID());
        verify(fundService, times(1)).queryByID(applyDTO.getFundID());
        verify(applyService, times(1)).testInsert(applyDTO);
        verifyNoMoreInteractions(userService, researchGroupService, researchGroupFundService, fundService, applyService);
    }

    // Add tests for other methods in the ApplyController

}
