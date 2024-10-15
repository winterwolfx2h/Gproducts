package com.GestionProduit.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements ApplicationRunner {

  private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
  private final ResourceLoader resourceLoader;
  @PersistenceContext private EntityManager entityManager;

  public DataInitializer(ResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }

  @Override
  @Transactional
  public void run(ApplicationArguments args) {
    initialize();
  }

  private void initialize() {
    Resource resource = resourceLoader.getResource("classpath:DataScript.sql");

    try (BufferedReader reader =
        new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
      StringBuilder sqlScript = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) {
        sqlScript.append(line).append(System.lineSeparator());
      }

      clearPreviousData();
      executeSqlScript(sqlScript.toString());
      resetSequences();

    } catch (IOException e) {
      logger.error("Error reading SQL script file: ", e);
    }
  }

  private void clearPreviousData() {
    entityManager.createNativeQuery("DELETE FROM produit").executeUpdate();
    entityManager.createNativeQuery("DELETE FROM category").executeUpdate();
    entityManager.createNativeQuery("DELETE FROM tax").executeUpdate();
  }

  private void executeSqlScript(String sqlScript) {
    for (String sql : sqlScript.split(";")) {
      if (!sql.trim().isEmpty()) {
        try {
          entityManager.createNativeQuery(sql.trim()).executeUpdate();
        } catch (Exception e) {
          logger.error("Error executing query: {}", sql, e);
        }
      }
    }
  }

  private void resetSequences() {
    entityManager.createNativeQuery("ALTER TABLE produit AUTO_INCREMENT = 1").executeUpdate();
    entityManager.createNativeQuery("ALTER TABLE category AUTO_INCREMENT = 1").executeUpdate();
    entityManager.createNativeQuery("ALTER TABLE tax AUTO_INCREMENT = 1").executeUpdate();
  }
}
