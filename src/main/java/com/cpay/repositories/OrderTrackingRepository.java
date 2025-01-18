package com.cpay.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.cpay.entities.OrderTracking;
import com.cpay.entities.CreditCardApplication;
import java.util.Optional;

@Repository
public interface OrderTrackingRepository extends CrudRepository<OrderTracking, Long> {

    // Custom query to find an OrderTracking by orderId
    Optional<OrderTracking> findByOrderId(Long orderId);

    // Custom query to find OrderTracking by CreditCardApplication
    Optional<OrderTracking> findByCreditCardApplication(CreditCardApplication application);
}
