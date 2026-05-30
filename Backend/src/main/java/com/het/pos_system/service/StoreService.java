package com.het.pos_system.service;

import com.het.pos_system.domain.StoreStatus;
import com.het.pos_system.entity.Store;
import com.het.pos_system.entity.User;
import com.het.pos_system.exceptions.ResourceNotFoundException;
import com.het.pos_system.exceptions.UserException;
import com.het.pos_system.payload.dto.StoreDto;
import com.het.pos_system.payload.dto.UserDto;

import java.util.List;

public interface StoreService {

    StoreDto createStore(StoreDto storeDto , User user);
    StoreDto getStoreById(Long id);
    List<StoreDto> getAllStores( StoreStatus status );
    Store getStoreByAdmin();
    StoreDto updateStore(Long id, StoreDto storeDto) throws Exception;
    void DeleteStore() throws Exception;
    StoreDto getStoreByEmployee() throws Exception;
//    UserDto addEmployee(Long id, UserDto userDto) throws UserException;
    List<UserDto> getEmployeesByStore(Long storeId) throws Exception;
    StoreDto moderateStore(Long storeId, StoreStatus action) throws ResourceNotFoundException;

}
