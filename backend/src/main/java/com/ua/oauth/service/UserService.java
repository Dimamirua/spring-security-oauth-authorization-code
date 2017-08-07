package com.ua.oauth.service;

import com.ua.oauth.models.User;

/**
 * Created on 04.04.17.
 */
public interface UserService {
    User getUserByLogin(String login);
}
