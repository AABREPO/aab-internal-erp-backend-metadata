package com.aabuilders.Dashboard.Config;

import com.aabuilders.Dashboard.Entity.User;
import com.aabuilders.Dashboard.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;

@Component
public class SuperAdminBootstrap implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(SuperAdminBootstrap.class);

    private final UserRepository userRepository;
    private final String superAdminEmails;

    public SuperAdminBootstrap(UserRepository userRepository,
                               @Value("${app.super-admin.emails:}") String superAdminEmails) {
        this.userRepository = userRepository;
        this.superAdminEmails = superAdminEmails;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (!StringUtils.hasText(superAdminEmails)) {
            return;
        }

        Arrays.stream(superAdminEmails.split(","))
                .map(String::trim)
                .filter(StringUtils::hasText)
                .forEach(this::promoteToSuperAdmin);
    }

    private void promoteToSuperAdmin(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            log.warn("Configured super admin email not found: {}", email);
            return;
        }

        boolean changed = false;
        if (!user.isSuperAdmin()) {
            user.setSuperAdmin(true);
            changed = true;
        }
        if (!Boolean.TRUE.equals(user.getCanCreateUsers())) {
            user.setCanCreateUsers(true);
            changed = true;
        }

        if (changed) {
            userRepository.save(user);
            log.info("Promoted user to super admin: {}", email);
        }
    }
}
