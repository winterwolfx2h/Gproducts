package com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Market.SubMarket;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubMarketRepository extends JpaRepository<SubMarket, Integer> {
  Optional<SubMarket> findBySubMarketName(String subMarketName);
}
