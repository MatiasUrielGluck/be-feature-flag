package com.matiasgluck.befeatureflag.controller;

import com.matiasgluck.befeatureflag.dto.AuthRequestDTO;
import com.matiasgluck.befeatureflag.dto.AuthResponseDTO;
import com.matiasgluck.befeatureflag.entity.User;
import com.matiasgluck.befeatureflag.service.AuthenticationService;
import com.matiasgluck.befeatureflag.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody AuthRequestDTO authRequestDTO) {
        authenticationService.signup(authRequestDTO);

        return authenticate(AuthRequestDTO.builder()
                .email(authRequestDTO.getEmail())
                .password(authRequestDTO.getPassword())
                .build());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> authenticate(@RequestBody AuthRequestDTO authRequestDTO) {
        User authenticatedUser = authenticationService.authenticate(authRequestDTO);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        AuthResponseDTO authResponseDTO = AuthResponseDTO.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .build();

        return ResponseEntity.ok(authResponseDTO);
    }
}