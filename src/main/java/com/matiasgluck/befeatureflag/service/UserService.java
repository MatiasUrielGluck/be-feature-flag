package com.matiasgluck.befeatureflag.service;

import com.matiasgluck.befeatureflag.entity.User;
import com.matiasgluck.befeatureflag.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    public UserService(UserRepository userRepository, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
    }

    public User getUserInstance() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    public String renewApiKey() {
        String newKey = authenticationService.generateApiKey();
        User user = getUserInstance();
        user.setApiKey(newKey);
        userRepository.save(user);
        return newKey;
    }
}
