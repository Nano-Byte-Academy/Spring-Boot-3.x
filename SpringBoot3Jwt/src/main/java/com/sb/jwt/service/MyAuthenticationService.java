package com.sb.jwt.service;

import com.sb.jwt.dto.RefreshTokenDto;
import com.sb.jwt.dto.SignInRequestDto;
import com.sb.jwt.dto.SignInResponseDto;
import com.sb.jwt.dto.SignUpRequestDto;
import com.sb.jwt.entity.MyUser;

public interface MyAuthenticationService {

    MyUser signUp(SignUpRequestDto signUpRequestDto);

    SignInResponseDto signIn(SignInRequestDto signInRequestDto);

    SignInResponseDto refreshToken(RefreshTokenDto refreshTokenDto);

}
