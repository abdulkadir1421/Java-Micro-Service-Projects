package com.micriserviceprogramm.orderservice.repository;
import com.micriserviceprogramm.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
