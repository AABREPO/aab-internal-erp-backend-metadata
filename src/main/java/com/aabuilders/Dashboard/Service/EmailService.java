package com.aabuilders.Dashboard.Service;

import com.aabuilders.Dashboard.Entity.OtpPurpose;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    @Value("${app.mail.from:noreply@backendaab.in}")
    private String fromEmail;

    @Value("${spring.mail.username:}")
    private String mailUsername;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOtpEmail(String toEmail, String otp, OtpPurpose purpose) {
        String subject = purpose == OtpPurpose.REGISTRATION
                ? "AA Builders - Email Verification OTP"
                : "AA Builders - Password Reset OTP";

        String body = purpose == OtpPurpose.REGISTRATION
                ? "Your email verification OTP is: " + otp + "\n\nThis OTP expires in 10 minutes."
                : "Your password reset OTP is: " + otp + "\n\nThis OTP expires in 10 minutes. Do not share it with anyone.";

        if (mailUsername == null || mailUsername.isBlank()) {
            log.warn("Mail not configured. OTP for {} ({}): {}", toEmail, purpose, otp);
            return;
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
        } catch (Exception e) {
            log.error("Failed to send OTP email to {}", toEmail, e);
            log.warn("OTP for {} ({}): {}", toEmail, purpose, otp);
        }
    }
}
