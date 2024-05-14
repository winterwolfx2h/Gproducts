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

    Optional<POPlan> findByName(String name);

    boolean existsByMarketAndSubMarket(Market market, SubMarket subMarket);

    boolean existsByName(String name);


    @Query("SELECT p FROM POPlan p WHERE p.detailedDescription LIKE %:detailedDescription% ")
    List<POPlan> searchByKeyword(String detailedDescription);
}
