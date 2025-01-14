package com.cpay.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import com.cpay.entities.OrderTracking;

public interface OrderTrackingRepository extends CrudRepository<OrderTracking, Long> {

    // Modify to return Optional<OrderTracking>
    Optional<OrderTracking> findByCreditCardApplicationId(Long applicationId);
}
