package com.cs304.csfunding.controller;

import com.cs304.csfunding.api.FundDTO;
import com.cs304.csfunding.api.Result;
import com.cs304.csfunding.controller.ApplyController;
import com.cs304.csfunding.controller.FundController;
import com.cs304.csfunding.entity.Fund;
import com.cs304.csfunding.service.FundService;
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

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class FundControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FundService fundService;
    @InjectMocks
    private FundController fundController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(fundController).build();
    }

    @Test
    public void testAddFund() throws Exception {
        FundDTO fundDTO = new FundDTO(); // Create a FundDTO object with test data

        when(fundService.testInsert(fundDTO)).thenReturn("success");

        mockMvc.perform(MockMvcRequestBuilders.post("/register/fund")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(fundDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("success"));
    }

    @Test
    public void testModifyFund_existingFund() throws Exception {
        FundDTO fundDTO = new FundDTO(); // Create a FundDTO object with test data
        Fund fund = new Fund(); // Create a Fund object with test data

        when(fundService.queryByID(fundDTO.getUuid())).thenReturn(fund);
        when(fundService.testModify(fundDTO)).thenReturn(new Result(200, "success", null));

        mockMvc.perform(MockMvcRequestBuilders.post("/modify/fund")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(fundDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }

    @Test
    public void testModifyFund_nonExistingFund() throws Exception {
        FundDTO fundDTO = new FundDTO(); // Create a FundDTO object with test data

        when(fundService.queryByID(fundDTO.getUuid())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/modify/fund")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(fundDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("fund not found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }

    @Test
    public void testDeleteFund_existingFund() throws Exception {
        int uuid = 1;
        Fund fund = new Fund(); // Create a Fund object with test data

        when(fundService.queryByID(uuid)).thenReturn(fund);

        mockMvc.perform(MockMvcRequestBuilders.get("/delete/fund")
                        .param("uuid", String.valueOf(uuid)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }

    @Test
    public void testDeleteFund_nonExistingFund() throws Exception {
        int uuid = 1;

        when(fundService.queryByID(uuid)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/delete/fund")
                        .param("uuid", String.valueOf(uuid)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("fund not found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }

    @Test
    public void testGetAllFund_fundsFound() throws Exception {
        List<Fund> funds = Arrays.asList(new Fund(), new Fund()); // Create a list of Fund objects with test data

        when(fundService.testQueryAll()).thenReturn(funds);

        mockMvc.perform(MockMvcRequestBuilders.get("/getallfunds"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()").value(2));
    }

    @Test
    public void testGetAllFund_noFundsFound() throws Exception {
        when(fundService.testQueryAll()).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/getallfunds"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("funds not found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }

    @Test
    public void testGetFundByUuid_existingFund() throws Exception {
        int uuid = 1;
        Fund fund = new Fund(); // Create a Fund object with test data

        when(fundService.queryByID(uuid)).thenReturn(fund);

        mockMvc.perform(MockMvcRequestBuilders.get("/get-fund")
                        .param("uuid", String.valueOf(uuid)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty());
    }

    @Test
    public void testGetFundByUuid_nonExistingFund() throws Exception {
        int uuid = 1;

        when(fundService.queryByID(uuid)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/get-fund")
                        .param("uuid", String.valueOf(uuid)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("fund not found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }

    @Test
    public void testGetFundVague_fundFound() throws Exception {
        String fundNumber = "ABC";
        String fundName = "XYZ";
        List<Fund> funds = Arrays.asList(new Fund(), new Fund()); // Create a list of Fund objects with test data

        when(fundService.queryVague(fundNumber, fundName)).thenReturn(funds);

        mockMvc.perform(MockMvcRequestBuilders.get("/get-fund-vague")
                        .param("FundNumber", fundNumber)
                        .param("FundName", fundName))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()").value(2));
    }

    @Test
    public void testGetFundVague_noFundFound() throws Exception {
        String fundNumber = "ABC";
        String fundName = "XYZ";

        when(fundService.queryVague(fundNumber, fundName)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/get-fund-vague")
                        .param("FundNumber", fundNumber)
                        .param("FundName", fundName))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("fund not found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
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
