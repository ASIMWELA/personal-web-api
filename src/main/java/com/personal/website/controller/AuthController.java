package com.personal.website.controller;

import com.personal.website.entity.PasswordResetToken;
import com.personal.website.entity.UserEntity;
import com.personal.website.exception.EntityAlreadyExistException;
import com.personal.website.exception.EntityNotFoundException;
import com.personal.website.exception.TokenExpiredException;
import com.personal.website.payload.ApiResponse;
import com.personal.website.payload.LoginRequest;
import com.personal.website.payload.LoginResponse;
import com.personal.website.payload.SignUpRequest;
import com.personal.website.repository.PasswordResetTokenRepository;
import com.personal.website.repository.RoleRepository;
import com.personal.website.repository.UserRepository;
import com.personal.website.security.JwtTokenProvider;
import com.personal.website.service.UserDetailsImpl;
import com.personal.website.service.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider jwtUtils;

    @Value("${app.emailOrigin}")
    private String emailSender;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
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

    @SneakyThrows
    @RequestMapping(
            value="/forgot-password",
            method = RequestMethod.POST
    )
    public ResponseEntity.BodyBuilder forgotPassword(@RequestParam("email") String email, HttpServletRequest request)
    {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(
                ()->new EntityNotFoundException("No user found with email "+ email)
        );

        PasswordResetToken token = new PasswordResetToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setExpiryDate(60);
        passwordResetTokenRepository.save(token);

        String url = request.getScheme() + "://" +
                     request.getServerName() + ":" +
                     request.getServerPort()+
                     "/api/auth/reset-password?token="+token.getToken();

        Thread.sleep(10000);
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(emailSender);
        mail.setSubject("Password Reset");
        mail.setText("Click on the link below to complete the reset process\n\n"+url);
        javaMailSender.send(mail);

        //if(javaMailSender)

        return ResponseEntity.ok();
    }

    @RequestMapping(
            value="/reset-password",
            method = RequestMethod.POST
    )
    public ResponseEntity.BodyBuilder resetPassword(HttpServletRequest request) throws ServletRequestBindingException
    {
        String requestToken = ServletRequestUtils.getStringParameter(request,"token");
        String password = ServletRequestUtils.getStringParameter(request,"password");


        PasswordResetToken token =passwordResetTokenRepository.findByToken(requestToken).orElseThrow(
                ()->new EntityNotFoundException("Token: " + requestToken+" not found")
        );

        if(token.isExpired())
            throw new TokenExpiredException("Token expired");

        UserEntity user = token.getUser();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        passwordResetTokenRepository.delete(token);

        return ResponseEntity.ok();
    }
}