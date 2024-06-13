package com.Bcm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = {"com.Bcm"})
@EnableCaching
public class Bcm {

  public static void main(String[] args) {

    SpringApplication.run(Bcm.class, args);
  }
}
