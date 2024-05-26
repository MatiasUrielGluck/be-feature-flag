package com.matiasgluck.befeatureflag.controller;

import com.matiasgluck.befeatureflag.dto.AccountInfoResponseDTO;
import com.matiasgluck.befeatureflag.entity.User;
import com.matiasgluck.befeatureflag.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/account")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<AccountInfoResponseDTO> getAccountInfo() {
        User currentUser = userService.getUserInstance();
        AccountInfoResponseDTO response =  AccountInfoResponseDTO.builder()
                .email(currentUser.getEmail())
                .fullName(currentUser.getFullName())
                .build();
        return ResponseEntity.ok(response);
    }
}
