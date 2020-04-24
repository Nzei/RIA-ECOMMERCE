package com.ria.ait.drv_database.repository;

import com.ria.ait.drv_database.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {
}
