package com.aabuilders.Dashboard.Service;

import com.aabuilders.Dashboard.DTO.UserDto;
import com.aabuilders.Dashboard.DTO.UserResponseDto;
import com.aabuilders.Dashboard.Entity.User;
import com.aabuilders.Dashboard.Entity.UserRoles;
import com.aabuilders.Dashboard.Repository.UserRepository;
import com.aabuilders.Dashboard.Repository.UserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRolesRepository userRolesRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponseDto loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null || !matchesPassword(password, user)) {
            return null;
        }
        if (!user.isActiveUser()) {
            return null;
        }
        return new UserResponseDto(user);
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

    private boolean isBcryptHash(String password) {
        return password.startsWith("$2a$") || password.startsWith("$2b$") || password.startsWith("$2y$");
    }

    private String encodePasswordIfNeeded(String rawPassword) {
        if (!StringUtils.hasText(rawPassword)) {
            return null;
        }
        if (isBcryptHash(rawPassword)) {
            return rawPassword;
        }
        return passwordEncoder.encode(rawPassword);
    }

    public UserResponseDto registerUser(UserDto userDto) throws Exception {
        if (userRepository.findByEmail(userDto.getEmail()) != null) {
            throw new Exception("This email is already registered");
        }
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setUserImageUrl(userDto.getUserImageUrl());
        user.setPosition(userDto.getPosition());
        user.setBranchId(userDto.getBranchId());
        user.setEmailVerified(true);
        user.setUserStatus("ACTIVE");
        return new UserResponseDto(userRepository.save(user));
    }

    public UserResponseDto updateUser(Long userId, UserDto userDto) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found with id: " + userId));
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        if (StringUtils.hasText(userDto.getPassword())) {
            user.setPassword(encodePasswordIfNeeded(userDto.getPassword()));
        }
        user.setUserImageUrl(userDto.getUserImageUrl());
        user.setPosition(userDto.getPosition());
        user.setBranchId(userDto.getBranchId());
        if (StringUtils.hasText(userDto.getUserStatus())) {
            user.setUserStatus(userDto.getUserStatus().trim().toUpperCase());
        }
        if (userDto.getUserRoles() != null) {
            List<UserRoles> rolesEntities = new ArrayList<>();
            for (UserRoles roleDto : userDto.getUserRoles()) {
                UserRoles existingRole = userRolesRepository.findByRoles(roleDto.getRoles());
                if (existingRole == null) {
                    existingRole = new UserRoles();
                    existingRole.setRoles(roleDto.getRoles());
                    userRolesRepository.save(existingRole);
                }
                rolesEntities.add(existingRole);
            }
            user.setUserRoles(rolesEntities);
        }
        return new UserResponseDto(userRepository.save(user));
    }

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    public UserResponseDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserResponseDto::new)
                .orElse(null);
    }

    public List<String> getAllUsernames() {
        return userRepository.findAll()
                .stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
    }

    public void deleteUser(Long userId) throws Exception {
        if (!userRepository.existsById(userId)) {
            throw new Exception("User not found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }
}
