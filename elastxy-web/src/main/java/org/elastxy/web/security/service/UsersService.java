package org.elastxy.web.security.service;

import java.util.List;

import org.elastxy.web.security.domain.User;


public interface UsersService {

	User findByUsername(String username);

    List<User> findAllUsers();
    
}
