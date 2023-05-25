package com.cs304.csfunding.controller;

import com.cs304.csfunding.api.ResearchGroupDTO;
import com.cs304.csfunding.api.Result;
import com.cs304.csfunding.entity.ResearchGroup;
import com.cs304.csfunding.service.ResearchGroupService;
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

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

public class ResearchGroupControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ResearchGroupService researchGroupService;
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

        mockMvc.perform(MockMvcRequestBuilders.post("/register/researchgroup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(researchGroupDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("success"));
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

    // Helper method to convert an object to JSON string
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
