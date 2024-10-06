package com.example.Server.service.intf;

import com.example.Server.model.RefreshToken;
import com.example.Server.repository.impl.UserRepository;
import com.example.Server.repository.intf.IRefreshTokenRepository;
import com.example.Server.repository.intf.IUserRepository;

import java.util.Optional;

public interface IRefreshTokenService {
     Optional<RefreshToken> findByToken(String token);
     RefreshToken createRefreshToken(Long userId);
     RefreshToken verifyExpiration(RefreshToken token);
}
