package com.ua.oauth.service.impl;

import com.ua.oauth.models.User;
import com.ua.oauth.repository.UserRepository;
import com.ua.oauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created on 04.04.17.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }


}
