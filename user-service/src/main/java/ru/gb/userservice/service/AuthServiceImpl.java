package ru.gb.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.gb.userservice.repo.UserRepository;
import ru.gb.userservice.security.CustomUserDetailsService;
import ru.gb.userservice.security.JwtTokenProvider;
import ru.gb.userservice.user.auth.JwtRequest;
import ru.gb.userservice.user.auth.JwtResponse;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl {
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtResponse login(final JwtRequest loginRequest) {
        var jwtResponse = new JwtResponse();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword())
        );
        UserDetails user = userDetailsService
                .loadUserByUsername(loginRequest.getEmail());
        var userInfo = userRepository
                .findByEmail(loginRequest.getEmail())
                .orElseThrow();
        if (user != null) {
            jwtResponse.setId(userInfo.getId());
            jwtResponse.setEmail(userInfo.getEmail());
            jwtResponse.setAccessToken(jwtTokenProvider.generateToken(user));
            return jwtResponse;
        }
        return jwtResponse;
    }
}
