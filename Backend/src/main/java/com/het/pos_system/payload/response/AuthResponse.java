package com.het.pos_system.payload.response;

import com.het.pos_system.payload.dto.UserDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String jwt;
    private String message;
    private UserDto user;
}
