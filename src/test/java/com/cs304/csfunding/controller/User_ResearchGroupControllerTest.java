package com.cs304.csfunding.controller;

import com.cs304.csfunding.api.Fund_ApplyDTO;
import com.cs304.csfunding.api.Result;
import com.cs304.csfunding.api.User_ResearchGroupDTO;
import com.cs304.csfunding.controller.Fund_ApplyController;
import com.cs304.csfunding.service.Fund_ApplyService;
import com.cs304.csfunding.service.User_ResearchGroupService;
import com.fasterxml.jackson.databind.JsonNode;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class User_ResearchGroupControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private User_ResearchGroupService user_researchGroupService;

    @InjectMocks
    private User_ResearchGroupController user_researchGroupController;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(user_researchGroupController).build();
    }

    @Test
    public void testAddUserResearchGroup() throws Exception {
        User_ResearchGroupDTO user_researchGroupDTO = new User_ResearchGroupDTO();
        user_researchGroupDTO.setUserUUID(1);
        user_researchGroupDTO.setResearchGroupUUID(2);
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(user_researchGroupDTO);
        // 设置fund_applyDTO的属性值
        when(user_researchGroupService.testInsert(user_researchGroupDTO)).thenReturn("");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/add-user_group")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value(""))
                .andDo(print())
                .andReturn();
        String responseJson = result.getResponse().getContentAsString();
        JsonNode jsonResponse = new ObjectMapper().readTree(responseJson);
        JsonNode dataNode = jsonResponse.get("data");
        System.out.println(1);
        System.out.println(dataNode);
    }

    @Test
    public void testgetResearchGroupByUser() throws Exception {
        int uid = 7;
        List<Integer> fundList = Arrays.asList(1);

        when(user_researchGroupService.testQueryByUser(uid)).thenReturn(fundList);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/selectresearchgroupbyuser")
                        .param("uid", String.valueOf(uid))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        JsonNode jsonResponse = new ObjectMapper().readTree(responseJson);
        JsonNode dataNode = jsonResponse.get("data");
        System.out.println(1);
        System.out.println(dataNode);
        assertEquals(dataNode.toString(), fundList.toString());
    }
}
