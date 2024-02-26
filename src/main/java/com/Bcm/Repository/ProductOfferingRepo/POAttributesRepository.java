package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.POAttributes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface POAttributesRepository extends JpaRepository<POAttributes, Integer> {

    Optional<POAttributes> findById(int poAttribute_code);

    Optional<POAttributes> findByName(String name);

    @Query("SELECT p FROM POAttributes p WHERE p.name LIKE %:name% ")
    List<POAttributes> searchByKeyword(String name);
}
