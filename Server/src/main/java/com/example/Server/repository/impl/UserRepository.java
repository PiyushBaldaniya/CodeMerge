package com.example.Server.repository.impl;

import com.example.Server.model.User;
import com.example.Server.repository.intf.IUserRepository;
import com.example.Server.service.intf.IUserService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class UserRepository {

    IUserRepository userRepository;
    public UserRepository(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
