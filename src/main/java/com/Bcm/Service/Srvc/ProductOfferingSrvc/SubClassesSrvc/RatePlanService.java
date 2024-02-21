package com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc;

import com.Bcm.Model.ProductOfferingABE.SubClasses.RatePlan;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RatePlanService {

    RatePlan create(RatePlan ratePlan);

    List<RatePlan> read();

    RatePlan update(int po_RatePlanCode, RatePlan ratePlan);

    String delete(int po_RatePlanCode);

    RatePlan findById(int po_RatePlanCode);

    List<RatePlan> searchByKeyword(String name);

    RatePlan findByName(String name);

}
