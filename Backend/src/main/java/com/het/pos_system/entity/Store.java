package com.het.pos_system.entity;

import com.het.pos_system.domain.StoreStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String brand;

    @OneToOne
    @JoinColumn(name = "store_admin_id")
    private User storeAdmin;

    @Column(length = 500)
    private String description;

    private String storeType;
    private StoreStatus status;

    @Embedded
    private StoreContact contact = new StoreContact();


    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    void onCreate() {
        createdAt = updatedAt = LocalDateTime.now();
        status = StoreStatus.PENDING;
    }
    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
    }


}
