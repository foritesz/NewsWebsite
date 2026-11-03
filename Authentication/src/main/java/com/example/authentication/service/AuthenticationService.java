package com.example.authentication.service;

import com.example.authentication.model.dto.LoginRequest;
import com.example.authentication.model.dto.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

  public AuthenticationResult loginAndReturnJwt(LoginRequest request) {
    throw new UnsupportedOperationException("Authentication is not implemented yet.");
  }

  public AuthenticationResult registerAndReturnJwt(RegisterRequest request) {
    throw new UnsupportedOperationException("Registration is not implemented yet.");
  }

  public record AuthenticationResult(String jwt, String email) {
  }
}
