package com.liverpool.exam.ports;

import com.liverpool.exam.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Port interface for user-related application operations.
 * Implementations live in the adapters layer.
 */
public interface UserPort {
    User create(User u);
    Optional<User> findById(String id);
    Optional<User> findByUserId(String userId);
    List<User> findAll();
    void delete(String id);
    User update(User u);
    java.util.List<com.liverpool.exam.model.OrderSummary> resolveOrdersForUser(String userId);
}
