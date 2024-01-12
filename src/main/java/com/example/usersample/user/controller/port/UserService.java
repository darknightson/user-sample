package com.example.usersample.user.controller.port;

import com.example.usersample.user.domain.User;
import com.example.usersample.user.domain.UserCreate;
import com.example.usersample.user.domain.UserUpdate;

import java.util.List;

public interface UserService {

    User getById(long id);
    User getUser(String email);
    List<User> getUsers();
    User save(UserCreate userCreate);

    User update(Long id, UserUpdate userUpdate);
}
