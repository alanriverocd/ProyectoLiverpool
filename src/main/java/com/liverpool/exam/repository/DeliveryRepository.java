package com.liverpool.exam.repository;

import com.liverpool.exam.model.DeliveryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface DeliveryRepository extends MongoRepository<DeliveryEntity, String> {
    List<DeliveryEntity> findByUserId(String userId);
}
