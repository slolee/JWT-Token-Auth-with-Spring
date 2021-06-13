package com.zn.iotproject.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String userId;

    @NotNull
    @Column(unique = true)
    private String refreshKey;

    public RefreshToken(String userId, String refreshKey) {
        this.userId = userId;
        this.refreshKey = refreshKey;
    }
}
