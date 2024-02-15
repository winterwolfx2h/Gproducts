package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.BundledProductOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BundledProductOfferRepository extends JpaRepository<BundledProductOffer, Integer> {
    Optional<BundledProductOffer> findById(int bdo_Code);

}

