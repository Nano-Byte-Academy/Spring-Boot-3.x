package com.sb.jwt.controller;

import com.sb.jwt.dto.RefreshTokenDto;
import com.sb.jwt.dto.SignInRequestDto;
import com.sb.jwt.dto.SignInResponseDto;
import com.sb.jwt.dto.SignUpRequestDto;
import com.sb.jwt.entity.MyUser;
import com.sb.jwt.service.MyAuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class MyAuthController {

    private final MyAuthenticationService myAuthSvc;

    public MyAuthController(MyAuthenticationService myAuthSvc) {
        this.myAuthSvc = myAuthSvc;
    }

    @GetMapping("/unauthenticatedEndPoint")
    public String unauthenticatedEndPoint() {
        return "My unauthenticated response!";
    }

    @PostMapping("/signup")
    public ResponseEntity<MyUser> signup(@RequestBody SignUpRequestDto signUpRequestDto) {
        return ResponseEntity.ok(myAuthSvc.signUp(signUpRequestDto));
    }

    @PostMapping("/signin")
    public ResponseEntity<SignInResponseDto> signin(@RequestBody SignInRequestDto signInRequestDto) {
        return ResponseEntity.ok(myAuthSvc.signIn(signInRequestDto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<SignInResponseDto> refresh(@RequestBody RefreshTokenDto refreshTokenDto) {
        return ResponseEntity.ok(myAuthSvc.refreshToken(refreshTokenDto));
    }


}
