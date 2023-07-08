package com.ajcordenete.basekit.service;

import com.ajcordenete.basekit.constant.TokenType;
import com.ajcordenete.basekit.entity.Token;
import com.ajcordenete.basekit.entity.User;
import com.ajcordenete.basekit.repository.TokenRepository;
import com.ajcordenete.basekit.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private DateTimeUtil dateTimeUtil;

    @Value("${jwt.expiration_hour}")
    private long EXPIRATION;

    public void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revokeToken(false)
                .expirationDate(dateTimeUtil.getFutureDateFromNow(EXPIRATION, ChronoUnit.HOURS))
                .build();
        tokenRepository.save(token);
    }

    public void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevokeToken(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
