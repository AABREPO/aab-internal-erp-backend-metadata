package com.aabuilders.Dashboard.Service;

import com.aabuilders.Dashboard.Entity.OtpPurpose;
import com.aabuilders.Dashboard.Entity.UserOtp;
import com.aabuilders.Dashboard.Repository.UserOtpRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OtpService {

    private final UserOtpRepository userOtpRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final SecureRandom secureRandom = new SecureRandom();

    @Value("${app.otp.expiry-minutes:10}")
    private int otpExpiryMinutes;

    public OtpService(UserOtpRepository userOtpRepository,
                      PasswordEncoder passwordEncoder,
                      EmailService emailService) {
        this.userOtpRepository = userOtpRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Transactional
    public void generateAndSendOtp(String email, OtpPurpose purpose) {
        userOtpRepository.deleteByEmailAndPurpose(email, purpose);

        String otp = String.format("%06d", secureRandom.nextInt(1_000_000));

        UserOtp userOtp = new UserOtp();
        userOtp.setEmail(email);
        userOtp.setOtpHash(passwordEncoder.encode(otp));
        userOtp.setPurpose(purpose);
        userOtp.setExpiresAt(LocalDateTime.now().plusMinutes(otpExpiryMinutes));
        userOtp.setUsed(false);
        userOtpRepository.save(userOtp);

        emailService.sendOtpEmail(email, otp, purpose);
    }

    @Transactional
    public boolean verifyOtp(String email, String otp, OtpPurpose purpose) {
        Optional<UserOtp> otpRecord = userOtpRepository
                .findTopByEmailAndPurposeAndUsedFalseAndExpiresAtAfterOrderByIdDesc(
                        email, purpose, LocalDateTime.now());

        if (otpRecord.isEmpty()) {
            return false;
        }

        UserOtp userOtp = otpRecord.get();
        if (!passwordEncoder.matches(otp, userOtp.getOtpHash())) {
            return false;
        }

        userOtp.setUsed(true);
        userOtpRepository.save(userOtp);
        return true;
    }
}
