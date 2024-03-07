package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.ProductSpecification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductSpecificationRepository extends JpaRepository<ProductSpecification, Integer> {

    Optional<ProductSpecification> findById(int po_code);

    Optional<ProductSpecification> findByname(String name);

    @Query("SELECT p FROM ProductSpecification p WHERE p.name LIKE :name ")
    List<ProductSpecification> searchByKeyword(String name);

    @Query("SELECT po FROM ProductSpecification po JOIN po.poPlan c WHERE  c.SHDES = :name ")
    List<ProductSpecification> findAllWithPOPLAN(String name);

    @Query("SELECT p FROM ProductSpecification p WHERE p.poPlan.TMCODE = :TMCODE")
    List<ProductSpecification> findByPoPlan_TMCODE(int TMCODE);

}
