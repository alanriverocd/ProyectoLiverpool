package com.liverpool.exam.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liverpool.exam.model.DeliveryEntity;
import com.liverpool.exam.repository.DeliveryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeliveryController.class)
public class DeliveryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DeliveryRepository repo;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void createAndGetDelivery() throws Exception {
        DeliveryEntity saved = new DeliveryEntity();
        saved.setId("del1");
        saved.setUserId("user1");
        saved.setAddress("Calle Falsa 123");
        saved.setPhone("555-0101");
        saved.setNotes("Fragile");

        given(repo.save(any(DeliveryEntity.class))).willReturn(saved);
        given(repo.findById("del1")).willReturn(Optional.of(saved));

        mvc.perform(post("/api/v1/deliveries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(saved)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("del1"))
                .andExpect(jsonPath("$.address").value("Calle Falsa 123"));

        mvc.perform(get("/api/v1/deliveries/del1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("del1"));
    }
}
