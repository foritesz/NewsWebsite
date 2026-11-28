package com.example.authentication.service;

import com.example.authentication.model.dto.LoginRequest;
import com.example.authentication.model.dto.RegisterRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;
  private final JwtService jwtService;

  public AuthenticationService(
    AuthenticationManager authenticationManager,
    UserDetailsService userDetailsService,
    JwtService jwtService
  ) {
    this.authenticationManager = authenticationManager;
    this.userDetailsService = userDetailsService;
    this.jwtService = jwtService;
  }

  public AuthenticationResult loginAndReturnJwt(LoginRequest request) {
    if (request.email() == null || request.password() == null) {
      throw new BadCredentialsException("Invalid credentials");
    }
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(request.email(), request.password())
    );
    UserDetails user = userDetailsService.loadUserByUsername(request.email());
    return new AuthenticationResult(jwtService.generateToken(user), user.getUsername());
  }

  public AuthenticationResult registerAndReturnJwt(RegisterRequest request) {
    throw new UnsupportedOperationException("Registration is not available for the demo account.");
  }

  public record AuthenticationResult(String jwt, String email) {
  }
}
