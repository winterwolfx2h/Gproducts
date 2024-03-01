package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.ProductResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface ProductResourceRepository extends JpaRepository<ProductResource, Integer> {

    Optional<ProductResource> findById(int PrResId);

    Optional<ProductResource> findByname(String name);

    @Query("SELECT p FROM ProductResource p WHERE p.name LIKE :name ")
    List<ProductResource> searchByKeyword(String name);
/*

    @Query("SELECT po FROM ProductResource po JOIN po.prServiceId c WHERE  c.name = :name ")
    List<ProductResource> findAllWithPrServiceId(String name);

    @Query("SELECT p FROM ProductResource p WHERE p.prServiceId.pr_PrServiceId = :pr_PrServiceId")
    List<ProductResource> findByPrServiceId_pr_PrServiceId(int pr_PrServiceId);

    @Query("SELECT po FROM ProductResource po JOIN po.lrServiceId c WHERE  c.name = :name ")
    List<ProductResource> findAllWithLrServiceId(String name);

    @Query("SELECT p FROM ProductResource p WHERE p.lrServiceId.pr_LrServiceId = :pr_LrServiceId")
    List<ProductResource> findByPrServiceId_lr_PrServiceId(int pr_LrServiceId);

*/
}
