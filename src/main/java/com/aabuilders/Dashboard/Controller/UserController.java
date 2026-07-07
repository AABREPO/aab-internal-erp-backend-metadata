package com.aabuilders.Dashboard.Controller;

import com.aabuilders.Dashboard.DTO.AuthMessageDto;
import com.aabuilders.Dashboard.DTO.EmailRequestDto;
import com.aabuilders.Dashboard.DTO.LoginRequestDto;
import com.aabuilders.Dashboard.DTO.RegisterConfirmDto;
import com.aabuilders.Dashboard.DTO.UserDto;
import com.aabuilders.Dashboard.DTO.UserPermissionDto;
import com.aabuilders.Dashboard.DTO.UserResponseDto;
import com.aabuilders.Dashboard.DTO.UsernameOnlyDto;
import com.aabuilders.Dashboard.Service.AuthService;
import com.aabuilders.Dashboard.Service.UserPermissionService;
import com.aabuilders.Dashboard.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserPermissionService userPermissionService;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDto loginRequest) {
        try {
            return ResponseEntity.ok(authService.login(loginRequest));
        } catch (AuthService.InvalidCredentialsException | AuthService.EmailNotVerifiedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthMessageDto(e.getMessage()));
        }
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new AuthMessageDto("Public registration is disabled. Please contact your administrator."));
    }

    @PostMapping("/user/create/request-otp")
    public ResponseEntity<?> requestCreateUserOtp(@AuthenticationPrincipal UserDetails userDetails,
                                                  @RequestBody EmailRequestDto request) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthMessageDto("Invalid or missing JWT token"));
        }
        try {
            userPermissionService.requestCreateUserOtp(userDetails.getUsername(), request.getEmail());
            return ResponseEntity.ok(new AuthMessageDto("OTP sent to the new user's email"));
        } catch (UserPermissionService.ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new AuthMessageDto(e.getMessage()));
        } catch (AuthService.EmailAlreadyRegisteredException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new AuthMessageDto(e.getMessage()));
        }
    }

    @PostMapping("/user/create")
    public ResponseEntity<?> createUser(@AuthenticationPrincipal UserDetails userDetails,
                                          @RequestBody RegisterConfirmDto request) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthMessageDto("Invalid or missing JWT token"));
        }
        try {
            UserResponseDto user = userPermissionService.createUser(userDetails.getUsername(), request);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (UserPermissionService.ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new AuthMessageDto(e.getMessage()));
        } catch (AuthService.EmailAlreadyRegisteredException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new AuthMessageDto(e.getMessage()));
        } catch (AuthService.InvalidOtpException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AuthMessageDto(e.getMessage()));
        }
    }

    @PutMapping("/user/{id}/permissions")
    public ResponseEntity<?> updateUserPermissions(@AuthenticationPrincipal UserDetails userDetails,
                                                   @PathVariable Long id,
                                                   @RequestBody UserPermissionDto request) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthMessageDto("Invalid or missing JWT token"));
        }
        try {
            return ResponseEntity.ok(userPermissionService.updateUserPermissions(userDetails.getUsername(), id, request));
        } catch (UserPermissionService.ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new AuthMessageDto(e.getMessage()));
        } catch (AuthService.UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AuthMessageDto(e.getMessage()));
        }
    }

    @GetMapping("/user/all")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/user/usernames")
    public ResponseEntity<List<UsernameOnlyDto>> getAllUsernames() {
        List<UsernameOnlyDto> usernames = userService.getAllUsernames()
                .stream()
                .map(UsernameOnlyDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(usernames);
    }

    @GetMapping("/user/id/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        UserResponseDto user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/user/edit/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        try {
            UserResponseDto updatedUser = userService.updateUser(id, userDto);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Update failed: " + e.getMessage());
        }
    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Delete failed: " + e.getMessage());
        }
    }
}
