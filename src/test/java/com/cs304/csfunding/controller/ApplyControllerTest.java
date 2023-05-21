package com.cs304.csfunding.controller;

import com.cs304.csfunding.api.ApplyDTO;
import com.cs304.csfunding.api.InspectDTO;
import com.cs304.csfunding.api.Result;
import com.cs304.csfunding.entity.Apply;
import com.cs304.csfunding.entity.Fund;
import com.cs304.csfunding.entity.ResearchGroup;
import com.cs304.csfunding.entity.User;
import com.cs304.csfunding.service.*;
import com.cs304.csfunding.util.HttpContextUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
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

    @Test
    public void testGetMyApply() throws Exception {
        int mockUserId = 123; // 设置mock的用户ID
        Result expectedResult = new Result(); // 设置预期的结果对象
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/my-apply"));

        // 验证状态码和响应结果
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("success"));
    }

    @Test
    public void testGetAllApply() throws Exception {
        List<Apply> mockApplies = new ArrayList<>(); // 设置模拟的Apply列表

        // 模拟调用testQueryAll方法并返回模拟的Apply列表
        when(applyService.testQueryAll()).thenReturn(mockApplies);

        // 发起GET请求
        ResultActions resultActions = mockMvc.perform(get("/getallapplys"));

        // 验证状态码和响应结果
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.hasSize(mockApplies.size())));
    }

    @Test
    public void testGetAllApply_NotFound() throws Exception {
        // 模拟调用testQueryAll方法返回null
        when(applyService.testQueryAll()).thenReturn(null);

        // 发起GET请求
        ResultActions resultActions = mockMvc.perform(get("/getallapplys"));

        // 验证状态码和响应结果
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("applies not found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }

    @Test
    public void testTestJudgeApply() throws Exception {
        InspectDTO inspectDTO = new InspectDTO();
        inspectDTO.setPass(true);
        inspectDTO.setRemark("Passed");
        inspectDTO.setAid(123);

        // 发起POST请求，并传递InspectDTO对象作为请求体
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/testjudge")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(inspectDTO)));

        // 验证状态码和响应结果
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("OK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());

        // 验证调用applyService.testJudgeByID方法的参数
        verify(applyService).testJudgeByID("pass", inspectDTO.getRemark(), inspectDTO.getAid());
    }
    @Test
    public void testgetSortApplyByUserID() throws Exception {
        InspectDTO inspectDTO = new InspectDTO();
        inspectDTO.setPass(true);
        inspectDTO.setRemark("Passed");
        inspectDTO.setAid(123);

        // 发起POST请求，并传递InspectDTO对象作为请求体
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/testjudge")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(inspectDTO)));

        // 验证状态码和响应结果
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("OK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());

        // 验证调用applyService.testJudgeByID方法的参数
        verify(applyService).testJudgeByID("pass", inspectDTO.getRemark(), inspectDTO.getAid());
    }

    // 辅助方法：将对象转换为JSON字符串
    private String asJsonString(Object obj) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
    @Test
    public void testgetApplyByResearchGroupID() throws Exception{
        List<Apply> mockApplies = new ArrayList<>(); // 设置模拟的Apply列表
        int researchGroupID = 1;

        // 模拟调用testQueryAll方法并返回模拟的Apply列表
        when(applyService.testQueryByResearchGroupID(researchGroupID)).thenReturn(mockApplies);

        // 发起GET请求
        ResultActions resultActions = mockMvc.perform(get("/selectapplybyresearchgroupid").param("researchGroupID", String.valueOf(researchGroupID)));

        // 验证状态码和响应结果
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.hasSize(mockApplies.size())));
    }

    @Test
    public void testgetApplyByResearchGroupID_NotFound() throws Exception{
        int researchGroupID = 1;

        // 模拟调用testQueryAll方法并返回模拟的Apply列表
        when(applyService.testQueryByResearchGroupID(researchGroupID)).thenReturn(null);

        // 发起GET请求
        ResultActions resultActions = mockMvc.perform(get("/selectapplybyresearchgroupid").param("researchGroupID", String.valueOf(researchGroupID)));

        // 验证状态码和响应结果
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("applies not found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }

    @Test
    public void testgetApplyByFundID() throws Exception{
        List<Apply> mockApplies = new ArrayList<>(); // 设置模拟的Apply列表
        int researchGroupID = 1;

        // 模拟调用testQueryAll方法并返回模拟的Apply列表
        when(applyService.testQueryByFundID(researchGroupID)).thenReturn(mockApplies);

        // 发起GET请求
        ResultActions resultActions = mockMvc.perform(get("/all-apply-fund").param("fundID", String.valueOf(researchGroupID)));

        // 验证状态码和响应结果
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.hasSize(mockApplies.size())));
    }

    @Test
    public void testgetApplyByFundID_NotFound() throws Exception{
        int researchGroupID = 1;

        // 模拟调用testQueryAll方法并返回模拟的Apply列表
        when(applyService.testQueryByFundID(researchGroupID)).thenReturn(null);

        // 发起GET请求
        ResultActions resultActions = mockMvc.perform(get("/all-apply-fund").param("fundID", String.valueOf(researchGroupID)));

        // 验证状态码和响应结果
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("applies not found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }

    @Test
    public void testResubmitApply() throws Exception {
        int uuid = -1; // 设置模拟的UUID
        int researchGroupID = 456; // 设置模拟的ResearchGroupID
        int fundID = 789; // 设置模拟的FundID
        int money = 100; // 设置模拟的money

        ApplyDTO applyDTO = new ApplyDTO();
        applyDTO.setApply(uuid);
        applyDTO.setResearchGroupID(researchGroupID);
        applyDTO.setFundID(fundID);
        applyDTO.setMoney(money);

        // 模拟HttpContextUtil.getRequestUuid()方法返回UUID
//        when(HttpContextUtil.getRequestUuid()).thenReturn(uuid);

        // 模拟调用userService.queryByUuid()方法返回User对象
        User mockUser = new User();
        when(userService.queryByUuid(uuid)).thenReturn(mockUser);

        // 模拟调用researchGroupService.testQueryByUser()方法返回ResearchGroup列表
        List<ResearchGroup> mockResearchGroups = new ArrayList<>(); // 设置模拟的ResearchGroup列表
        when(researchGroupService.testQueryByUser(mockUser.getUuid())).thenReturn(mockResearchGroups);

        // 模拟调用researchGroupFundService.testQueryByFund()方法返回ResearchGroupFund列表
        List<Integer> mockResearchGroupFund = new ArrayList<>(); // 设置模拟的ResearchGroupFund列表
        when(researchGroupFundService.testQueryByFund(fundID)).thenReturn(mockResearchGroupFund);

        // 模拟调用fundService.queryByID()方法返回Fund对象
        Fund mockFund = new Fund();
        mockFund.setSum(200); // 设置模拟的Fund的sum属性
        when(fundService.queryByID(fundID)).thenReturn(mockFund);

        // 发起POST请求，并传递ApplyDTO对象作为请求体
        ResultActions resultActions = mockMvc.perform(post("/register/resubmit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(applyDTO)));

        // 验证状态码和响应结果
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("OK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());

        // 验证调用applyService.testUpdateReSubmitted()方法的参数
        verify(applyService).testUpdateReSubmitted(applyDTO.getApply());

        // 验证调用applyService.testInsert()方法的参数
        verify(applyService).testInsert(applyDTO);
    }
    // Add tests for other methods in the ApplyController

}
