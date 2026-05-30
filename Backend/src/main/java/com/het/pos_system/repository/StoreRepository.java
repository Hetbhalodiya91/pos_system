package com.het.pos_system.repository;

import com.het.pos_system.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Store findByStoreAdminId(Long storeAdminId);
}
