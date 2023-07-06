package com.ajcordenete.basekit.service;

import com.ajcordenete.basekit.data.LoginRequest;
import com.ajcordenete.basekit.entity.User;
import com.ajcordenete.basekit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserRepository repository;

    private final AuthenticationManager authenticationManager;

    public User authenticate(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (DisabledException e) {
            log.info("Auth: Disabled user");
        } catch (LockedException e) {
            log.info("Auth: Locked user");
        } catch (BadCredentialsException e) {
            log.info("Auth: Incorrect username or password");
        }
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();

        return user;
    }
}
