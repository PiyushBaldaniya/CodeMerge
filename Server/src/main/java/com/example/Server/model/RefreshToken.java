package com.example.Server.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "refreshtoken")
public class RefreshToken {
    @Id
    private long id;
    @OneToOne
    @JoinColumn(name = "user_user_id")
    private User user;
    private String token;
    private Instant expiryDate;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
