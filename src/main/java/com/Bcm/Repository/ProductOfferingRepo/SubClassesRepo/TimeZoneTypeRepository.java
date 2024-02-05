package com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo;

import com.Bcm.Model.ProductOfferingABE.SubClasses.TimeZoneType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TimeZoneTypeRepository extends JpaRepository<TimeZoneType, Integer> {

    Optional<TimeZoneType> findById(int po_TimeZoneTypeCode);

    @Query("SELECT p FROM TimeZoneType p WHERE p.name LIKE %:name% ")
    List<TimeZoneType> searchByKeyword(String name);
}
