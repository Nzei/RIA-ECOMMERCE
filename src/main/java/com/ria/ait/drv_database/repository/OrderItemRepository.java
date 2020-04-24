package com.ria.ait.drv_database.repository;

import com.ria.ait.drv_database.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository <OrderItem, Integer> {
}
