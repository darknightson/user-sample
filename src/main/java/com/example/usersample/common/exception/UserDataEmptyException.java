package com.example.usersample.common.exception;

public class UserDataEmptyException extends RuntimeException {
    public UserDataEmptyException() {
        super("사용자 리스트가 존재하지 않습니다.");
    }
}
