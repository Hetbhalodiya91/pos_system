package com.het.pos_system.controller;

import com.het.pos_system.exceptions.UserException;
import com.het.pos_system.payload.dto.UserDto;
import com.het.pos_system.payload.request.LoginRequest;
import com.het.pos_system.payload.response.AuthResponse;
import com.het.pos_system.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signupHandler(
            @RequestBody UserDto req
    ) throws UserException {

        return ResponseEntity.status(201).body(authService.signup(req));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginHandler(
            @RequestBody LoginRequest req
    ) throws UserException {

        return ResponseEntity.ok(
                authService.login(req.getUsername(), req.getPassword())
        );
    }
}
