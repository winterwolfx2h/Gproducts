package com.Bcm.Service.Srvc;

import com.Bcm.Model.ProductOfferingABE.POPlan;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Market;
import com.Bcm.Model.ProductOfferingABE.SubClasses.SubMarket;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface POPlanService {

    POPlan create(POPlan poPlan);

    List<POPlan> read();

    POPlan update(int TMCODE, POPlan poPlan);

    String delete(int TMCODE);

    POPlan findById(int TMCODE);

    List<POPlan> searchByKeyword(String detailedDescription);

    POPlan findByName(String name);

    POPlan changePoplanStatus(int TMCODE);

    boolean existsById(int TMCODE);

    boolean existsByMarketAndSubMarket(Market market, SubMarket subMarket);

    boolean existsByName(String name);

}
