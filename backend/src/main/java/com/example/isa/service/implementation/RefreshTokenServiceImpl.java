package com.example.isa.service.implementation;

import com.example.isa.exception.TokenRefreshException;
import com.example.isa.model.RefreshToken;
import com.example.isa.model.User;
import com.example.isa.repository.RefreshTokenRepository;
import com.example.isa.repository.UserRepository;
import com.example.isa.security.TokenHandler;
import com.example.isa.service.interfaces.RefreshTokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public RefreshTokenServiceImpl(RefreshTokenRepository repository, UserRepository userRepository) {
        this.refreshTokenRepository = repository;
        this.userRepository = userRepository;
    }


    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshToken createRefreshToken(UUID userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            RefreshToken refreshToken = RefreshToken.builder()
                    .user(user.get())
                    .expiryDate(new Date().getTime() + 2*24*60*60*1000)
                    .token(UUID.randomUUID().toString())
                    .build();
            refreshTokenRepository.save(refreshToken);
            return refreshToken;
        } else {
            return null;
        }
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(new Date().getTime()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        } else {
            return token;
        }
    }

    @Override
    @Transactional
    public void deleteByUserId(UUID userId) {
        Optional<User> user = userRepository.findById(userId);
        user.ifPresent(refreshTokenRepository::deleteByUser);
    }
}
