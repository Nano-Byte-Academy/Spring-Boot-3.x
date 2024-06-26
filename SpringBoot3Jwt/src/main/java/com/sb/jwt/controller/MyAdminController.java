package com.sb.jwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/admin")
public class MyAdminController {

    @GetMapping("/helloAdmin")
    public ResponseEntity<String> helloAdmin(){
        return ResponseEntity.ok("Hello Admin!");
    }

}
