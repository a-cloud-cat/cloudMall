package org.example.cloudmall.auth.service;

import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public boolean login(String username, String password) {
        return "admin".equals(username) && "123456".equals(password);
    }

}
