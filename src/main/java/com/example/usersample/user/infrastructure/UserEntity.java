package com.example.usersample.user.infrastructure;

import com.example.usersample.common.infrastructure.BaseEntity;
import com.example.usersample.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true)
    private String email;

    private String password;

    private String address;

    private String nickname;

    @Builder
    private UserEntity(Long id, String email, String password, String address, String nickname) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.address = address;
        this.nickname = nickname;
    }

    public static UserEntity from(User user) {
        return UserEntity.builder()
            .id(user.getId())
            .email(user.getEmail())
            .password(user.getPassword())
            .address(user.getAddress())
            .nickname(user.getNickname())
            .build();
    }

    public User toModel() {
        return User.builder()
            .id(id)
            .email(email)
            .password(password)
            .address(address)
            .nickname(nickname)
            .build();
    }
}
