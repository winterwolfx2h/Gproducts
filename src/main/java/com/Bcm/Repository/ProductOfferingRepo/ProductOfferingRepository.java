package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.POPlan;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Market;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface ProductOfferingRepository extends JpaRepository<ProductOffering, Integer> {


    @Query("SELECT po FROM ProductOffering po JOIN po.category c WHERE  c.name LIKE %:name% ")
    List<ProductOffering> findAllWithCategory(String name);

    @Query("SELECT p FROM ProductOffering p WHERE p.category.po_CategoryCode = :po_CategoryCode")
    List<ProductOffering> findByCategory_po_CategoryCode(int po_CategoryCode);
    Optional<ProductOffering> findById(int po_code);




    @Query("SELECT p FROM ProductOffering p WHERE p.name LIKE %:name% ")
    List<ProductOffering> searchByKeyword(String name);
}

