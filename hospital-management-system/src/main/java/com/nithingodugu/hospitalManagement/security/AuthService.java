package com.nithingodugu.hospitalManagement.security;

import com.nithingodugu.hospitalManagement.dto.LoginRequestDto;
import com.nithingodugu.hospitalManagement.dto.LoginResponseDto;
import com.nithingodugu.hospitalManagement.dto.SignupRequestDto;
import com.nithingodugu.hospitalManagement.dto.SignupResponseDto;
import com.nithingodugu.hospitalManagement.entity.Patient;
import com.nithingodugu.hospitalManagement.entity.User;
import com.nithingodugu.hospitalManagement.entity.type.AuthProviderType;
import com.nithingodugu.hospitalManagement.entity.type.RoleType;
import com.nithingodugu.hospitalManagement.repository.PatientRepository;
import com.nithingodugu.hospitalManagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
        );

        User user = (User) authentication.getPrincipal();

        String token = authUtil.generateAccessToken(user);

        return new LoginResponseDto(token, user.getId());
    }

    public SignupResponseDto signup(SignupRequestDto singupRequestDto) {
        User user = singupInteral(singupRequestDto, AuthProviderType.EMAIL, null);
        return new SignupResponseDto(user.getId(), user.getUsername());

    }

    @Transactional
    public User singupInteral(SignupRequestDto singupRequestDto, AuthProviderType providerType, String providerId) {

        User user = userRepository.findByUsername(singupRequestDto.getEmail()).orElse(null);

        if(user != null){
            throw new IllegalArgumentException("User already existed");
        }

        user = User.builder()
                .username(singupRequestDto.getEmail())
//                .roles(Set.of(RoleType.PATIENT))
                .roles(singupRequestDto.getRoles())
                .providerType(providerType)
                .providerId(providerId)
                .build();


        if (providerType == AuthProviderType.EMAIL){
            user.setPassword(passwordEncoder.encode(singupRequestDto.getPassword()));
        }

        user = userRepository.save(user);

        Patient patient = Patient.builder()
                .name(singupRequestDto.getName())
                .email(singupRequestDto.getEmail())
                .user(user)
                .build();

        patientRepository.save(patient);

        return user;
    }

    public ResponseEntity<LoginResponseDto> handleOAuth2LoginRequest(OAuth2User oAuth2User, String registrationId) {

        AuthProviderType providerType = authUtil.getAuthProviderFromRegistrationId(registrationId);
        String providerId = authUtil.determineProviderIdFromOAuth2Token(oAuth2User, registrationId);

        User user = userRepository.findByProviderIdAndProviderType(providerId, providerType).orElse(null);

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        User emailUser = userRepository.findByUsername(email).orElse(null);

        if (user == null && emailUser == null){
            String username = authUtil.determineUsernameFromOAuth2User(oAuth2User, registrationId, providerId);
            user = singupInteral(new SignupRequestDto(username, null, name, Set.of(RoleType.PATIENT)),providerType, providerId);

        }else if(user != null){
            if (email != null && !email.isBlank() && !user.getUsername().equals(email)){
                user.setUsername(email);
                userRepository.save(user);
            }
        }else{
            throw new BadCredentialsException("email existed with other provider");
        }

        LoginResponseDto loginResponseDto = new LoginResponseDto(authUtil.generateAccessToken(user), user.getId());

        return ResponseEntity.ok(loginResponseDto);

    }
}
