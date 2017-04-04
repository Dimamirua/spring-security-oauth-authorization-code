package com.ua.oauth.repository;

import com.ua.oauth.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created on 04.04.17.
 */
public interface UserRepository extends JpaRepository<User,Long> {

    User findByLogin(String login);

}
