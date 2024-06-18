package com.Bcm.Service.Srvc.ProductOfferingSrvc;

import com.Bcm.Model.ProductOfferingABE.BusinessProcess;
import com.Bcm.Model.ProductOfferingABE.Methods;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BusinessProcessService {
    BusinessProcess create(BusinessProcess businessProcess);

    List<BusinessProcess> read();

    BusinessProcess update(int businessProcess_id, BusinessProcess businessProcess);

    String delete(int businessProcess_id);

    BusinessProcess findById(int businessProcess_id);

    List<BusinessProcess> searchByKeyword(String name);

    BusinessProcess findByName(String name);


}
