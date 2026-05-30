package com.het.pos_system.repository;

import com.het.pos_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByFullName(String username);
    User findByEmail(String email);
    List<User> findByStoreId(Long storeId);
}
