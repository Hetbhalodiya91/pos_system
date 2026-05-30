package com.het.pos_system.service.impl;

import com.het.pos_system.domain.StoreStatus;
import com.het.pos_system.domain.UserRole;
import com.het.pos_system.entity.Store;
import com.het.pos_system.entity.User;
import com.het.pos_system.exceptions.ResourceNotFoundException;
import com.het.pos_system.exceptions.UserException;
import com.het.pos_system.mapper.StoreMapper;
import com.het.pos_system.mapper.UserMapper;
import com.het.pos_system.payload.dto.StoreDto;
import com.het.pos_system.payload.dto.UserDto;
import com.het.pos_system.repository.StoreRepository;
import com.het.pos_system.repository.UserRepository;
import com.het.pos_system.service.StoreService;
import com.het.pos_system.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;


    @Override
    public StoreDto createStore(StoreDto storeDto, User user) {

        Store store = StoreMapper.toEntity(storeDto, user);

        storeRepository.save(store);


        return StoreMapper.toDto(store);
    }

    @Override
    public StoreDto getStoreById(Long id) {
        Store store = storeRepository.findById(id).orElse(null);

        return StoreMapper.toDto(store);
    }

    @Override
    public List<StoreDto> getAllStores(StoreStatus status) {

//        User user = userService.getCurrentUser();

        return storeRepository.findAll().stream()
                .filter(store -> store.getStatus() == status)
                .map(StoreMapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Store getStoreByAdmin() {

        User user = userService.getCurrentUser();

        return storeRepository.findByStoreAdminId(user.getId());
    }

    @Override
    public StoreDto updateStore(Long id, StoreDto storeDto) throws Exception {

        User user = userService.getCurrentUser();
        Store store = storeRepository.findByStoreAdminId(user.getId());

        if(store == null || !store.getId().equals(id)){
            throw new Exception("Store not found or you are not authorized to update this store");
        }

         if(storeDto.getStatus() != null){
            store.setStatus(storeDto.getStatus());
        }
         if(storeDto.getStoreType() != null){
            store.setStoreType(storeDto.getStoreType());
        }
         if(storeDto.getBrand() != null){
            store.setBrand(storeDto.getBrand());
        }
         if(storeDto.getDescription() != null){
            store.setDescription(storeDto.getDescription());
        }
         if(storeDto.getContact() != null){
            store.setContact(storeDto.getContact());
        }

         Store updatedStore = storeRepository.save(store);

        return StoreMapper.toDto(updatedStore);
    }

    @Override
    public void DeleteStore() throws Exception {

            Store store = getStoreByAdmin();
            if(store == null){
                throw new Exception("Store not found or you are not authorized to delete this store");
            }
            storeRepository.delete(store);
    }

    @Override
    public StoreDto getStoreByEmployee() throws Exception {
        User currentUser = userService.getCurrentUser();
        if(currentUser == null){
            throw new Exception("User not found");
        }
        return StoreMapper.toDto(currentUser.getStore());
    }
//    @Override
//    public UserDto addEmployee(Long id, UserDto userDto) throws UserException {
//        Store store= getStoreByAdmin();
//
//        User employee = UserMapper.toEntity(userDto);
//        if(userDto.getRole()== UserRole.ROLE_STORE_MANAGER){
//            employee.setStore(store);
//        }else if(userDto.getRole()== UserRole.ROLE_BRANCH_MANAGER){
//            Branch branch=branchRepository.findById(userDto.getBranchId()).orElseThrow(
//                    ()-> new EntityNotFoundException("branch not found")
//            );
//            employee.setBranch(branch);
//            employee.setStore(store);
//        }
//
//        employee.setPassword(passwordEncoder.encode(userDto.getPassword()));
//        User addedEmployee=userRepository.save(employee);
//
//        return UserMapper.toDTO(addedEmployee);
//    }

    @Override
    public List<UserDto> getEmployeesByStore(Long storeId) throws Exception {
        User currentUser=userService.getCurrentUser();

        Store store=storeRepository.findById(storeId).orElseThrow(
                ()->new EntityNotFoundException("store not found")
        );
        if(!store.getStoreAdmin().getId().equals(currentUser.getId())
                || !currentUser.getStore().getId().equals(store.getId())){
            throw new Exception("You are not authorized to access this store's employees");
        }

        List<User> employees=userRepository.findByStoreId(storeId);
        return UserMapper.toDTOList(employees);

    }


    @Override
    public StoreDto moderateStore(Long storeId, StoreStatus action) throws ResourceNotFoundException {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found with id: " + storeId));

        store.setStatus(action);
        Store updatedStore = storeRepository.save(store);
        return StoreMapper.toDto(updatedStore);
    }

}
