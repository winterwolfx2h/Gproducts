package com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc;

import com.Bcm.Model.ProductOfferingABE.SubClasses.LrServiceId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LrServiceIdService {

    LrServiceId create(LrServiceId LrServiceId);

    List<LrServiceId> read();

    LrServiceId update(int pr_LrServiceId, LrServiceId LrServiceId);

    String delete(int pr_LrServiceId);

    LrServiceId findById(int pr_LrServiceId);

    List<LrServiceId> searchByKeyword(String name);

    LrServiceId findByName(String name);


}
