package com.het.pos_system.service.impl;

import com.het.pos_system.configrations.JwtProvider;
import com.het.pos_system.domain.UserRole;
import com.het.pos_system.entity.User;
import com.het.pos_system.exceptions.UserException;
import com.het.pos_system.mapper.UserMapper;
import com.het.pos_system.payload.dto.UserDto;
import com.het.pos_system.payload.response.AuthResponse;
import com.het.pos_system.repository.UserRepository;
import com.het.pos_system.service.AuthService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserImplementation customUserImplementation;

    @Override
    public AuthResponse signup(UserDto userDto) throws UserException {
        User user = userRepository.findByEmail(userDto.getEmail());
        if (user != null) {
            throw new UserException("Email already in use");
        }
        if(userDto.getRole().equals(UserRole.ROLE_ADMIN)){

                throw new UserException("ROLE ADMIN cannot be assigned to a user");

        }
        // Create new user
        user = User.builder()
                .fullName(userDto.getFullName())
                .email(userDto.getEmail())
                .phoneNumber(userDto.getPhoneNumber())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .createdAt(LocalDateTime.now())
                .lastLogin(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        user.setRole(userDto.getRole());;
        userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse response = AuthResponse.builder()
                .jwt(jwt)
                .message("User registered successfully")
                .user(UserMapper.toDto(user))
                .build();
        return response;
    }

    @Override
    public AuthResponse login(String email, String password) throws UserException {
        Authentication authentication = authenticate(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role =  authorities.iterator().next().getAuthority();
        String token = jwtProvider.generateToken(authentication);

        User user = userRepository.findByEmail(email);

//        update last Login
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        AuthResponse response = AuthResponse.builder()
                .jwt(token)
                .message("User logged in successfully")
                .user(UserMapper.toDto(user))
                .build();

        return response;
    }
    public Authentication authenticate(String email, String password) throws UserException {

        UserDetails userDetails = customUserImplementation.loadUserByUsername(email);
        if(userDetails == null) {
            throw new UserException("email id doesn't exist "+ email);
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new UserException("Wrong Password ");
        }
        return new UsernamePasswordAuthenticationToken(email, null, userDetails.getAuthorities());
    }
}
