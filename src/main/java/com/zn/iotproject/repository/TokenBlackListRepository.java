package com.zn.iotproject.repository;

import com.zn.iotproject.domain.TokenBlackList;
import org.springframework.data.repository.CrudRepository;

public interface TokenBlackListRepository extends CrudRepository<TokenBlackList, Long> {
    Boolean existsByToken(String token);
}
