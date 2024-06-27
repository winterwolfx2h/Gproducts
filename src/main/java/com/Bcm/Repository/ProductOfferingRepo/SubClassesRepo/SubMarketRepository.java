package com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Market.SubMarket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubMarketRepository extends JpaRepository<SubMarket, Integer> {
    Optional<SubMarket> findBySubMarketName(String subMarketName);

}
