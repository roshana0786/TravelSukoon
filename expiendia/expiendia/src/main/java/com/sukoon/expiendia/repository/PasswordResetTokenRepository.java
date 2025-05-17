//package com.sukoon.expiendia.repository;
//
//import com.sukoon.expiendia.entity.PasswordResetToken;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//
//@Repository
//public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> { // Assuming Long as ID type
//    Optional<PasswordResetToken> findByToken(String token);
//}