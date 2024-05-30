package com.microservice.orderservice.repository;

import com.microservice.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUserId(Long userId);

    @Query(value = "SELECT id FROM Order WHERE status = 'IN_PROGRESS'")
    List<Long> findAllByStatusIsInProgress();
}
