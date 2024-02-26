package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.POCharacteristicType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface POCharacteristicTypeRepository extends JpaRepository<POCharacteristicType, Integer> {

    Optional<POCharacteristicType> findById(int poCharType_code);

    Optional<POCharacteristicType> findByName(String name);

    @Query("SELECT p FROM POCharacteristicType p WHERE p.name LIKE %:name% ")
    List<POCharacteristicType> searchByKeyword(String name);

    @Query("SELECT po FROM POCharacteristicType po JOIN po.provider c WHERE  c.name = :name ")
    List<POCharacteristicType> findAllWithProvider(String name);

    @Query("SELECT p FROM POCharacteristicType p WHERE p.provider.po_ProviderCode = :po_ProviderCode")
    List<POCharacteristicType> findByProvider_po_ProviderCode(int po_ProviderCode);
}
