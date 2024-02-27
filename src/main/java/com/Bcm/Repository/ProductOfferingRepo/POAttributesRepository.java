package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.POAttributes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface POAttributesRepository extends JpaRepository<POAttributes, Integer> {

    Optional<POAttributes> findById(int poAttribute_code);

    Optional<POAttributes> findByDescription(String description);

    @Query("SELECT p FROM POAttributes p WHERE p.description LIKE %:description% ")
    List<POAttributes> searchByKeyword(String description);
}
