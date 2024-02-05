package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.BundledProductOffer;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BundledProductOfferRepository extends JpaRepository<BundledProductOffer,Integer>{
    Optional<BundledProductOffer> findById(int bdo_Code);

}

