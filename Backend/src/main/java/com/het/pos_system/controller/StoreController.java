package com.het.pos_system.controller;


import com.het.pos_system.domain.StoreStatus;
import com.het.pos_system.entity.Store;
import com.het.pos_system.entity.User;
import com.het.pos_system.exceptions.ResourceNotFoundException;
import com.het.pos_system.exceptions.UserException;
import com.het.pos_system.mapper.StoreMapper;
import com.het.pos_system.payload.dto.StoreDto;
import com.het.pos_system.payload.dto.UserDto;
import com.het.pos_system.payload.response.ApiResponse;
import com.het.pos_system.service.StoreService;
import com.het.pos_system.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;
    private final UserService userService;

    // 🔹 Create Store
    @PostMapping
    public ResponseEntity<StoreDto> createStore(@Valid @RequestBody StoreDto storeDto,
                                                @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.getUserByJwtToken(jwt);
        return ResponseEntity.ok(storeService.createStore(storeDto, user));
    }

    // 🔹 Get Store by ID
    @GetMapping("/{id}")
    public ResponseEntity<StoreDto> getStoreById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(storeService.getStoreById(id));
    }



    // 🔹 Update Store
    @PutMapping("/{id}")
    public ResponseEntity<StoreDto> updateStore(
            @PathVariable Long id,
            @RequestBody StoreDto storeDto)
            throws Exception,
            UserException {
        return ResponseEntity.ok(storeService.updateStore(id, storeDto));
    }

    // 🔹 Delete Store
    @DeleteMapping()
    public ResponseEntity<ApiResponse> deleteStore()
            throws Exception, UserException {
        storeService.DeleteStore();
        return ResponseEntity.ok(new ApiResponse("store deleted successfully"));
    }


    @GetMapping("/admin")
    public ResponseEntity<StoreDto> getStoresByAdminId() throws UserException {
        Store store=storeService.getStoreByAdmin();
        return ResponseEntity.ok(StoreMapper.toDto(store));
    }

    @GetMapping("/employee")
    public ResponseEntity<StoreDto> getStoresByEmployee() throws UserException, Exception {
        StoreDto store=storeService.getStoreByEmployee();
        return ResponseEntity.ok(store);
    }

    @GetMapping("/{storeId}/employee/list")
    @PreAuthorize("hasAnyAuthority('ROLE_STORE_MANAGER', 'ROLE_STORE_ADMIN')")
    public ResponseEntity<List<UserDto>> getStoreEmployeeList(
            @PathVariable Long storeId) throws UserException, Exception {
        List<UserDto> users=storeService.getEmployeesByStore(storeId);
        return ResponseEntity.ok(users);
    }

//    @PostMapping("/add/employee")
//    @PreAuthorize("hasAnyAuthority('STORE_MANAGER','STORE_ADMIN')")
//    public ResponseEntity<UserDto> addEmployee(
//            @RequestBody UserDto userDto) throws UserException {
//        UserDto user=storeService.addEmployee(null, userDto);
//        return ResponseEntity.ok(user);
//    }

//    super admin action

    // 🔹 Get All Stores (without pagination)
    @GetMapping
    public ResponseEntity<List<StoreDto>> getAllStores(
            @RequestParam(required = false) StoreStatus status
    ) {
        return ResponseEntity.ok(storeService.getAllStores(status));
    }

    /**
     * Approve or decline a store request
     * @param storeId the store ID
     * @param action the action to perform (APPROVE or DECLINE)
     * @return updated StoreDTO
     */
    @PutMapping("/{storeId}/moderate")
    public StoreDto moderateStore(
            @PathVariable Long storeId,
            @RequestParam StoreStatus action
    ) throws ResourceNotFoundException {
        return storeService.moderateStore(storeId, action);
    }
}
