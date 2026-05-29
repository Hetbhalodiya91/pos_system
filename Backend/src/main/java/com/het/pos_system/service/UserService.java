package com.het.pos_system.service;

import com.het.pos_system.entity.User;

import java.util.List;

public interface UserService {
    User getUserByJwtToken(String JwtToken);
    User getCurrentUser();
    User getUserByEmail(String email);
    User getUserById(Long id);
    List<User> getAllUsers();
}
