package com.Bcm.Service.Srvc.ProductOfferingSrvc;

import com.Bcm.Model.ProductOfferingABE.BusinessProcess;
import org.springframework.stereotype.Service;

@Service
public interface BusinessProcessService {
//
//    List<BusinessProcess> create(List<BusinessProcess> businessProcesses);
//
//    List<BusinessProcess> read();
//
//    BusinessProcess update(int businessProcessId, BusinessProcess BusinessProcess);
//
//    String delete(int businessProcessId);
//
//    BusinessProcess findById(int businessProcessId);
//
//    List<BusinessProcess> searchByKeyword(String bussinessProcType);
//
//    BusinessProcess findByBussinessProcType(String bussinessProcType);
//
//    boolean existsById(int businessProcessId);

    BusinessProcess createBusinessProcess(BusinessProcess businessProcess) throws Exception;

}
