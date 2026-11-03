package com.example.authentication.controller;

import com.example.authentication.model.dto.LoginRequest;
import com.example.authentication.model.dto.LoginResponse;
import com.example.authentication.model.dto.RegisterRequest;
import com.example.authentication.service.AuthenticationService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

  public static final String ACCESS_TOKEN_COOKIE = "access_token";

  @Value("${app.cookie.secure:false}")
  private boolean cookieSecure;

  private final AuthenticationService authenticationService;

  public AuthenticationController(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
    var result = authenticationService.loginAndReturnJwt(request);

    ResponseCookie cookie = buildAccessTokenCookie(result.jwt(), 60 * 60);

    return ResponseEntity.ok()
      .header(HttpHeaders.SET_COOKIE, cookie.toString())
      .body(new LoginResponse(result.email()));
  }

  @PostMapping("/register")
  public ResponseEntity<LoginResponse> register(@RequestBody RegisterRequest request) {
    var result = authenticationService.registerAndReturnJwt(request);

    ResponseCookie cookie = buildAccessTokenCookie(result.jwt(), 60 * 60);

    return ResponseEntity.ok()
      .header(HttpHeaders.SET_COOKIE, cookie.toString())
      .body(new LoginResponse(result.email()));
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logout() {
    ResponseCookie cookie = buildAccessTokenCookie("", 0);

    return ResponseEntity.noContent()
      .header(HttpHeaders.SET_COOKIE, cookie.toString())
      .build();
  }

  private ResponseCookie buildAccessTokenCookie(String token, long maxAgeSeconds) {
    return ResponseCookie.from(ACCESS_TOKEN_COOKIE, token)
      .httpOnly(true)
      .secure(cookieSecure)
      .path("/")
      .sameSite("Lax")
      .maxAge(maxAgeSeconds)
      .build();
  }
}
