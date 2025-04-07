package com.nkk.User.controller;

import com.nkk.User.config.Jwt.JwtUtil;
import com.nkk.User.entity.Users;
import com.nkk.User.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    private final JwtUtil jwtUtil;

    @Autowired
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping("/register")
    public ResponseEntity<Users> registerUser(@RequestBody Users users) {
        Users user= userService.registerUser(users);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        // Validate the username and password
        Users user = userService.findUserByUsername(username);
        if ( user!= null) {
            return jwtUtil.generateToken(user);
        }
        throw new IllegalArgumentException("Invalid credentials");
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok().body("Hello from User Service");
    }
}
