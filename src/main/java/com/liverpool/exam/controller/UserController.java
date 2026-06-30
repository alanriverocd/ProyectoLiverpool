package com.liverpool.exam.controller;

import com.liverpool.exam.model.OrderSummary;
import com.liverpool.exam.model.User;
import com.liverpool.exam.ports.UserPort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Validated
public class UserController {
    /**
     * REST adapter for user operations. Acts as an entry-point adapter (controller)
     * and delegates business work to the application port `UserPort`.
     */
    private final UserPort svc;

    public UserController(UserPort svc) { this.svc = svc; }

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
