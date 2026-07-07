package com.aabuilders.Dashboard.Service;

import com.aabuilders.Dashboard.DTO.RegisterConfirmDto;
import com.aabuilders.Dashboard.DTO.UserPermissionDto;
import com.aabuilders.Dashboard.DTO.UserResponseDto;
import com.aabuilders.Dashboard.Entity.User;
import com.aabuilders.Dashboard.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserPermissionService {

    private final UserRepository userRepository;
    private final AuthService authService;

    public UserPermissionService(UserRepository userRepository, AuthService authService) {
        this.userRepository = userRepository;
        this.authService = authService;
    }

    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new AuthService.UserNotFoundException("User not found");
        }
        return user;
    }

    public void ensureCanCreateUsers(User user) {
        if (!user.canCreateUsers()) {
            throw new ForbiddenException("You do not have permission to create users");
        }
    }

    public void ensureSuperAdmin(User user) {
        if (!user.isSuperAdmin()) {
            throw new ForbiddenException("Only super admin can manage user permissions");
        }
    }

    public void requestCreateUserOtp(String actorEmail, String newUserEmail) {
        User actor = getUserByEmail(actorEmail);
        ensureCanCreateUsers(actor);
        authService.requestRegistrationOtp(newUserEmail);
    }

    @Transactional
    public UserResponseDto createUser(String actorEmail, RegisterConfirmDto request) {
        User actor = getUserByEmail(actorEmail);
        ensureCanCreateUsers(actor);
        return authService.registerUser(request);
    }

    @Transactional
    public UserResponseDto updateUserPermissions(String actorEmail, Long targetUserId, UserPermissionDto request) {
        User actor = getUserByEmail(actorEmail);
        ensureSuperAdmin(actor);

        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new AuthService.UserNotFoundException("User not found with id: " + targetUserId));

        boolean nextSuperAdmin = request.getSuperAdmin() != null
                ? request.getSuperAdmin()
                : targetUser.isSuperAdmin();
        boolean nextCanCreateUsers = request.getCanCreateUsers() != null
                ? request.getCanCreateUsers()
                : Boolean.TRUE.equals(targetUser.getCanCreateUsers());

        if (targetUser.isSuperAdmin() && !nextSuperAdmin && userRepository.countBySuperAdminTrue() <= 1) {
            throw new ForbiddenException("At least one super admin is required");
        }

        if (!actor.getId().equals(targetUser.getId()) || nextSuperAdmin) {
            targetUser.setSuperAdmin(nextSuperAdmin);
        }

        if (!nextSuperAdmin) {
            targetUser.setCanCreateUsers(nextCanCreateUsers);
        } else {
            targetUser.setCanCreateUsers(true);
        }

        return new UserResponseDto(userRepository.save(targetUser));
    }

    public static class ForbiddenException extends RuntimeException {
        public ForbiddenException(String message) {
            super(message);
        }
    }
}
