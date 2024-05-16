package com.sb.jwt.service;

import com.sb.jwt.dto.RefreshTokenDto;
import com.sb.jwt.dto.SignInRequestDto;
import com.sb.jwt.dto.SignInResponseDto;
import com.sb.jwt.dto.SignUpRequestDto;
import com.sb.jwt.entity.MyRoles;
import com.sb.jwt.entity.MyUser;
import com.sb.jwt.repository.MyUserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class MyAuthenticationServiceImpl implements MyAuthenticationService {

    private final MyUserRepository myUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public MyAuthenticationServiceImpl(MyUserRepository myUserRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTService jwtService) {
        this.myUserRepository = myUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public MyUser signUp(SignUpRequestDto signUpRequestDto) {
        MyUser myUser = new MyUser();
        myUser.setFirstname(signUpRequestDto.getFirstName());
        myUser.setLastname(signUpRequestDto.getLastName());
        myUser.setEmail(signUpRequestDto.getEmail());
        myUser.setRole(MyRoles.USER);
        myUser.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));

        return myUserRepository.save(myUser);
    }

    public SignInResponseDto signIn(SignInRequestDto signInRequestDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequestDto.getEmail(), signInRequestDto.getPassword()
        ));

        var user = myUserRepository.findByEmail(signInRequestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var jwtToken = jwtService.generateToken(user);
        var jwtRefreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        SignInResponseDto signInResponseDto = new SignInResponseDto();
        signInResponseDto.setToken(jwtToken);
        signInResponseDto.setRefreshToken(jwtRefreshToken);

        return signInResponseDto;
    }

    public SignInResponseDto refreshToken(RefreshTokenDto refreshTokenDto) {
        String username = jwtService.extractUserName(refreshTokenDto.getToken());
        MyUser myUser = myUserRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid token"));
        if (jwtService.isTokenValid(refreshTokenDto.getToken(), myUser)) {
            var jwtToken = jwtService.generateToken(myUser);

            SignInResponseDto signInResponseDto = new SignInResponseDto();
            signInResponseDto.setToken(jwtToken);
            signInResponseDto.setRefreshToken(refreshTokenDto.getToken());

            return signInResponseDto;
        }
        return null;
    }

}
