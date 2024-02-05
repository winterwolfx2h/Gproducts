package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.BundledProductOfferOption;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BundledProductOfferOptionRepository extends JpaRepository<BundledProductOfferOption,Integer>{
    Optional<BundledProductOfferOption> findById(int bdoo_Code);

}

