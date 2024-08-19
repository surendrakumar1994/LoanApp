package com.bank.notification;

import com.bank.notification.controller.NotificationController;
import com.bank.notification.entity.Notification;
import com.bank.notification.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class NotificationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(notificationController).build();
    }

    @Test
    public void testGetNotificationById() throws Exception {
        Notification notification = new Notification();
        notification.setId(1L);
      //  ((Object) notification).setMessage("Test Notification");

        when(notificationService.getNotificationById(anyLong())).thenReturn(notification);

        mockMvc.perform(MockMvcRequestBuilders.get("/notification/get/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
        //        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Test Notification"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testCreateNotification() throws Exception {
        Notification notification = new Notification();
        notification.setId(1L);
    //    notification.setMessage("Test Notification");

     //   when(notificationService.createNotification(any(Notification.class))).thenReturn(notification);

        mockMvc.perform(MockMvcRequestBuilders.post("/notification/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"message\":\"Test Notification\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }
}
