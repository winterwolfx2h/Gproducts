package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.BundledProductOffer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BundledProductOfferRepository extends JpaRepository<BundledProductOffer, Integer> {
    Optional<BundledProductOffer> findById(int bdo_Code);

}

