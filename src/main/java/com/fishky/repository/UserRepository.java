package com.fishky.repository;

import com.fishky.model.UserEntity;

public interface UserRepository {
    Long save(UserEntity user);
    UserEntity readById(Long id);
    Long readIdByUsername(String s);
    UserEntity readByUsername(String s);
    UserEntity modify(UserEntity user);
    Boolean delete(Long id);
}
