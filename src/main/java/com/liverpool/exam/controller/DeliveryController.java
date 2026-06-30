package com.liverpool.exam.controller;

import com.liverpool.exam.model.DeliveryEntity;
import com.liverpool.exam.repository.DeliveryRepository;
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
@RequestMapping("/api/v1/deliveries")
public class DeliveryController {
    /**
     * REST adapter for CRUD operations on deliveries.
     */
    private final DeliveryRepository repo;

    public DeliveryController(DeliveryRepository repo) { this.repo = repo; }

    @GetMapping
    public List<DeliveryEntity> list() { return repo.findAll(); }

    @GetMapping("/by-user/{userId}")
    public List<DeliveryEntity> byUser(@PathVariable String userId) { return repo.findByUserId(userId); }

    @PostMapping
    public DeliveryEntity create(@RequestBody DeliveryEntity e) { return repo.save(e); }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryEntity> get(@PathVariable String id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeliveryEntity> update(@PathVariable String id, @RequestBody DeliveryEntity updated) {
        return repo.findById(id).map(existing -> {
            existing.setAddress(updated.getAddress());
            existing.setPhone(updated.getPhone());
            existing.setNotes(updated.getNotes());
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
