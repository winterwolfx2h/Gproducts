package com.GestionProduit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = {"com.GestionProduit"})
@EnableCaching
public class GestionProduit {

  public static void main(String[] args) {

    SpringApplication.run(GestionProduit.class, args);
  }
}
