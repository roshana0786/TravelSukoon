package com.sukoon.expiendia.service;

import com.sukoon.expiendia.dto.PasswordRequestDTO;
import com.sukoon.expiendia.entity.Role;
import com.sukoon.expiendia.entity.UserInfo;

import com.sukoon.expiendia.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserInfoRepository repository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder encoder;

//    @Autowired
//    private PasswordResetTokenRepository passwordResetTokenRepository; // Need this repository

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    public Optional<UserInfo> getUserByEmail(String email) {
        return repository.findByEmail(email);
    }

    public UserInfo addUser(UserInfo userInfo) {
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        userInfo.setEnabled(true);  // Enable user by default
        return repository.save(userInfo);  // Return the saved UserInfo object
    }

    // New method to fetch all users
    public List<UserInfo> getAllUsers() {
        return repository.findAll();
    }

    public ResponseEntity<?> checkEmailAndGenerateToken(String email) {
        Optional<UserInfo> optionalUser = repository.findByEmail(email);

        if (optionalUser.isPresent()) {
            // User exists, generate a password reset token
//            UserInfo user = optionalUser.get();
//            String token = UUID.randomUUID().toString();
//            createPasswordResetTokenForUser(user, token);
            //emailService.sendResetPasswordEmail(email, token);
            return ResponseEntity.ok("User is already Register.");
        }

        // Create token and new user
        String token = UUID.randomUUID().toString();
        UserInfo newUser = new UserInfo();
        newUser.setEmail(email);
        newUser.setToken(token);
        newUser.setRole(Role.USER);
        newUser.setEnabled(false);
        newUser.setPassword(encoder.encode("TEMP")); // Encode temporary password
        newUser.setName("PendingUser");

        repository.save(newUser);

        // Send registration email
        emailService.sendResetPasswordEmail(email, token); // Assuming sendResetPasswordEmail can handle registration tokens

        return ResponseEntity.ok("New user created. Email sent to complete registration.");
    }

//    public void createPasswordResetTokenForUser(UserInfo user, String token) {
//        PasswordResetToken passwordResetToken = new PasswordResetToken();
//        passwordResetToken.setToken(token);
//        passwordResetToken.setUser(user);
//        passwordResetToken.setExpiryDateTime(LocalDateTime.now().plusHours(24));
//        passwordResetTokenRepository.save(passwordResetToken);
//    }

    public Optional<UserInfo> getPasswordResetToken(String token) {
        return repository.findByToken(token);
    }

    public void deletePasswordResetToken(String token) {
        repository.deleteByToken(token);
    }

    public void updatePassword(UserInfo user, String newPassword) {
        user.setPassword(encoder.encode(newPassword));
        repository.save(user);
    }

    public ResponseEntity<?> processSetPassword(PasswordRequestDTO passwordRequestDTO) {
        Optional<UserInfo> passwordResetToken = getPasswordResetToken(passwordRequestDTO.getToken());

        if (passwordResetToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Invalid or expired token."));
        }
        UserInfo user = passwordResetToken.get();
        updatePassword(user, passwordRequestDTO.getPassword());
        user.setEnabled(true); // Enable the user after setting the password (for registration flow)
        user.setToken(null); // Clear the registration token if you stored it in UserInfo
        repository.save(user);
        deletePasswordResetToken(passwordRequestDTO.getPassword());

        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", 200);
        response.put("message", "Password set successfully. You can now log in.");
        return ResponseEntity.ok(response);
    }
}