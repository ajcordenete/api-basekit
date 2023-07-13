package com.ajcordenete.basekit.controller;

import com.ajcordenete.basekit.data.UserResponse;
import com.ajcordenete.basekit.entity.User;
import com.ajcordenete.basekit.repository.UserRepository;
import com.ajcordenete.basekit.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable(name = "id") long id) {
        //throw new RuntimeException("This is a test");
        return ResponseEntity.ok(userService.getById(id));
    }
}
