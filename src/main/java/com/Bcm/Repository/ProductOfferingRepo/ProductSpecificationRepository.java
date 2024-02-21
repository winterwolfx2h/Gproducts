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

    @Query("SELECT po FROM ProductSpecification po JOIN po.family c WHERE  c.name = :name ")
    List<ProductSpecification> findAllWithFamily(String name);

    @Query("SELECT p FROM ProductSpecification p WHERE p.family.po_FamilyCode = :po_FamilyCode")
    List<ProductSpecification> findByFamily_po_FamilyCode(int po_FamilyCode);

    @Query("SELECT po FROM ProductSpecification po JOIN po.market c WHERE  c.name = :name ")
    List<ProductSpecification> findAllWithMarket(String name);

    @Query("SELECT p FROM ProductSpecification p WHERE p.market.po_MarketCode = :po_MarketCode")
    List<ProductSpecification> findByMarket_po_MarketCode(int po_MarketCode);

    @Query("SELECT po FROM ProductSpecification po JOIN po.subMarket c WHERE  c.name = :name ")
    List<ProductSpecification> findAllWithSubMarket(String name);

    @Query("SELECT p FROM ProductSpecification p WHERE p.subMarket.po_SubMarketCode = :po_SubMarketCode")
    List<ProductSpecification> findBySubMarket_po_SubMarketCode(int po_SubMarketCode);

    @Query("SELECT po FROM ProductSpecification po JOIN po.poPlan c WHERE  c.SHDES = :name ")
    List<ProductSpecification> findAllWithPOPLAN(String name);

    @Query("SELECT p FROM ProductSpecification p WHERE p.poPlan.TMCODE = :TMCODE")
    List<ProductSpecification> findByPoPlan_TMCODE(int TMCODE);

}
