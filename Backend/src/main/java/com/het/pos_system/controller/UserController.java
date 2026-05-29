package com.het.pos_system.controller;

import com.het.pos_system.configrations.JwtProvider;
import com.het.pos_system.entity.User;
import com.het.pos_system.exceptions.UserException;
import com.het.pos_system.mapper.UserMapper;
import com.het.pos_system.payload.dto.UserDto;
import com.het.pos_system.service.UserService;
import io.jsonwebtoken.JwtBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final AuthController authController;
    private final UserService userService;
    private final JwtProvider jwtProvider;

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfile(@RequestHeader("Authorization") String jwt) throws UserException {

        String Email = jwtProvider.getEmailFromJwtToken(jwt);

        User user = userService.getUserByEmail(Email);
        if(user == null){
            throw new UserException("User not found");
        }

        return ResponseEntity.ok(UserMapper.toDto(user));
//        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@RequestHeader("Authorization") String jwt, @PathVariable Long id) throws UserException {

        String Email = jwtProvider.getEmailFromJwtToken(jwt);

        User user = userService.getUserByEmail(Email);

//        if (user.getRole().name().equals("ROLE_ADMIN")) {
            User userById = userService.getUserById(id);
            if(userById == null){
                throw new UserException("User not found");
            }
            return ResponseEntity.ok(UserMapper.toDto(userById));
//        }
//            return ResponseEntity.status(403).build();
    }

}
