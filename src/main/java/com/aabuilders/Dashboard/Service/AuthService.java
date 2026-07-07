package com.aabuilders.Dashboard.Service;

import com.aabuilders.Dashboard.DTO.LoginRequestDto;
import com.aabuilders.Dashboard.DTO.LoginResponseDto;
import com.aabuilders.Dashboard.DTO.RegisterConfirmDto;
import com.aabuilders.Dashboard.DTO.ResetPasswordDto;
import com.aabuilders.Dashboard.DTO.UserResponseDto;
import com.aabuilders.Dashboard.Entity.OtpPurpose;
import com.aabuilders.Dashboard.Entity.User;
import com.aabuilders.Dashboard.Repository.UserRepository;
import com.aabuilders.Dashboard.Util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final OtpService otpService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       OtpService otpService,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.otpService = otpService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public void requestRegistrationOtp(String email) {
        if (userRepository.findByEmail(email) != null) {
            throw new EmailAlreadyRegisteredException("This email is already registered");
        }
        otpService.generateAndSendOtp(email, OtpPurpose.REGISTRATION);
    }

    @Transactional
    public UserResponseDto registerUser(RegisterConfirmDto request) {
        if (userRepository.findByEmail(request.getEmail()) != null) {
            throw new EmailAlreadyRegisteredException("This email is already registered");
        }

        if (!otpService.verifyOtp(request.getEmail(), request.getOtp(), OtpPurpose.REGISTRATION)) {
            throw new InvalidOtpException("Invalid or expired OTP");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUserImageUrl(request.getUserImageUrl());
        user.setEmployeeId(request.getEmployeeId());
        user.setPosition(request.getPosition());
        user.setBranchId(request.getBranchId());
        user.setEmailVerified(true);
        user.setUserStatus("ACTIVE");

        return new UserResponseDto(userRepository.save(user));
    }

    public LoginResponseDto login(LoginRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail());
        if (user == null) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        ensureUserIsActive(user);

        if (Boolean.FALSE.equals(user.getEmailVerified())) {
            throw new EmailNotVerifiedException("Please verify your email before logging in");
        }

        if (!matchesPassword(request.getPassword(), user)) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        upgradeLegacyPasswordIfNeeded(user, request.getPassword());

        String token = jwtUtil.generateToken(user.getEmail());
        return new LoginResponseDto(token, user);
    }

    public void requestForgotPasswordOtp(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("No account found with this email");
        }
        ensureUserIsActive(user);
        otpService.generateAndSendOtp(email, OtpPurpose.PASSWORD_RESET);
    }

    @Transactional
    public void resetPassword(ResetPasswordDto request) {
        User user = userRepository.findByEmail(request.getEmail());
        if (user == null) {
            throw new UserNotFoundException("No account found with this email");
        }

        ensureUserIsActive(user);

        if (!otpService.verifyOtp(request.getEmail(), request.getOtp(), OtpPurpose.PASSWORD_RESET)) {
            throw new InvalidOtpException("Invalid or expired OTP");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    public void resendOtp(String email, OtpPurpose purpose) {
        if (purpose == OtpPurpose.REGISTRATION) {
            if (userRepository.findByEmail(email) != null) {
                throw new EmailAlreadyRegisteredException("This email is already registered");
            }
        } else if (purpose == OtpPurpose.PASSWORD_RESET) {
            User user = userRepository.findByEmail(email);
            if (user == null) {
                throw new UserNotFoundException("No account found with this email");
            }
            ensureUserIsActive(user);
        }
        otpService.generateAndSendOtp(email, purpose);
    }

    public UserResponseDto getCurrentUser(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        ensureUserIsActive(user);
        return new UserResponseDto(user);
    }

    public boolean validateAccessToken(String token) {
        if (!jwtUtil.isTokenValid(token)) {
            return false;
        }
        User user = userRepository.findByEmail(jwtUtil.extractUsername(token));
        return user != null && user.isActiveUser();
    }

    public static class EmailAlreadyRegisteredException extends RuntimeException {
        public EmailAlreadyRegisteredException(String message) {
            super(message);
        }
    }

    public static class InvalidOtpException extends RuntimeException {
        public InvalidOtpException(String message) {
            super(message);
        }
    }

    public static class InvalidCredentialsException extends RuntimeException {
        public InvalidCredentialsException(String message) {
            super(message);
        }
    }

    public static class EmailNotVerifiedException extends RuntimeException {
        public EmailNotVerifiedException(String message) {
            super(message);
        }
    }

    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    public static class AccountInactiveException extends RuntimeException {
        public AccountInactiveException(String message) {
            super(message);
        }
    }

    private void ensureUserIsActive(User user) {
        if (!user.isActiveUser()) {
            throw new AccountInactiveException("This user account is inactive. Please contact admin.");
        }
    }

    private boolean matchesPassword(String rawPassword, User user) {
        String storedPassword = user.getPassword();
        if (storedPassword == null) {
            return false;
        }
        if (isBcryptHash(storedPassword)) {
            return passwordEncoder.matches(rawPassword, storedPassword);
        }
        return storedPassword.equals(rawPassword);
    }

    private void upgradeLegacyPasswordIfNeeded(User user, String rawPassword) {
        if (!isBcryptHash(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(rawPassword));
            userRepository.save(user);
        }
    }

    private boolean isBcryptHash(String password) {
        return password.startsWith("$2a$") || password.startsWith("$2b$") || password.startsWith("$2y$");
    }
}
