package com.GestionProduit.Configuration;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class Extern implements InitializingBean {

  public static String SWAGGER_VERSION;
  public static String SWAGGER_TITLE;
  public static String SWAGGER_DESCRIPTION;
  public static String SWAGGER_LICENSE;
  public static String SWAGGER_LICENSE_URL;

  @Value("${swagger.version}")
  String GET_SWAGGER_VERSION;

  @Value("${swagger.title}")
  String GET_SWAGGER_TITLE;

  @Value("${swagger.description}")
  String GET_SWAGGER_DESCRIPTION;

  @Value("${swagger.license}")
  String GET_SWAGGER_LICENSE;

  @Value("${swagger.licenseurl}")
  String GET_SWAGGER_LICENSE_URL;

  @Bean
  public static String getAllConfig() {
    return "";
  }

  @Override
  public synchronized void afterPropertiesSet() {

    SWAGGER_VERSION = GET_SWAGGER_VERSION;
    SWAGGER_TITLE = GET_SWAGGER_TITLE;
    SWAGGER_DESCRIPTION = GET_SWAGGER_DESCRIPTION;
    SWAGGER_LICENSE = GET_SWAGGER_LICENSE;
    SWAGGER_LICENSE_URL = GET_SWAGGER_LICENSE_URL;
  }
}
