package com.sukoon.expiendia.service;

import com.sukoon.expiendia.entity.AuthRequest;
import com.sukoon.expiendia.entity.UserInfo;
import com.sukoon.expiendia.repository.UserInfoRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserInfoRepository userRepository;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService, UserInfoRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public String generateToken(AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );

            UserInfo user = userRepository.findByEmail(authRequest.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            return jwtService.generateToken(user); // Pass UserInfo object instead of email
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid email or password");
        }
    }
}
