package com.example.usersample.user.service.port;

import com.example.usersample.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(long id); // findById()
    Optional<User> findByUser(String email);
    Optional<List<User>> findUsers(); // JPARepository.findAll()
    User save(User user);
 }
