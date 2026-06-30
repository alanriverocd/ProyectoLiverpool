package com.liverpool.exam.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liverpool.exam.model.OrderEntity;
import com.liverpool.exam.repository.OrderRepository;
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

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OrderRepository repo;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void createAndGetOrder() throws Exception {
        OrderEntity saved = new OrderEntity();
        saved.setId("abc123");
        saved.setOrderRef("REF-1");
        saved.setUserId("user1");
        saved.setOrderStatus("CREATED");
        saved.setStoreName("Tienda A");

        given(repo.save(any(OrderEntity.class))).willReturn(saved);
        given(repo.findById("abc123")).willReturn(Optional.of(saved));

        // create
        mvc.perform(post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(saved)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("abc123"))
                .andExpect(jsonPath("$.orderRef").value("REF-1"));

        // get
        mvc.perform(get("/api/v1/orders/abc123")).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("abc123"));
    }
}
