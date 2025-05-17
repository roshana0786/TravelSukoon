package com.sukoon.expiendia.controller;


import com.sukoon.expiendia.admin.entity.TourPackage;
import com.sukoon.expiendia.admin.service.TourPackageService;
import com.sukoon.expiendia.dto.PasswordRequestDTO;
import com.sukoon.expiendia.entity.AuthRequest;
import com.sukoon.expiendia.entity.UserInfo;
import com.sukoon.expiendia.service.JwtService;
import com.sukoon.expiendia.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private TourPackageService tourPackageService;
    private final UserInfoService service;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder; // Inject PasswordEncoder

    @Autowired
    public UserController(
            @Qualifier("userInfoService") UserInfoService service,
            JwtService jwtService,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder) {
        this.service = service;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/welcome")
    public ResponseEntity<String> welcome() {
        return ResponseEntity.ok("Welcome, this endpoint is not secure.");
    }

    @PostMapping("/addNewUser")
    public ResponseEntity<?> addNewUser(@RequestBody UserInfo userInfo) {
        UserInfo savedUser = service.addUser(userInfo); // Ensure this method returns the saved user object

        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", 200);
        response.put("message", "User Saved Successfully");

        // User details in response
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("id", savedUser.getId());
        userDetails.put("email", savedUser.getEmail());
        userDetails.put("name", savedUser.getName());
        userDetails.put("password", savedUser.getPassword()); // Encrypted password
        userDetails.put("role", savedUser.getRole().name());
        userDetails.put("enabled", savedUser.isEnabled());
        userDetails.put("authorities", savedUser.getAuthorities());

        response.put("ourUsers", userDetails);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal UserInfo userInfo) {
        if (userInfo != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("statusCode", 200);
            response.put("message", "successful");
            response.put("ourUsers", userInfo);

            // Dynamic Role Handling
            if (userInfo.getRole().name().equalsIgnoreCase("ADMIN")) {
                response.put("profileType", "Admin Profile");
            } else if (userInfo.getRole().name().equalsIgnoreCase("USER")) {
                response.put("profileType", "User Profile");
            }

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
    }

    @GetMapping("/getAllUsers")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        List<UserInfo> users = service.getAllUsers();  // No cast required
        return ResponseEntity.ok(users);
    }

    @PostMapping("/check-email")
    public ResponseEntity<?> checkEmailAndGenerateToken(@RequestParam String email) {
        return service.checkEmailAndGenerateToken(email);
    }

    @PostMapping("/generateToken")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );

            if (authentication.isAuthenticated()) {
                UserInfo user = service.getUserByEmail(authRequest.getEmail())
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

                // Generate Access Token
                String token = jwtService.generateToken(user);

                // Generate Refresh Token (Optional but recommended for security)
                String refreshToken = jwtService.generateRefreshToken(user);

                // Response structure as per the screenshot
                Map<String, Object> response = new HashMap<>();
                response.put("statusCode", 200);
                response.put("message", "Successfully Logged In");
                response.put("token", token);
                response.put("refreshToken", refreshToken);
                response.put("expirationTime", "24Hrs");  // Customize the expiry duration if needed

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Invalid credentials!"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid email or password!"));
        }
    }

    @PostMapping("/set-password")
    public ResponseEntity<?> setPassword(@RequestBody PasswordRequestDTO passwordRequestDTO) {
        return service.processSetPassword(passwordRequestDTO);
    }

    // Get all tour packages (accessible to users)
    @GetMapping("/getAllPackages")
    public ResponseEntity<List<TourPackage>> getAllTourPackagesForUsers() {
        List<TourPackage> allPackages = tourPackageService.getAllPackages();
        return ResponseEntity.ok(allPackages);
    }

    // Get specific tour package by ID (accessible to users)
    @GetMapping("{id}")
    public ResponseEntity<TourPackage> getTourPackageByIdForUsers(@PathVariable Long id) {
        return tourPackageService.getPackageById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }
}