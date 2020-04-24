package com.ria.ait.drv_database.repository;

import com.ria.ait.drv_database.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Integer> {
    Optional<Stock> findByItem_ItemId(int id);
}
