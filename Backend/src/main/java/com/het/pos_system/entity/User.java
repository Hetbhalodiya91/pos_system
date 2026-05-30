package com.het.pos_system.entity;

import com.het.pos_system.domain.UserRole;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String fullName;
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;
//
//    @ManyToOne
//    private Branch branch;


    private UserRole Role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean verified;
    private LocalDateTime lastLogin;

}
