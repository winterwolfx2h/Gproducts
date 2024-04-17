package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.POAttributes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;



public interface POAttributesRepository extends JpaRepository<POAttributes, Integer> {
    Optional<POAttributes> findById(int poAttribute_code);

    Optional<POAttributes> findByValueDescription_Value(String value);
}