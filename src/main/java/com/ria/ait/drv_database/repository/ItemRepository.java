package com.ria.ait.drv_database.repository;

import com.ria.ait.drv_database.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    Page<Item> findAllByItemNameOrItemNameContains(String name, String partName, Pageable pageable);

    Page<Item> findAllByDescriptionOrDescriptionContains(String desc, String partDesc, Pageable pageable);
}
