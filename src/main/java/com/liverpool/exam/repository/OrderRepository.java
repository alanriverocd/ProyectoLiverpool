package com.liverpool.exam.repository;

import com.liverpool.exam.model.OrderEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface OrderRepository extends MongoRepository<OrderEntity, String> {
    List<OrderEntity> findByUserId(String userId);
}
