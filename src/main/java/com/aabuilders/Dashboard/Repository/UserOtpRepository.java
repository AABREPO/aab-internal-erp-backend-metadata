package com.aabuilders.Dashboard.Repository;

import com.aabuilders.Dashboard.Entity.OtpPurpose;
import com.aabuilders.Dashboard.Entity.UserOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserOtpRepository extends JpaRepository<UserOtp, Long> {

    Optional<UserOtp> findTopByEmailAndPurposeAndUsedFalseAndExpiresAtAfterOrderByIdDesc(
            String email, OtpPurpose purpose, LocalDateTime now);

    @Modifying
    @Transactional
    void deleteByEmailAndPurpose(String email, OtpPurpose purpose);
}
