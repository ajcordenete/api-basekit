package com.ajcordenete.basekit.service;

import com.ajcordenete.basekit.entity.Token;
import com.ajcordenete.basekit.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    public Token createToken(String tokenStr) {
        Token token = new Token();
        token.setToken(tokenStr);
        token.setExpired(false);
        token.setRevokeToken(false);
        token.setExpirationDate(LocalDateTime.now());

        return tokenRepository.save(token);
    }
}
