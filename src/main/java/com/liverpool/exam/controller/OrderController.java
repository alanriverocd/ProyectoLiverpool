package com.liverpool.exam.controller;

import com.liverpool.exam.model.OrderEntity;
import com.liverpool.exam.repository.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    /**
     * REST adapter for CRUD operations on orders.
     */
    private final OrderRepository repo;

    public OrderController(OrderRepository repo) { this.repo = repo; }

    @GetMapping
    public List<OrderEntity> list() { return repo.findAll(); }

    @GetMapping("/by-user/{userId}")
    public List<OrderEntity> byUser(@PathVariable String userId) { return repo.findByUserId(userId); }

    @PostMapping
    public OrderEntity create(@RequestBody OrderEntity e) { return repo.save(e); }

    @GetMapping("/{id}")
    public ResponseEntity<OrderEntity> get(@PathVariable String id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderEntity> update(@PathVariable String id, @RequestBody OrderEntity updated) {
        return repo.findById(id).map(existing -> {
            existing.setOrderRef(updated.getOrderRef());
            existing.setOrderStatus(updated.getOrderStatus());
            existing.setStoreName(updated.getStoreName());
            existing.setUserId(updated.getUserId());
            return ResponseEntity.ok(repo.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
