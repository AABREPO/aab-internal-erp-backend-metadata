package com.aabuilders.Dashboard.Controller;

import com.aabuilders.Dashboard.DTO.AuthMessageDto;
import com.aabuilders.Dashboard.DTO.EmailRequestDto;
import com.aabuilders.Dashboard.DTO.LoginRequestDto;
import com.aabuilders.Dashboard.DTO.LoginResponseDto;
import com.aabuilders.Dashboard.DTO.RegisterConfirmDto;
import com.aabuilders.Dashboard.DTO.ResendOtpDto;
import com.aabuilders.Dashboard.DTO.ResetPasswordDto;
import com.aabuilders.Dashboard.DTO.UserResponseDto;
import com.aabuilders.Dashboard.Entity.OtpPurpose;
import com.aabuilders.Dashboard.Service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register/request-otp")
    public ResponseEntity<?> requestRegistrationOtp(@RequestBody EmailRequestDto request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new AuthMessageDto("Public registration is disabled. Please contact your administrator."));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterConfirmDto request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new AuthMessageDto("Public registration is disabled. Please contact your administrator."));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto request) {
        try {
            LoginResponseDto response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (AuthService.InvalidCredentialsException | AuthService.EmailNotVerifiedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthMessageDto(e.getMessage()));
        } catch (AuthService.AccountInactiveException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new AuthMessageDto(e.getMessage()));
        }
    }

    @PostMapping("/forgot-password/request-otp")
    public ResponseEntity<?> requestForgotPasswordOtp(@RequestBody EmailRequestDto request) {
        try {
            authService.requestForgotPasswordOtp(request.getEmail());
            return ResponseEntity.ok(new AuthMessageDto("OTP sent to your email to reset password"));
        } catch (AuthService.UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AuthMessageDto(e.getMessage()));
        } catch (AuthService.AccountInactiveException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new AuthMessageDto(e.getMessage()));
        }
    }

    @PostMapping("/forgot-password/reset")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDto request) {
        try {
            authService.resetPassword(request);
            return ResponseEntity.ok(new AuthMessageDto("Password updated successfully"));
        } catch (AuthService.UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AuthMessageDto(e.getMessage()));
        } catch (AuthService.InvalidOtpException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AuthMessageDto(e.getMessage()));
        } catch (AuthService.AccountInactiveException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new AuthMessageDto(e.getMessage()));
        }
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<?> resendOtp(@RequestBody ResendOtpDto request) {
        try {
            OtpPurpose purpose = OtpPurpose.valueOf(request.getPurpose().toUpperCase());
            authService.resendOtp(request.getEmail(), purpose);
            return ResponseEntity.ok(new AuthMessageDto("OTP resent to your email"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthMessageDto("Invalid purpose. Use REGISTRATION or PASSWORD_RESET"));
        } catch (AuthService.EmailAlreadyRegisteredException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new AuthMessageDto(e.getMessage()));
        } catch (AuthService.UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AuthMessageDto(e.getMessage()));
        } catch (AuthService.AccountInactiveException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new AuthMessageDto(e.getMessage()));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthMessageDto("Invalid or missing JWT token"));
        }
        try {
            return ResponseEntity.ok(authService.getCurrentUser(userDetails.getUsername()));
        } catch (AuthService.UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AuthMessageDto(e.getMessage()));
        } catch (AuthService.AccountInactiveException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new AuthMessageDto(e.getMessage()));
        }
    }

    @PostMapping("/validate-token")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthMessageDto("Missing or invalid Authorization header"));
        }
        String token = authHeader.substring(7);
        if (authService.validateAccessToken(token)) {
            return ResponseEntity.ok(new AuthMessageDto("Token is valid"));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthMessageDto("Token is invalid or expired"));
    }
}
