package com.ria.ait.drv_database.repository;

import com.ria.ait.drv_database.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, Integer> {
}
