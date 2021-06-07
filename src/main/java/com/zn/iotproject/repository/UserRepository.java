package com.zn.iotproject.repository;

import com.zn.iotproject.domain.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<Users, Long> {
    Optional<Users> findByUserId(String userId);
}
