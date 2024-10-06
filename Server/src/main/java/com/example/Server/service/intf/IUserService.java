package com.example.Server.service.intf;

import com.example.Server.dto.request.LoginRequest;
import com.example.Server.dto.request.RegisterUserRequest;
import com.example.Server.dto.request.TokenRefreshRequest;
import com.example.Server.model.User;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface IUserService {
     Optional<User> loadUserByUsername(String username);
     ResponseEntity<?> authenticateUser(LoginRequest loginRequest);
     ResponseEntity<?> refreshtoken(TokenRefreshRequest request);
     ResponseEntity<?> registerUser(RegisterUserRequest registerUserRequest);
}
