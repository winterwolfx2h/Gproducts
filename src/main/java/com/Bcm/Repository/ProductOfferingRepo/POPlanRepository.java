package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.POPlan;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Market;
import com.Bcm.Model.ProductOfferingABE.SubClasses.SubMarket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface POPlanRepository extends JpaRepository<POPlan, Integer> {

    Optional<POPlan> findById(int TMCODE);

    Optional<POPlan> findBySHDES(String SHDES);

    boolean existsByMarketAndSubMarket(Market market, SubMarket subMarket);

    boolean existsBySHDES(String SHDES);


    @Query("SELECT p FROM POPlan p WHERE p.DES LIKE %:DES% ")
    List<POPlan> searchByKeyword(String DES);

    @Query("SELECT po FROM POPlan po JOIN po.market c WHERE  c.name = :name ")
    List<POPlan> findAllWithMarket(String name);

    @Query("SELECT p FROM POPlan p WHERE p.market.po_MarketCode = :po_MarketCode")
    List<POPlan> findByMarket_po_MarketCode(int po_MarketCode);

    @Query("SELECT po FROM POPlan po JOIN po.subMarket c WHERE  c.name = :name ")
    List<POPlan> findAllWithSubMarket(String name);

    @Query("SELECT p FROM POPlan p WHERE p.subMarket.po_SubMarketCode = :po_SubMarketCode")
    List<POPlan> findBySubMarket_po_SubMarketCode(int po_SubMarketCode);

}
