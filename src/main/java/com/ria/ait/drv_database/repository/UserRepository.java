package com.ria.ait.drv_database.repository;

import com.ria.ait.drv_database.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

//    Page<User> findAllByUsernameOrUsernameContains(String username, String username2, Pageable pageable);
}
