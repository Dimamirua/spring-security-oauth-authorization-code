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
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashSet;
import java.util.List;

@SpringBootApplication
@EnableAutoConfiguration
public class SpringSecurityOauthAuthorizationCodeApplication extends SpringBootServletInitializer implements CommandLineRunner {

    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityOauthAuthorizationCodeApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        PasswordEncoder passwordEncoder = applicationContext.getBean(PasswordEncoder.class);
        UserRepository userRepository = applicationContext.getBean(UserRepository.class);
        RoleRepository roleRepository = applicationContext.getBean(RoleRepository.class);
        DataSource dataSource = applicationContext.getBean(DataSource.class);

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

        try (Connection connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, new EncodedResource(new ClassPathResource("sql/tables.sql"), "utf8"),
                    false, false, "--", "/*commit*/", "______", "__________=");
        }

    }
}
