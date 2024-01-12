package com.example.usersample.user.service;

import com.example.usersample.common.exception.UserDataEmptyException;
import com.example.usersample.common.exception.UserNotFoundException;
import com.example.usersample.user.controller.port.UserService;
import com.example.usersample.user.domain.User;
import com.example.usersample.user.domain.UserCreate;
import com.example.usersample.user.domain.UserUpdate;
import com.example.usersample.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public User getUser(String email) {
        return userRepository.findByUser(email).orElseThrow(() -> new UserNotFoundException(email));
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findUsers().orElseThrow(() -> new UserDataEmptyException());
    }

    @Override
    public User save(UserCreate userCreate) {
        User user = User.create(userCreate);
        return userRepository.save(user);
    }

    @Override
    public User update(Long id, UserUpdate userUpdate) {
        User user = getById(id);
        user.update(userUpdate);
        return userRepository.save(user);
    }
}
