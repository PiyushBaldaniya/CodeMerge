package com.example.Server.service.impl;

import com.example.Server.model.RefreshToken;
import com.example.Server.model.User;
import com.example.Server.repository.impl.RefreshTokenRepository;
import com.example.Server.repository.impl.UserRepository;
import com.example.Server.repository.intf.IRefreshTokenRepository;
import com.example.Server.repository.intf.IUserRepository;
import com.example.Server.service.intf.IRefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class RefreshTokenService implements IRefreshTokenService {
    @Autowired
    IRefreshTokenRepository refreshTokenRepository;

    @Autowired
    IUserRepository userRepository;

    public RefreshTokenService(IRefreshTokenRepository refreshTokenRepository, IUserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }


    public Optional<RefreshToken> findByToken(String token) {
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByToken(token);
        if(refreshToken.isPresent()){
            User user=refreshToken.get().getUser();
            Optional<User> completeUserData=userRepository.findById(user.getId());
            completeUserData.ifPresent(value -> refreshToken.get().setUser(value));
        }
        return refreshToken;
    }


    public RefreshToken createRefreshToken(Long userId) {
        return null;
    }


    public RefreshToken verifyExpiration(RefreshToken token) {
        return null;
    }


    public void deleteByUser(User user) {
        refreshTokenRepository.deleteByUser(user);
    }


    public void delete(RefreshToken t) {
        refreshTokenRepository.delete(t);
    }
}
