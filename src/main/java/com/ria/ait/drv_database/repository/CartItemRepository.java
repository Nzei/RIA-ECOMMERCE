package com.ria.ait.drv_database.repository;

import com.ria.ait.drv_database.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository <CartItem, Integer> {
}
