package com.liverpool.exam.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    public void byUserId_includesOrdersAndItems() throws Exception {
        String pedidosJson = "[ { \"orderRef\": \"ORD-1\", \"userId\": \"u123\", \"orderStatus\": \"EN CAMINO\", \"storeName\": \"Tienda A\", \"estimatedDelivery\": \"2026-07-01\" } ]";
        String itemsJson = "[ { \"itemId\": \"I1\", \"orderRef\": \"ORD-1\", \"displayName\": \"Producto X\", \"quantity\": 2 } ]";

        when(restTemplate.getForObject("https://6994a4eab081bc23e9c0f61e.mockapi.io/api/v1/pedidos", String.class)).thenReturn(pedidosJson);
        when(restTemplate.getForObject("https://6994a4eab081bc23e9c0f61e.mockapi.io/api/v1/items", String.class)).thenReturn(itemsJson);

        // create user
        String userJson = "{ \"userId\": \"u123\", \"nombre\": \"Juan\", \"apellidoP\": \"Perez\", \"apellidoM\": \"Lopez\", \"email\": \"j@example.com\", \"deliveryAddress\": \"Calle 1\" }";

        mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isOk());

        mvc.perform(get("/api/v1/users/by-userid/u123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orders[0].orderRef").value("ORD-1"))
                .andExpect(jsonPath("$.orders[0].items[0].displayName").value("Producto X"));
    }
}
