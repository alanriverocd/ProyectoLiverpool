package com.liverpool.exam.service;

import com.liverpool.exam.model.OrderSummary;
import com.liverpool.exam.model.User;
import com.liverpool.exam.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repo;
    private final OrderService orderService;

    public UserService(UserRepository repo, OrderService orderService) {
        this.repo = repo;
        this.orderService = orderService;
    }

    public User create(User u) { return repo.save(u); }
    public Optional<User> findById(String id) { return repo.findById(id); }
    public Optional<User> findByUserId(String userId) { return repo.findByUserId(userId); }
    public List<User> findAll() { return repo.findAll(); }
    public void delete(String id) { repo.deleteById(id); }
    public User update(User u) { return repo.save(u); }

    // populate orders field by calling external /pedidos
    public List<OrderSummary> resolveOrdersForUser(String userId) {
        List<OrderSummary> out = new ArrayList<>();
        JsonNode[] pedidos = orderService.fetchPedidosRaw();
        for (JsonNode p : pedidos) {
            if (!p.path("userId").asText("").equals(userId)) continue;
            OrderSummary s = new OrderSummary();
            s.setOrderRef(p.path("orderRef").asText(""));
            s.setOrderStatus(p.path("orderStatus").asText(""));
            s.setStoreName(p.path("storeName").asText(""));
            s.setEstimatedDelivery(p.path("estimatedDelivery").asText(""));
            out.add(s);
        }
        return out;
    }
}
