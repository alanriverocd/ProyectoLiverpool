package com.liverpool.exam.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.liverpool.exam.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/search")
public class SearchController {
    private final OrderService orderService;

    public SearchController(OrderService orderService) { this.orderService = orderService; }

    @GetMapping("/pedidos")
    public ResponseEntity<List<JsonNode>> searchPedidos(@RequestParam("q") String q) {
        List<JsonNode> results = orderService.search(q);
        return ResponseEntity.ok(results);
    }
}
