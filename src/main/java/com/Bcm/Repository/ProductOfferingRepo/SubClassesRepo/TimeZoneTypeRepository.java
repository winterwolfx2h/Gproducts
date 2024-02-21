package com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo;

import com.Bcm.Model.ProductOfferingABE.SubClasses.TimeZoneType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TimeZoneTypeRepository extends JpaRepository<TimeZoneType, Integer> {

    Optional<TimeZoneType> findById(int po_TimeZoneTypeCode);

    Optional<TimeZoneType> findByName(String name);

    @Query("SELECT p FROM TimeZoneType p WHERE p.name = :name")
    List<TimeZoneType> searchByKeyword(String name);
}
