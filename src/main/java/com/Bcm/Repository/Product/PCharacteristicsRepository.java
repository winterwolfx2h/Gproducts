package com.Bcm.Repository.Product;

import com.Bcm.Model.Product.ProductCharacteristics;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PCharacteristicsRepository extends JpaRepository<ProductCharacteristics, Integer> {
  Optional<ProductCharacteristics> findById(int pCharacteristic_code);

  Optional<ProductCharacteristics> findByValueDescription_Value(String value);
}
