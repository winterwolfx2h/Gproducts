package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductOfferingRepository extends JpaRepository<ProductOffering, Integer> {

    Optional<ProductOffering> findById(int po_code);

    Optional<ProductOffering> findByname(String name);

    List<ProductOffering> findByParent(String parentName);

    List<ProductOffering> findByPoType(String poType);

    List<ProductOffering> findByFamilyName(String familyName);

    @Query("SELECT p FROM ProductOffering p WHERE p.name = :name")
    List<ProductOffering> searchByKeyword(String name);

    @Query("SELECT po FROM ProductOffering po JOIN po.poAttributes c WHERE  c.charValue = :charValue ")
    List<ProductOffering> findAllWithPoAttributes(String charValue);

    @Query("SELECT p FROM ProductOffering p JOIN p.poAttributes pa WHERE pa.poAttribute_code = :poAttribute_code")
    List<ProductOffering> findByPoAttributes_poAttribute_code(int poAttribute_code);


    @Query("SELECT po FROM ProductOffering po JOIN po.productRelation c WHERE  c.type = :type ")
    List<ProductOffering> findAllWithProductRelation(String type);

    @Query("SELECT p FROM ProductOffering p WHERE p.productRelation.poRelation_Code = :poRelation_Code")
    List<ProductOffering> findByProductRelation_poRelation_Code(int poRelation_Code);

    @Query("SELECT po FROM ProductOffering po JOIN po.productOfferRelation c WHERE  c.type = :type ")
    List<ProductOffering> findAllWithProductOfferRelation(String type);

    @Query("SELECT p FROM ProductOffering p WHERE p.productOfferRelation.PoOfferRelation_Code = :PoOfferRelation_Code")
    List<ProductOffering> findByProductOfferRelation_PoOfferRelation_Code(int PoOfferRelation_Code);

    @Query("SELECT po FROM ProductOffering po JOIN po.logicalResource c WHERE  c.logicalResourceType = :logicalResourceType ")
    List<ProductOffering> findAllWithProductResource(String logicalResourceType);

    @Query("SELECT p FROM ProductOffering p WHERE p.logicalResource.logResourceId = :logResourceId")
    List<ProductOffering> findByProductResource_logResourceId(int logResourceId);

    @Query("SELECT po FROM ProductOffering po JOIN po.physicalResource c WHERE  c.physicalResourceType = :physicalResourceType ")
    List<ProductOffering> findAllWithPhysicalResource(String physicalResourceType);

    @Query("SELECT p FROM ProductOffering p WHERE p.physicalResource.phyResourceId = :phyResourceId")
    List<ProductOffering> findByPhysicalResource_phyResourceId(int phyResourceId);

    @Query("SELECT po FROM ProductOffering po JOIN po.businessProcess c WHERE  c.bussinessProcType = :bussinessProcType ")
    List<ProductOffering> findAllWithBusinessProcess(String bussinessProcType);

    @Query("SELECT p FROM ProductOffering p WHERE p.businessProcess.businessProcessId = :businessProcessId")
    List<ProductOffering> findByBusinessProcess_businessProcessId(int businessProcessId);

    @Query("SELECT po FROM ProductOffering po JOIN po.eligibility c WHERE  c.channel = :channel ")
    List<ProductOffering> findAllWithEligibility(String channel);

    @Query("SELECT p FROM ProductOffering p WHERE p.eligibility.eligibilityId = :eligibilityId")
    List<ProductOffering> findByEligibility_eligibilityId(int eligibilityId);

}


