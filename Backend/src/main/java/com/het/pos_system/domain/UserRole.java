package com.het.pos_system.domain;

import lombok.Getter;
import lombok.Setter;

public enum UserRole {
    ROLE_ADMIN,
    ROLE_STORE_ADMIN,
    ROLE_STORE_MANAGER,
    ROLE_BRANCH_MANAGER,
    ROLE_BRANCH_ADMIN,
    ROLE_BRANCH_CASHIER,
    ROLE_CUSTOMER
}
