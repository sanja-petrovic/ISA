package com.example.isa.service.interfaces;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import com.example.isa.model.RefreshToken;
import com.example.isa.repository.RefreshTokenRepository;
import com.example.isa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface RefreshTokenService {
    Optional<RefreshToken> findByToken(String token);
    RefreshToken createRefreshToken(UUID userId);
    RefreshToken verifyExpiration(RefreshToken token);
    void deleteByUserId(UUID userId);
}
