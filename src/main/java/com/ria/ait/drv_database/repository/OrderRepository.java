package com.ria.ait.drv_database.repository;

import com.ria.ait.drv_database.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
