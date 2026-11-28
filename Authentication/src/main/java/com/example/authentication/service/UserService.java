package com.example.authentication.service;

import com.example.authentication.model.dto.UserProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Value("${demo.user.email}")
  private String demoEmail;

  @Value("${demo.user.role:ADMIN}")
  private String demoRole;

  public UserProfile getProfileByEmail(String email) {
    if (demoEmail == null || !demoEmail.equalsIgnoreCase(email)) {
      throw new UsernameNotFoundException("User not found");
    }
    return new UserProfile(1L, demoEmail, demoRole);
  }

  public void deleteUser(Long id) {
    throw new UnsupportedOperationException("User deletion is not available for the demo account.");
  }
}
