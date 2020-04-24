package com.ria.ait.drv_database.repository;

import com.ria.ait.drv_database.model.ReviewItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewItemRepository extends JpaRepository<ReviewItem, Integer> {
}
