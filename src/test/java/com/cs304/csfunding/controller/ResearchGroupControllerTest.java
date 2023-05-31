package com.cs304.csfunding.controller;

import com.cs304.csfunding.api.ResearchGroupDTO;
import com.cs304.csfunding.api.Result;
import com.cs304.csfunding.entity.Fund;
import com.cs304.csfunding.entity.ResearchGroup;
import com.cs304.csfunding.service.FundService;
import com.cs304.csfunding.service.ResearchGroupService;
import com.cs304.csfunding.service.ResearchGroup_FundService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

public class ResearchGroupControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ResearchGroupService researchGroupService;
    @Mock
    private ResearchGroup_FundService researchGroup_fundService;
    @Mock
    private FundService fundService;
    @InjectMocks
    private ResearchGroupController researchGroupController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(researchGroupController).build();
    }

    @Test
    public void testAddResearchGroup() throws Exception {
        ResearchGroupDTO researchGroupDTO = new ResearchGroupDTO(); // Create a ResearchGroupDTO object with test data

        when(researchGroupService.testInsert(researchGroupDTO)).thenReturn("success");

        mockMvc.perform(MockMvcRequestBuilders.post("/add-researchgroup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(researchGroupDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value("success"));
    }

    @Test
    public void testGetAllResearchGroup() throws Exception {
        List<ResearchGroup> researchGroups = Arrays.asList(new ResearchGroup(), new ResearchGroup()); // Create a list of ResearchGroup objects with test data

        when(researchGroupService.testQueryAll()).thenReturn(researchGroups);

        mockMvc.perform(MockMvcRequestBuilders.get("/get-all-research-groups"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()").value(2));
    }

    @Test
    public void testGetResearchGroupByName() throws Exception {
        String teacherName = "John Doe";

        List<ResearchGroup> researchGroups = Arrays.asList(new ResearchGroup(), new ResearchGroup()); // Create a list of ResearchGroup objects with test data

        when(researchGroupService.testQueryByName(teacherName)).thenReturn(researchGroups);

        mockMvc.perform(MockMvcRequestBuilders.get("/get-research-groups-by-name")
                        .param("teacherName", teacherName))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()").value(2));
    }
    @Test
    public void testGetCurrentGroup() throws Exception {
        int uid = 123; // Set the user ID
        List<ResearchGroup> researchgroups = new ArrayList<>(); // Create a list of research groups
        // Add some research groups to the list (based on your requirements)
        researchgroups.add(new ResearchGroup());
        researchgroups.add(new ResearchGroup());
        when(researchGroupService.testQueryByUser(uid)).thenReturn(researchgroups);

        mockMvc.perform(MockMvcRequestBuilders.get("/get-current-group"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()").value(researchgroups.size()-2));
    }

    @Test
    public void testGetResearchGroupByUser() throws Exception {
        int userID = 456; // Set the user ID
        List<ResearchGroup> researchgroups = new ArrayList<>(); // Create a list of research groups
        // Add some research groups to the list (based on your requirements)
        researchgroups.add(new ResearchGroup());
        researchgroups.add(new ResearchGroup());
        when(researchGroupService.testQueryByUser(userID)).thenReturn(researchgroups);

        mockMvc.perform(MockMvcRequestBuilders.get("/get-research-groups-by-user")
                        .param("UserID", String.valueOf(userID)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()").value(researchgroups.size()));
    }

    @Test
    public void testGetCurrentResearchGroupData() throws Exception {
        int uid = 123; // Set the user ID
        List<ResearchGroup> researchgroups = new ArrayList<>(); // Create a list of research groups
        // Add some research groups to the list (based on your requirements)
        researchgroups.add(new ResearchGroup());
        researchgroups.add(new ResearchGroup());
        when(researchGroupService.testQueryByUser(uid)).thenReturn(researchgroups);

        mockMvc.perform(MockMvcRequestBuilders.get("/current-group-data"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()").value(researchgroups.size()-2));
    }

    @Test
    public void testGetUserResearchGroupFundData() throws Exception {
        int uid = 123; // Set the user ID
        List<ResearchGroup> researchgroups = new ArrayList<>(); // Create a list of research groups
        // Add some research groups to the list (based on your requirements)
        researchgroups.add(new ResearchGroup());
        researchgroups.add(new ResearchGroup());
        when(researchGroupService.testQueryByUser(uid)).thenReturn(researchgroups);

        List<Integer> fundIds = new ArrayList<>(); // Create a list of fund IDs
        // Add some fund IDs to the list (based on your requirements)
        fundIds.add(1);
        fundIds.add(2);
        when(researchGroup_fundService.testQueryByResearchGroup(researchgroups.get(0).getUuid())).thenReturn(fundIds);

        Fund fund1 = new Fund(); // Create a fund object
        fund1.setFundName("Fund 1");
        fund1.setBalance(100);
        Fund fund2 = new Fund(); // Create another fund object
        fund2.setFundName("Fund 2");
        fund2.setBalance(200);
        when(fundService.queryByID(fundIds.get(0))).thenReturn(fund1);
        when(fundService.queryByID(fundIds.get(1))).thenReturn(fund2);

        mockMvc.perform(MockMvcRequestBuilders.get("/user-group-fund-data")
                        .param("uid", String.valueOf(uid)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()").value(researchgroups.size()));
    }

    @Test
    public void testGetCurrentResearchGroupFundData() throws Exception {
        int uid = 123; // Set the user ID
        List<ResearchGroup> researchgroups = new ArrayList<>(); // Create a list of research groups
        // Add some research groups to the list (based on your requirements)
        researchgroups.add(new ResearchGroup());
        researchgroups.add(new ResearchGroup());
        when(researchGroupService.testQueryByUser(uid)).thenReturn(researchgroups);

        List<Integer> fundIds = new ArrayList<>(); // Create a list of fund IDs
        // Add some fund IDs to the list (based on your requirements)
        fundIds.add(1);
        fundIds.add(2);
        when(researchGroup_fundService.testQueryByResearchGroup(researchgroups.get(0).getUuid())).thenReturn(fundIds);

        Fund fund1 = new Fund(); // Create a fund object
        fund1.setFundName("Fund 1");
        fund1.setBalance(100);
        Fund fund2 = new Fund(); // Create another fund object
        fund2.setFundName("Fund 2");
        fund2.setBalance(200);
        when(fundService.queryByID(fundIds.get(0))).thenReturn(fund1);
        when(fundService.queryByID(fundIds.get(1))).thenReturn(fund2);

        mockMvc.perform(MockMvcRequestBuilders.get("/current-group-fund-data"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()").value(researchgroups.size()-2));
    }

    // Helper method to convert an object to JSON string
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
