package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.BundledProductOfferOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BundledProductOfferOptionRepository extends JpaRepository<BundledProductOfferOption, Integer> {
    Optional<BundledProductOfferOption> findById(int bdoo_Code);

}

