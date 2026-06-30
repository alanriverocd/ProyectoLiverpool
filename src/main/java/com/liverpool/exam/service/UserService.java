package com.liverpool.exam.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.liverpool.exam.model.OrderSummary;
import com.liverpool.exam.model.User;
import com.liverpool.exam.ports.UserPort;
import com.liverpool.exam.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the User application port.
 * Contains user CRUD operations and order enrichment logic.
 */
@Service
public class UserService implements UserPort {
    private final UserRepository repo;
    private final OrderService orderService;

    public UserService(UserRepository repo, OrderService orderService) {
        this.repo = repo;
        this.orderService = orderService;
    }

    @Override
    public User create(User u) { return repo.save(u); }

    @Override
    public Optional<User> findById(String id) { return repo.findById(id); }

    @Override
    public Optional<User> findByUserId(String userId) { return repo.findFirstByUserId(userId); }

    @Override
    public List<User> findAll() { return repo.findAll(); }

    @Override
    public void delete(String id) { repo.deleteById(id); }

    @Override
    public User update(User u) { return repo.save(u); }

    /**
     * Populate orders field by calling external /pedidos and /items adapters.
     * This is application logic kept in the service (core) layer.
     */
    public List<OrderSummary> resolveOrdersForUser(String userId) {
        List<OrderSummary> out = new ArrayList<>();
        JsonNode[] pedidos = orderService.fetchPedidosRaw();
        JsonNode[] items = orderService.fetchItemsRaw();
        for (JsonNode p : pedidos) {
            if (!p.path("userId").asText("").equals(userId)) continue;
            OrderSummary s = new OrderSummary();
            s.setOrderRef(p.path("orderRef").asText(""));
            s.setOrderStatus(p.path("orderStatus").asText(""));
            s.setStoreName(p.path("storeName").asText(""));
            s.setEstimatedDelivery(p.path("estimatedDelivery").asText(""));
            // attach items for this order
            java.util.List<com.liverpool.exam.model.ItemSummary> list = new java.util.ArrayList<>();
            String pedidoRef = p.path("orderRef").asText("");
            for (JsonNode it : items) {
                String itemOrderRef = it.path("orderRef").asText("");
                if (!itemOrderRef.equals(pedidoRef)) continue;
                com.liverpool.exam.model.ItemSummary is = new com.liverpool.exam.model.ItemSummary();
                is.setItemId(it.path("itemId").asText(""));
                is.setDisplayName(it.path("displayName").asText(""));
                try { is.setQuantity(it.path("quantity").asInt(1)); } catch (Exception e) { is.setQuantity(1); }
                list.add(is);
            }
            s.setItems(list);
            out.add(s);
        }
        return out;
    }
}
