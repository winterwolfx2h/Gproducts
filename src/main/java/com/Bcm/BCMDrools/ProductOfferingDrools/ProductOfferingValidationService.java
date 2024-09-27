package com.Bcm.BCMDrools.ProductOfferingDrools;

import org.springframework.stereotype.Service;

@Service
public class ProductOfferingValidationService {

  /*
    private static final Logger logger = LoggerFactory.getLogger(ProductOfferingValidationService.class);
  private final KieSession kieSession;

  public ProductOfferingValidationService() {
      KieServices kieServices = null;
      KieContainer kieContainer = null;
      KieSession localSession = null;

      try {
          logger.info("Initializing KieServices...");
          kieServices = KieServices.Factory.get();
          if (kieServices == null) {
              throw new RuntimeException("KieServices is null. Ensure Drools dependencies are set.");
          }

          logger.info("Initializing KieContainer...");
          kieContainer = kieServices.getKieClasspathContainer();
          if (kieContainer == null) {
              throw new RuntimeException("KieContainer is null. Check Drools configuration.");
          }

          logger.info("Initializing KieSession...");
          localSession = kieContainer.newKieSession("rulesSession");
          if (localSession == null) {
              throw new RuntimeException("KieSession is null. Ensure KIE configuration and rule files.");
          }
      } catch (Exception e) {
          logger.error("Error initializing ProductOfferingValidationService: " + e.getMessage(), e);
          throw new RuntimeException("Error initializing ProductOfferingValidationService: " + e.getMessage(), e);
      }

      this.kieSession = localSession;
  }

  public void validateProductOffering(ProductOfferingDTO dto) {
      if (kieSession == null) {
          throw new RuntimeException("KieSession is not initialized. Cannot validate ProductOfferingDTO.");
      }

      try {
          kieSession.insert(dto);
          kieSession.fireAllRules();
      } catch (Exception e) {
          logger.error("Error validating ProductOfferingDTO: " + e.getMessage(), e);
          throw new RuntimeException("Error validating ProductOfferingDTO: " + e.getMessage(), e);
      }
  }
  */
}
