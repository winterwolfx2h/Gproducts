package com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface ProviderRepository extends JpaRepository<Provider, Integer> {
    Optional<Provider> findById(int po_ParentCode);

    Optional<Provider> findByName(String name);

    @Query("SELECT p FROM Provider p WHERE p.name = :name")
    List<Provider> searchByKeyword(String name);
}
