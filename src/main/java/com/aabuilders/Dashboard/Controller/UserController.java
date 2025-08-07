package com.aabuilders.Dashboard.Controller;

import com.aabuilders.Dashboard.DTO.LoginResponseDto;
import com.aabuilders.Dashboard.DTO.UserDto;
import com.aabuilders.Dashboard.DTO.UserResponseDto;
import com.aabuilders.Dashboard.Entity.User;
import com.aabuilders.Dashboard.Service.UserService;
import com.aabuilders.Dashboard.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller for user management operations.
 * Handles user authentication, registration, and CRUD operations.
 */
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Authenticates a user with email and password.
     * Generates JWT token upon successful authentication.
     * 
     * @param userDto Contains email and password for authentication
     * @return ResponseEntity with JWT token and user data if successful, BAD_REQUEST if failed
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody UserDto userDto) {
        try {
            User authenticatedUser = userService.loginUser(userDto.getEmail(), userDto.getPassword());
            
            if (authenticatedUser != null) {
                // Generate JWT token
                String token = jwtUtil.generateToken(authenticatedUser.getEmail());
                
                // Create response DTO with token and user info (excluding password)
                LoginResponseDto loginResponse = new LoginResponseDto(token, authenticatedUser);
                
                return ResponseEntity.ok(loginResponse);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid credentials");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error: " + e.getMessage());
        }
    }

    /**
     * Registers a new user in the system.
     * Requires admin credentials for user registration.
     * 
     * @param userDto User data for registration
     * @return ResponseEntity with success message or error details
     */
    @PostMapping("/sign-in")
    public ResponseEntity<String> registerNewUser(@RequestBody UserDto userDto) {
        try {
            userService.registerUser(userDto);
            return ResponseEntity.ok("User registered successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to register user: " + e.getMessage());
        }
    }

    /**
     * Retrieves all users from the system.
     * Returns user information without passwords.
     * 
     * @return ResponseEntity with list of all users (passwords excluded)
     */
    @GetMapping("/user/all")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            List<UserResponseDto> userResponses = users.stream()
                    .map(UserResponseDto::new)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(userResponses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Retrieves a specific user by their ID.
     * Returns user information without password.
     * 
     * @param userId The ID of the user to retrieve
     * @return ResponseEntity with user data if found, NOT_FOUND if not found
     */
    @GetMapping("/user/id/{userId}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long userId) {
        try {
            return userService.getUserById(userId)
                    .map(UserResponseDto::new)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Updates an existing user's information.
     * Returns updated user information without password.
     * 
     * @param userId The ID of the user to update
     * @param userDto Updated user data
     * @return ResponseEntity with updated user data or error message
     */
    @PutMapping("/user/edit/{userId}")
    public ResponseEntity<?> updateExistingUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        try {
            User updatedUser = userService.updateUser(userId, userDto);
            UserResponseDto userResponse = new UserResponseDto(updatedUser);
            return ResponseEntity.ok(userResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Update failed: " + e.getMessage());
        }
    }

    /**
     * Deletes a user from the system.
     * 
     * @param userId The ID of the user to delete
     * @return ResponseEntity with success message or error details
     */
    @DeleteMapping("/user/delete/{userId}")
    public ResponseEntity<String> deleteExistingUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok("User deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Delete failed: " + e.getMessage());
        }
    }
}
