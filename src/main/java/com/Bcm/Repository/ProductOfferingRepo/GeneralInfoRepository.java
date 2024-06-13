package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.Product.GeneralInfoDTO;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneralInfoRepository extends JpaRepository<ProductOffering, Integer> {
  Optional<GeneralInfoDTO> findByName(String name);

  List<ProductOffering> findByNameNotLike(String namePattern);
}
