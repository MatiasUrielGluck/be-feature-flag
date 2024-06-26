package com.matiasgluck.befeatureflag.service;

import com.matiasgluck.befeatureflag.dto.AuthRequestDTO;
import com.matiasgluck.befeatureflag.entity.User;
import com.matiasgluck.befeatureflag.exception.UserAlreadyExistsException;
import com.matiasgluck.befeatureflag.repository.UserRepository;
import com.matiasgluck.befeatureflag.utils.UserUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String generateApiKey() {
        final int MAX_ATTEMPTS = 1000;
        int attempts = 0;
        String apiKey;
        do {
            apiKey = UserUtils.generateRandomKey(22);
            attempts++;
        } while (userRepository.existsByApiKey(apiKey) && attempts < MAX_ATTEMPTS);
        return apiKey;
    }

    public User signup(AuthRequestDTO input) {
        User user = User.builder()
                .fullName(input.getFullName())
                .email(input.getEmail())
                .password(passwordEncoder.encode(input.getPassword()))
                .apiKey(generateApiKey())
                .build();

        Optional<User> existingUser = userRepository.findByEmail(input.getEmail());
        if (existingUser.isPresent()) throw new UserAlreadyExistsException(user.getEmail());

        return userRepository.save(user);
    }

    public User authenticate(AuthRequestDTO input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}