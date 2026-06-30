package com.liverpool.exam.controller;

import com.liverpool.exam.model.OrderSummary;
import com.liverpool.exam.model.User;
import com.liverpool.exam.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Validated
public class UserController {
    private final UserService svc;

    public UserController(UserService svc) { this.svc = svc; }

    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody User u) {
        User saved = svc.create(u);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<User>> all() { return ResponseEntity.ok(svc.findAll()); }

    @GetMapping("/by-userid/{userId}")
    public ResponseEntity<User> byUserId(@PathVariable String userId) {
        return svc.findByUserId(userId)
                .map(u -> {
                    List<OrderSummary> orders = svc.resolveOrdersForUser(userId);
                    u.setOrders(orders);
                    return ResponseEntity.ok(u);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable String id) {
        return svc.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable String id, @Valid @RequestBody User u) {
        return svc.findById(id).map(existing -> {
            u.setId(existing.getId());
            return ResponseEntity.ok(svc.update(u));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        svc.delete(id);
        return ResponseEntity.noContent().build();
    }
}
