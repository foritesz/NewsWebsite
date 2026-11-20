package com.example.authentication.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationUserDetailsService implements UserDetailsService {

  private final PasswordEncoder passwordEncoder;

  @Value("${demo.user.email}")
  private String demoEmail;

  @Value("${demo.user.password}")
  private String demoPassword;

  @Value("${demo.user.role:ADMIN}")
  private String demoRole;

  public AuthenticationUserDetailsService(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if (demoEmail == null || !demoEmail.equalsIgnoreCase(username)) {
      throw new UsernameNotFoundException("User not found");
    }

    return org.springframework.security.core.userdetails.User
      .withUsername(demoEmail)
      .password(passwordEncoder.encode(demoPassword))
      .roles(demoRole)
      .build();
  }
}
