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

    @Query("SELECT po FROM ProductOffering po JOIN po.poAttributes c WHERE  c.attributeValDesc = :attributeValDesc ")
    List<ProductOffering> findAllWithPoAttributes(String attributeValDesc);

    @Query("SELECT p FROM ProductOffering p WHERE p.poAttributes.poAttribute_code = :poAttribute_code")
    List<ProductOffering> findByPoAttributes_poAttribute_code(int poAttribute_code);

    @Query("SELECT po FROM ProductOffering po JOIN po.productRelation c WHERE  c.type = :type ")
    List<ProductOffering> findAllWithProductRelation(String type);

    @Query("SELECT p FROM ProductOffering p WHERE p.productRelation.poRelation_Code = :poRelation_Code")
    List<ProductOffering> findByProductRelation_poRelation_Code(int poRelation_Code);

    @Query("SELECT po FROM ProductOffering po JOIN po.productOfferRelation c WHERE  c.name = :name ")
    List<ProductOffering> findAllWithProductOfferRelation(String name);

    @Query("SELECT p FROM ProductOffering p WHERE p.productOfferRelation.PoOfferRelation_Code = :PoOfferRelation_Code")
    List<ProductOffering> findByProductOfferRelation_PoOfferRelation_Code(int PoOfferRelation_Code);

    @Query("SELECT po FROM ProductOffering po JOIN po.productResource c WHERE  c.name = :name ")
    List<ProductOffering> findAllWithProductResource(String name);

    @Query("SELECT p FROM ProductOffering p WHERE p.productResource.PrResId = :PrResId")
    List<ProductOffering> findByProductResource_PrResId(int PrResId);

    @Query("SELECT po FROM ProductOffering po JOIN po.businessProcess c WHERE  c.bussinessProcType = :bussinessProcType ")
    List<ProductOffering> findAllWithBusinessProcess(String bussinessProcType);

    @Query("SELECT p FROM ProductOffering p WHERE p.businessProcess.businessProcessId = :businessProcessId")
    List<ProductOffering> findByBusinessProcess_businessProcessId(int businessProcessId);

    @Query("SELECT po FROM ProductOffering po JOIN po.eligibility c WHERE  c.channel = :channel ")
    List<ProductOffering> findAllWithEligibility(String channel);

    @Query("SELECT p FROM ProductOffering p WHERE p.eligibility.eligibilityId = :eligibilityId")
    List<ProductOffering> findByEligibility_eligibilityId(int eligibilityId);

}


