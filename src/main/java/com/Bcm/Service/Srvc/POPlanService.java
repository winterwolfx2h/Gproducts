package com.Bcm.Service.Srvc;

import com.Bcm.Model.ProductOfferingABE.POPlan;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface POPlanService {

    POPlan create (POPlan poPlan);
    List <POPlan> read();
    POPlan update(int PO_ID, POPlan poPlan);
    String delete (int PO_ID);
    POPlan findById(int PO_ID);

    List<POPlan> searchByKeyword(String name);


}
