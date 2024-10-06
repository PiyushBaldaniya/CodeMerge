package com.example.Server.repository.impl;

import com.example.Server.model.RefreshToken;
import com.example.Server.model.User;
import com.example.Server.repository.intf.IRefreshTokenRepository;
import com.example.Server.repository.intf.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
@Service
public class RefreshTokenRepository {

    @Autowired
    private IRefreshTokenRepository refreshTokenRepository;
    @Autowired
    private IUserRepository userRepository;

    public RefreshTokenRepository(IRefreshTokenRepository refreshTokenRepository,IUserRepository userRepository)
    { this.refreshTokenRepository = refreshTokenRepository;
    this.userRepository = userRepository;}


}
