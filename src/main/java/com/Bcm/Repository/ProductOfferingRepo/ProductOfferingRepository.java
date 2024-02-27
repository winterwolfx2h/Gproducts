package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductOfferingRepository extends JpaRepository<ProductOffering, Integer> {

    Optional<ProductOffering> findById(int po_code);

    Optional<ProductOffering> findByname(String name);

    @Query("SELECT p FROM ProductOffering p WHERE p.name = :name")
    List<ProductOffering> searchByKeyword(String name);

    @Query("SELECT po FROM ProductOffering po JOIN po.category c WHERE  c.name = :name ")
    List<ProductOffering> findAllWithCategory(String name);

    List<ProductOffering> findByParentName(String parentName);

    @Query("SELECT p FROM ProductOffering p WHERE p.category.po_CategoryCode = :po_CategoryCode")
    List<ProductOffering> findByCategory_po_CategoryCode(int po_CategoryCode);

    @Query("SELECT po FROM ProductOffering po JOIN po.parent c WHERE  c.name = :name ")
    List<ProductOffering> findAllWithParent(String name);

    @Query("SELECT p FROM ProductOffering p WHERE p.parent.po_ParentCode = :po_ParentCode")
    List<ProductOffering> findByParent_po_ParentCode(int po_ParentCode);

    @Query("SELECT po FROM ProductOffering po JOIN po.category c WHERE  c.name = :name ")
    List<ProductOffering> findAllWithFamily(String name);

    @Query("SELECT p FROM ProductOffering p WHERE p.productSpecification.po_SpecCode = :po_SpecCode")
    List<ProductOffering> findByProductSpecification_po_SpecCode(int po_SpecCode);


}


