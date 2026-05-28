package com.het.pos_system.service;

import com.het.pos_system.exceptions.UserException;
import com.het.pos_system.payload.dto.UserDto;
import com.het.pos_system.payload.response.AuthResponse;

public interface AuthService {

    AuthResponse signup(UserDto userDto) throws UserException;
    AuthResponse login(String email, String password) throws UserException;
}
