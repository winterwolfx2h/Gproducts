package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.Product.GeneralInfoDTO;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GeneralInfoRepository extends JpaRepository<ProductOffering, Integer> {




    Optional<GeneralInfoDTO> findByName(String name);



}
