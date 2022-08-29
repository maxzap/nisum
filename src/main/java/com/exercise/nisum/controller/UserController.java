package com.exercise.nisum.controller;


import com.exercise.nisum.dto.user.CreateUserRequestDTO;
import com.exercise.nisum.dto.user.CreatedUserResponseDTO;
import com.exercise.nisum.model.UserModel;
import com.exercise.nisum.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/app")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/user/create", produces = "application/json")
    public ResponseEntity<CreatedUserResponseDTO> createUser(HttpServletRequest request,
                                                             @Valid @RequestBody CreateUserRequestDTO userData) {
        var user = userService.registerUser(userData);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping(value = "/users", produces = "application/json")
    List<UserModel> getAllUser(HttpServletRequest request) {
        return userService.findAllUsers();
    }

    @GetMapping(value = "/user/{id}", produces = "application/json")
    UserModel findUserById(HttpServletRequest request, @PathVariable(value = "id") String id) {
        return userService.getUserByUserId(id);
    }
}
