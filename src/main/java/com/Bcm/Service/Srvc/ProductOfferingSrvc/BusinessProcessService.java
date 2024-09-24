package com.Bcm.Service.Srvc.ProductOfferingSrvc;

import com.Bcm.Model.ProductOfferingABE.BusinessProcess;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface BusinessProcessService {
  BusinessProcess create(BusinessProcess businessProcess);

  List<BusinessProcess> read();

  BusinessProcess update(int businessProcess_id, BusinessProcess businessProcess);

  String delete(int businessProcess_id);

  BusinessProcess findById(int businessProcess_id);

  List<BusinessProcess> searchByKeyword(String name);

  BusinessProcess findByName(String name);

  List<BusinessProcess> findBusinessProcessByProductId(int productId);
}
