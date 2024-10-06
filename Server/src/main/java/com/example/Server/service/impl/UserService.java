package com.example.Server.service.impl;

import com.example.Server.configurations.jwt.JwtUtil;
import com.example.Server.dto.request.LoginRequest;
import com.example.Server.dto.request.RegisterUserRequest;
import com.example.Server.dto.request.TokenRefreshRequest;
import com.example.Server.dto.response.JwtResponse;
import com.example.Server.dto.response.MessageResponse;
import com.example.Server.dto.response.TokenRefreshResponse;
import com.example.Server.model.RefreshToken;
import com.example.Server.model.User;
import com.example.Server.repository.intf.IUserRepository;
import com.example.Server.service.intf.IRefreshTokenService;
import com.example.Server.service.intf.IUserService;
import com.example.Server.utils.mappers.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService, UserDetails {

    IUserRepository userRepository;
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtil jwtUtil;
    IRefreshTokenService refreshTokenService;
    PasswordEncoder encoder;
    @Autowired
    EmailSender emailSender;
    public UserService(AuthenticationManager authenticationManager, IUserRepository userRepository,
                       JwtUtil jwtUtil, IRefreshTokenService refreshTokenService, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.refreshTokenService = refreshTokenService;
        this.encoder = encoder;
    }

    @Override
    public Optional<User> loadUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {

        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

            User user_model;
            try{user_model = userRepository.findByUsername(user.getUsername()).get();}
            catch(Exception e){user_model = new User();}

            String jwt = jwtUtil.generateToken(loginRequest.getUsername());
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(user_model.getId());
            return ResponseEntity.ok(new JwtResponse(jwt));

        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    public ResponseEntity<?> refreshtoken(TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken).map(refreshTokenService::verifyExpiration).map(RefreshToken::getUser).map(user -> {
            String token = jwtUtil.generateToken(user.getUsername());
            return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
        }).orElseThrow(() -> new ExpressionException(requestRefreshToken, "Refresh token is not in database!"));
    }

    public ResponseEntity<?> registerUser(RegisterUserRequest registerUserRequest) {
        try{
            if (userRepository.existsByUsername(registerUserRequest.getUsername())) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
            }

            if (userRepository.existsByEmail(registerUserRequest.getEmail())) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
            }

            User user = new User(registerUserRequest.getUsername(), registerUserRequest.getEmail(), encoder.encode(registerUserRequest.getPassword()));

            userRepository.save(user);

            boolean sendEmail = emailSender.sendEmail(registerUserRequest.getEmail(), "Welcome to GitColab", "Hello " + user.getUsername() + ",\n\nWelcome to GitColab! Happy integrating things! \n\nTeam GitColab!");

            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));

        }
        catch (Exception e)
        {
            return ResponseEntity.ok(new MessageResponse(e.getMessage()));
        }

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }
}
