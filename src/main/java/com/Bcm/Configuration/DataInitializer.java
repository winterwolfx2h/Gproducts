package com.Bcm.Configuration;

import com.Bcm.Repository.ProductOfferingRepo.ProductPriceGroupRepository;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.ChannelRepository;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.EntityRepository;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class DataInitializer implements ApplicationRunner {

  final ChannelRepository channelRepository;
  final EntityManager entityManager;
  final EntityRepository entityRepository;
  final ProductPriceGroupRepository productPriceGroupRepository;

  @Override
  @Transactional
  public void run(ApplicationArguments args) {
    initialize();
  }

  private void initialize() {
    if (!channelRepository.existsById(0)) {
      entityManager
          .createNativeQuery(
              "INSERT INTO Channel (channel_code, name, description) VALUES (0, 'ALL', 'ALL OF THE ABOVE')")
          .executeUpdate();

      entityManager.createNativeQuery("ALTER SEQUENCE channel_sequence RESTART WITH 1").executeUpdate();
    }
    if (!entityRepository.existsById(0)) {
      entityManager
          .createNativeQuery(
              "INSERT INTO Entity (entity_code, name, description) VALUES (0, 'ALL', 'ALL OF THE ABOVE')")
          .executeUpdate();

      entityManager.createNativeQuery("ALTER SEQUENCE entity_sequence RESTART WITH 1").executeUpdate();
    }

    if (!productPriceGroupRepository.existsById(0)) {
      entityManager
          .createNativeQuery(
              "INSERT INTO Product_Price_Group (product_price_group_code, name, description) VALUES (0, 'ALL', 'ALL OF"
                  + " THE ABOVE')")
          .executeUpdate();

      entityManager.createNativeQuery("ALTER SEQUENCE product_price_group_sequence RESTART WITH 1").executeUpdate();
    }
  }
}
