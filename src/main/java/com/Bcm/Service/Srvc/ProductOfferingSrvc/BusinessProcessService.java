package com.Bcm.Service.Srvc.ProductOfferingSrvc;

import com.Bcm.Model.ProductOfferingABE.BusinessProcess;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BusinessProcessService {

    BusinessProcess create(BusinessProcess BusinessProcess);

    List<BusinessProcess> read();

    BusinessProcess update(int businessProcessId, BusinessProcess BusinessProcess);

    String delete(int businessProcessId);

    BusinessProcess findById(int businessProcessId);

    List<BusinessProcess> searchByKeyword(String bussinessProcType);

    BusinessProcess findByBussinessProcType(String bussinessProcType);


}
