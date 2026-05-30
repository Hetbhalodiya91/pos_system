package com.het.pos_system.payload.dto;

import com.het.pos_system.domain.StoreStatus;
import com.het.pos_system.entity.StoreContact;
import com.het.pos_system.entity.User;
import jakarta.annotation.security.DenyAll;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreDto {

    private Long id;
    private String brand;
    private User storeAdmin;
    private String description;
    private String storeType;
    private StoreStatus status;
    private StoreContact contact ;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
