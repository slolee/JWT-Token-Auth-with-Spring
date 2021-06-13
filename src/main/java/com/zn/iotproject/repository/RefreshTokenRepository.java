package com.zn.iotproject.repository;

import com.zn.iotproject.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
    Boolean existsByUserIdAndRefreshKey(String userId, String refreshKey);
}
