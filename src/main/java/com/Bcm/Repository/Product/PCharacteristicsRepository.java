package com.Bcm.Repository.Product;

import com.Bcm.Model.Product.ProductCharacteristics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PCharacteristicsRepository extends JpaRepository<ProductCharacteristics, Integer> {
    Optional<ProductCharacteristics> findById(int pCharacteristic_code);

    Optional<ProductCharacteristics> findByValueDescription_Value(String value);
}
