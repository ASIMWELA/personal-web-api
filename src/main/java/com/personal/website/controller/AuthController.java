package com.personal.website.controller;

import com.personal.website.entity.UserEntity;
import com.personal.website.exception.EntityAlreadyExistException;
import com.personal.website.payload.ApiResponse;
import com.personal.website.payload.LoginRequest;
import com.personal.website.payload.LoginResponse;
import com.personal.website.payload.SignUpRequest;
import com.personal.website.repository.RoleRepository;
import com.personal.website.repository.UserRepository;
import com.personal.website.security.JwtTokenProvider;
import com.personal.website.service.UserDetailsImpl;
import com.personal.website.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    JwtTokenProvider jwtUtils;

    @Autowired
    private UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest)
    {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        return ResponseEntity.ok(new LoginResponse(jwt));
    }

    @RequestMapping(
            value="/signup-admin",
            method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
            )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse> registerAdmin(@Valid @RequestBody SignUpRequest entity) throws InterruptedException
    {
        userService.saveAdmin(entity);

        return new ResponseEntity<ApiResponse>(new ApiResponse(true, HttpStatus.CREATED, HttpStatus.CREATED.value(),"registered successfully"), HttpStatus.CREATED);

    }
    @RequestMapping(
            value="/signup-subscriber",
            method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse> registerSubscriber(@Valid @RequestBody SignUpRequest entity) throws InterruptedException
    {
        userService.saveSubscriber(entity);

        return new ResponseEntity<ApiResponse>(new ApiResponse(true, HttpStatus.CREATED, HttpStatus.CREATED.value(),"registered successfully"), HttpStatus.CREATED);

    }
}