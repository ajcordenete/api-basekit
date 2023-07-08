package com.ajcordenete.basekit.service;

import com.ajcordenete.basekit.constant.Role;
import com.ajcordenete.basekit.constant.TokenType;
import com.ajcordenete.basekit.data.AuthResponse;
import com.ajcordenete.basekit.data.LoginRequest;
import com.ajcordenete.basekit.data.RegisterRequest;
import com.ajcordenete.basekit.data.UserTokenResponse;
import com.ajcordenete.basekit.entity.Token;
import com.ajcordenete.basekit.entity.User;
import com.ajcordenete.basekit.repository.TokenRepository;
import com.ajcordenete.basekit.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    public AuthResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        handleUserTokens(user, jwtToken);

        return createAuthResponse(HttpStatus.OK, user, jwtToken, refreshToken);
    }

    public AuthResponse registerUser(RegisterRequest registerRequest) {
        User newUser = new User();
        newUser.setFullName(registerRequest.getFullName());
        newUser.setFirstName(registerRequest.getFirstName());
        newUser.setLastName(registerRequest.getLastName());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setRole(Role.USER);
        newUser.setIsEnabled(true);
        newUser.setIsLocked(false);

        userRepository.save(newUser);

        String jwtToken = jwtService.generateToken(newUser);
        String refreshToken = jwtService.generateRefreshToken(newUser);
        handleUserTokens(newUser, jwtToken);

        return createAuthResponse(HttpStatus.CREATED, newUser, jwtToken, refreshToken);
    }

    private AuthResponse createAuthResponse(
            HttpStatus status,
            User user,
            String jwtToken,
            String refreshToken
    ) {
        return AuthResponse.builder()
                .success(true)
                .status(status.value())
                .message(status.getReasonPhrase())
                .data(
                        new UserTokenResponse(
                                user,
                                jwtToken,
                                refreshToken
                        )
                )
                .build();
    }

    private void handleUserTokens(User user, String jwtToken) {
        tokenService.revokeAllUserTokens(user);
        tokenService.saveUserToken(user, jwtToken);
    }
}
