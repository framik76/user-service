package com.yanchware.userservice.service;

import com.yanchware.userservice.entity.User;
import com.yanchware.userservice.model.UserModelRequest;
import com.yanchware.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser (UserModelRequest userModelRequest) throws Exception {
        var optionalUser = userRepository.findByEmail(userModelRequest.getEmail());
        if (optionalUser.isEmpty()) {
            var user  = new User();
            user.setEmail(userModelRequest.getEmail());
            user.setFirstName(userModelRequest.getFirstName());
            user.setLastName(userModelRequest.getLastName());
            user.setApiKey(UUID.randomUUID().toString());
            user = userRepository.save(user);
            return user;
        } else {
            throw new Exception("user already present");
        }
    }

    public List<User> getAllUsers() {
        var users = new ArrayList<User>();
        userRepository.findAll().forEach(user -> users.add(user));
        return users;
    }

}
