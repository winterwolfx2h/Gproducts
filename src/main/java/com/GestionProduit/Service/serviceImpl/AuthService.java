package com.GestionProduit.Service.serviceImpl;

import com.GestionProduit.Payload.request.LoginRequest;
import com.GestionProduit.Payload.response.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {

  private final RestTemplate restTemplate;
  private final String JWT_BASE_URL = "http://localhost:4001/api/auth";

  @Autowired
  public AuthService(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.build();
  }

  public AuthResponse login(LoginRequest loginRequest) {
    return restTemplate.postForObject(JWT_BASE_URL + "/login", loginRequest, AuthResponse.class);
  }
}
