package com.aabuilders.Dashboard.Service;

import com.aabuilders.Dashboard.DTO.UserDto;
import com.aabuilders.Dashboard.Entity.User;
import com.aabuilders.Dashboard.Entity.UserRoles;
import com.aabuilders.Dashboard.Repository.UserRepository;
import com.aabuilders.Dashboard.Repository.UserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for user management operations.
 * Handles business logic for user authentication, registration, and CRUD operations.
 */
@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final UserRolesRepository userRolesRepository;
    
    private static final String ADMIN_USERNAME = "Admin";
    private static final String ADMIN_PASSWORD = "AAbuilder@2025";
    private static final String ADMIN_ONLY_MESSAGE = "Only admin can register new users";
    private static final String USER_EXISTS_MESSAGE = "User with this email already exists";
    private static final String USER_NOT_FOUND_MESSAGE = "User not found with id: ";

    @Autowired
    public UserService(UserRepository userRepository, UserRolesRepository userRolesRepository) {
        this.userRepository = userRepository;
        this.userRolesRepository = userRolesRepository;
    }

    /**
     * Authenticates a user with email and password.
     * 
     * @param email User's email address
     * @param password User's password
     * @return User object if authentication successful, null otherwise
     */
    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    /**
     * Registers a new user in the system.
     * Requires admin credentials for user registration.
     * 
     * @param userDto User data for registration
     * @throws Exception if admin credentials are invalid or user already exists
     */
    public void registerUser(UserDto userDto) throws Exception {
        validateAdminCredentials(userDto);
        validateUserDoesNotExist(userDto.getEmail());
        
        User newUser = createUserFromDto(userDto);
        userRepository.save(newUser);
    }

    /**
     * Updates an existing user's information.
     * 
     * @param userId The ID of the user to update
     * @param userDto Updated user data
     * @return Updated user object
     * @throws Exception if user not found
     */
    public User updateUser(Long userId, UserDto userDto) throws Exception {
        User existingUser = findUserByIdOrThrow(userId);
        
        updateUserFields(existingUser, userDto);
        updateUserRoles(existingUser, userDto);
        
        return userRepository.save(existingUser);
    }

    /**
     * Retrieves all users from the system.
     * 
     * @return List of all users
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    /**
     * Retrieves a user by their ID.
     * 
     * @param userId The ID of the user to retrieve
     * @return Optional containing the user if found
     */
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }
    
    /**
     * Deletes a user from the system.
     * 
     * @param userId The ID of the user to delete
     * @throws Exception if user not found
     */
    public void deleteUser(Long userId) throws Exception {
        if (!userRepository.existsById(userId)) {
            throw new Exception(USER_NOT_FOUND_MESSAGE + userId);
        }
        userRepository.deleteById(userId);
    }

    /**
     * Validates admin credentials for user registration.
     * 
     * @param userDto User data containing admin credentials
     * @throws Exception if admin credentials are invalid
     */
    private void validateAdminCredentials(UserDto userDto) throws Exception {
        if (!ADMIN_USERNAME.equals(userDto.getAdminUsername()) || 
            !ADMIN_PASSWORD.equals(userDto.getAdminPassword())) {
            throw new Exception(ADMIN_ONLY_MESSAGE);
        }
    }

    /**
     * Validates that a user with the given email does not already exist.
     * 
     * @param email Email to check
     * @throws Exception if user already exists
     */
    private void validateUserDoesNotExist(String email) throws Exception {
        if (userRepository.findByEmail(email) != null) {
            throw new Exception(USER_EXISTS_MESSAGE);
        }
    }

    /**
     * Creates a new User entity from UserDto.
     * 
     * @param userDto User data transfer object
     * @return New User entity
     */
    private User createUserFromDto(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setUserImage(userDto.getUserImage());
        user.setPosition(userDto.getPosition());
        return user;
    }

    /**
     * Finds a user by ID or throws an exception if not found.
     * 
     * @param userId The ID of the user to find
     * @return User entity
     * @throws Exception if user not found
     */
    private User findUserByIdOrThrow(Long userId) throws Exception {
        return userRepository.findById(userId)
                .orElseThrow(() -> new Exception(USER_NOT_FOUND_MESSAGE + userId));
    }

    /**
     * Updates user fields from UserDto.
     * 
     * @param user Existing user entity
     * @param userDto Updated user data
     */
    private void updateUserFields(User user, UserDto userDto) {
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setUserImage(userDto.getUserImage());
        user.setPosition(userDto.getPosition());
    }

    /**
     * Updates user roles from UserDto.
     * 
     * @param user Existing user entity
     * @param userDto User data containing roles
     */
    private void updateUserRoles(User user, UserDto userDto) {
        if (userDto.getUserRoles() != null) {
            List<UserRoles> rolesEntities = new ArrayList<>();
            
            for (UserRoles roleDto : userDto.getUserRoles()) {
                UserRoles existingRole = userRolesRepository.findByRoles(roleDto.getRoles());
                if (existingRole == null) {
                    existingRole = createNewRole(roleDto.getRoles());
                }
                rolesEntities.add(existingRole);
            }
            
            user.setUserRoles(rolesEntities);
        }
    }

    /**
     * Creates a new UserRoles entity.
     * 
     * @param roleName Name of the role
     * @return New UserRoles entity
     */
    private UserRoles createNewRole(String roleName) {
        UserRoles newRole = new UserRoles();
        newRole.setRoles(roleName);
        return userRolesRepository.save(newRole);
    }
}
