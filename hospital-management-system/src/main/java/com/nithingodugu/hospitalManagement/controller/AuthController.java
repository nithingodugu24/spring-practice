package com.nithingodugu.hospitalManagement.controller;

import com.nithingodugu.hospitalManagement.dto.LoginRequestDto;
import com.nithingodugu.hospitalManagement.dto.LoginResponseDto;
import com.nithingodugu.hospitalManagement.dto.SignupResponseDto;
import com.nithingodugu.hospitalManagement.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody LoginRequestDto singupRequestDto){
        return ResponseEntity.ok(authService.signup(singupRequestDto));
    }

}
