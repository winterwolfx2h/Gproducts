package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.POAttributes;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface POAttributesRepository extends JpaRepository<POAttributes, Integer> {
  Optional<POAttributes> findById(int poAttribute_code);

  Optional<POAttributes> findByValueDescription_Value(String value);
}
