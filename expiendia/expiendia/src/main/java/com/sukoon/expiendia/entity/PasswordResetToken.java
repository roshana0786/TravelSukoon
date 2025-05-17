//package com.sukoon.expiendia.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class PasswordResetToken {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false, unique = true)
//    private String token;
//
//    @OneToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "user_id", nullable = false)
//    private UserInfo user;
//
//    @Column(nullable = false)
//    private LocalDateTime expiryDateTime;
//}
