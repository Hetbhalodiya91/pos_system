package com.het.pos_system.mapper;

import com.het.pos_system.entity.Store;
import com.het.pos_system.entity.User;
import com.het.pos_system.payload.dto.StoreDto;

public class StoreMapper {

    public static StoreDto toDto(Store store) {
        return StoreDto.builder()
                .id(store.getId())
                .brand(store.getBrand())
                .description(store.getDescription())
                .storeAdmin(store.getStoreAdmin() != null ? store.getStoreAdmin() : null)
                .storeType(store.getStoreType())
                .contact(store.getContact())
                .status(store.getStatus())
                .createdAt(store.getCreatedAt())
                .updatedAt(store.getUpdatedAt())
                .build();
    }

    public static Store toEntity(StoreDto storeDto , User storeAdmin) {
        return Store.builder()
                .id(storeDto.getId())
                .brand(storeDto.getBrand())
                .description(storeDto.getDescription())
                .storeAdmin(storeAdmin)
                .storeType(storeDto.getStoreType())
                .contact(storeDto.getContact())
                .status(storeDto.getStatus())
                .createdAt(storeDto.getCreatedAt())
                .updatedAt(storeDto.getUpdatedAt())
                .build();
    }
}