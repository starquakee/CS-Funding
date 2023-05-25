package com.cs304.csfunding.controller;

import com.cs304.csfunding.api.NoticeDTO;
import com.cs304.csfunding.api.Result;
import com.cs304.csfunding.controller.NoticeController;
import com.cs304.csfunding.controller.UserController;
import com.cs304.csfunding.entity.Notice;
import com.cs304.csfunding.service.NoticeService;
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

public class NoticeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private NoticeService noticeService;
    @InjectMocks
    private NoticeController noticeController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(noticeController).build();
    }

    @Test
    public void testAddNotice() throws Exception {
        NoticeDTO noticeDTO = new NoticeDTO(); // Create a NoticeDTO object with test data

        when(noticeService.testInsert(noticeDTO)).thenReturn("success");

        mockMvc.perform(MockMvcRequestBuilders.post("/register/notice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(noticeDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @Test
    public void testDeleteNotice_existingNotice() throws Exception {
        int uuid = 123;
        Notice notice = new Notice(); // Create a Notice object with test data

        when(noticeService.testQueryByID(uuid)).thenReturn(notice);

        mockMvc.perform(MockMvcRequestBuilders.get("/delete/notice")
                        .param("uuid", String.valueOf(uuid)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }

    @Test
    public void testDeleteNotice_nonExistingNotice() throws Exception {
        int uuid = 123;

        when(noticeService.testQueryByID(uuid)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/delete/notice")
                        .param("uuid", String.valueOf(uuid)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("notice not found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }

    @Test
    public void testGetAllNotice() throws Exception {
        List<Notice> notices = Arrays.asList(new Notice(), new Notice()); // Create a list of Notice objects with test data

        when(noticeService.testQueryAll()).thenReturn(notices);

        mockMvc.perform(MockMvcRequestBuilders.get("/getallnotices"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()").value(2));
    }

    @Test
    public void testGetNoticeByID_existingNotice() throws Exception {
        int uuid = 123;
        Notice notice = new Notice(); // Create a Notice object with test data

        when(noticeService.testQueryByID(uuid)).thenReturn(notice);

        mockMvc.perform(MockMvcRequestBuilders.get("/get/notice")
                        .param("uuid", String.valueOf(uuid)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty());
    }

    @Test
    public void testGetNoticeByID_nonExistingNotice() throws Exception {
        int uuid = 123;

        when(noticeService.testQueryByID(uuid)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/get/notice")
                        .param("uuid", String.valueOf(uuid)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("notice not found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }

    @Test
    public void testGetNoticeByUserID() throws Exception {
        int userId = 123;
        List<Notice> notices = Arrays.asList(new Notice(), new Notice()); // Create a list of Notice objects with test data

        when(noticeService.testQueryByNoticeTo(userId)).thenReturn(notices);

        mockMvc.perform(MockMvcRequestBuilders.get("/get/usernotice"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray());
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
