package com.ua.oauth;

import com.ua.oauth.models.Role;
import com.ua.oauth.models.User;
import com.ua.oauth.repository.RoleRepository;
import com.ua.oauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;

@SpringBootApplication
@EnableAutoConfiguration
public class SpringSecurityOauthAuthorizationCodeApplication extends SpringBootServletInitializer implements CommandLineRunner {

    private final ApplicationContext applicationContext;

    @Autowired
    public SpringSecurityOauthAuthorizationCodeApplication(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityOauthAuthorizationCodeApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        PasswordEncoder passwordEncoder = applicationContext.getBean(PasswordEncoder.class);
        UserRepository userRepository = applicationContext.getBean(UserRepository.class);
        RoleRepository roleRepository = applicationContext.getBean(RoleRepository.class);

        List<Role> roles = roleRepository.findAll();
        HashSet<Role> rolesSet = null;
        if (roles.isEmpty()) {
            Role roleAdmin = new Role();
            roleAdmin.setRole("ADMIN");
            roleAdmin = roleRepository.save(roleAdmin);

            Role roleUser = new Role();
            roleUser.setRole("USER");
            roleUser = roleRepository.save(roleUser);
            rolesSet = new HashSet<>();
            rolesSet.add(roleAdmin);
            rolesSet.add(roleUser);
        }

        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            User user = new User();
            user.setLogin("admin");
            user.setPassword(passwordEncoder.encode("admin"));
            if (rolesSet == null) {
                List<Role> roleList = roleRepository.findAll();
                rolesSet = new HashSet<>(roleList);
                user.setUserRole(rolesSet);
            } else {
                user.setUserRole(rolesSet);
            }
            userRepository.save(user);
        }

    }
}
