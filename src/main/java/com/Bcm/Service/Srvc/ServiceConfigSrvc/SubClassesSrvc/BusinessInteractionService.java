package com.Bcm.Service.Srvc.ServiceConfigSrvc.SubClassesSrvc;

import com.Bcm.Model.ServiceABE.SubClasses.BusinessInteraction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BusinessInteractionService {

    BusinessInteraction create(BusinessInteraction businessInteraction);

    List<BusinessInteraction> read();

    BusinessInteraction update(int BI_code, BusinessInteraction businessInteraction);

    String delete(int BI_code);

    BusinessInteraction findById(int BI_code);

    List<BusinessInteraction> searchByKeyword(String name);
}
