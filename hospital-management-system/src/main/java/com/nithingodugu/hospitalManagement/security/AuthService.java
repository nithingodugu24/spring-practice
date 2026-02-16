package com.nithingodugu.hospitalManagement.security;

import com.nithingodugu.hospitalManagement.dto.LoginRequestDto;
import com.nithingodugu.hospitalManagement.dto.LoginResponseDto;
import com.nithingodugu.hospitalManagement.dto.SignupResponseDto;
import com.nithingodugu.hospitalManagement.entity.User;
import com.nithingodugu.hospitalManagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final AuthUtil authUtil;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
        );

        User user = (User) authentication.getPrincipal();

        String token = authUtil.generateAccessToken(user);

        return new LoginResponseDto(token, user.getId());
    }

    public SignupResponseDto signup(LoginRequestDto singupRequestDto) {
        User user = userRepository.findByUsername(singupRequestDto.getUsername()).orElse(null);

        if(user != null){
            throw new IllegalArgumentException("User already existed");
        }

        user = userRepository.save(User.builder()
                .username(singupRequestDto.getUsername())
                .password(passwordEncoder.encode(singupRequestDto.getPassword()))
                .build()
        );

        return new SignupResponseDto(user.getId(), user.getUsername());

    }
}
