package com.example.authentication.service;

import com.example.authentication.model.dto.UserProfile;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  public UserProfile getProfileByEmail(String email) {
    throw new UnsupportedOperationException("User profile lookup is not implemented yet.");
  }

  public void deleteUser(Long id) {
    throw new UnsupportedOperationException("User deletion is not implemented yet.");
  }
}
