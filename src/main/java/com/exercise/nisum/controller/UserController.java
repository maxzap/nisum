package com.exercise.nisum.controller;


import com.exercise.nisum.dto.AuthenticateRequest;
import com.exercise.nisum.dto.AuthenticateResponse;
import com.exercise.nisum.dto.user.CreateUserRequestDTO;
import com.exercise.nisum.dto.user.CreatedUserResponseDTO;
import com.exercise.nisum.model.UserModel;
import com.exercise.nisum.repository.UserRepository;
import com.exercise.nisum.service.UserService;
import com.exercise.nisum.util.jwt.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JwtToken jwtToken;

    @Autowired
    UserRepository userRepository;


    @PostMapping(value = "/create", produces = "application/json")
    public ResponseEntity<CreatedUserResponseDTO> createUser(HttpServletRequest request,
                                                             @Valid @RequestBody CreateUserRequestDTO userData) {
        var user = userService.registerUser(userData);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
    @PostMapping(value = "/authenticate", produces = "application/json")
    public ResponseEntity<AuthenticateResponse> authenticateUser(HttpServletRequest request,
                                                                 @Valid @RequestBody AuthenticateRequest userData) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userData.getUsername(), userData.getPassword()));
        } catch (BadCredentialsException exc) {
            throw new BadCredentialsException("Incorrect username or password", exc);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(userData.getUsername());
        final String jwt = jwtToken.generateToken(userDetails.getUsername());

        userRepository.updateTokenByEmail(jwt, userDetails.getUsername(), LocalDateTime.now());

        return ResponseEntity.ok(new AuthenticateResponse(jwt));

    }

    @GetMapping(value = "/find-all", produces = "application/json")
    List<UserModel> getAllUser(HttpServletRequest request) {
        return userService.findAllUsers();
    }

    @GetMapping(value = "/find/{id}", produces = "application/json")
    UserModel findUserById(HttpServletRequest request, @PathVariable(value = "id") String id) {
        return userService.getUserByUserId(id);
    }
    @DeleteMapping(value = "/delete/{id}", produces = "application/json")
    HttpEntity<Object> deleteUserById(HttpServletRequest request, @PathVariable(value = "id") String id) {
        var user = userService.deleteUserById(id);
        return (user == 1) ? ResponseEntity.status(HttpStatus.OK).body(user) : ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();

    }
}
