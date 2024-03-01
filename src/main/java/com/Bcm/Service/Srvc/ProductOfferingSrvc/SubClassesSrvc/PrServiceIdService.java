package com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc;

import com.Bcm.Model.ProductOfferingABE.SubClasses.PrServiceId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PrServiceIdService {

    PrServiceId create(PrServiceId PrServiceId);

    List<PrServiceId> read();

    PrServiceId update(int pr_PrServiceId, PrServiceId PrServiceId);

    String delete(int pr_PrServiceId);

    PrServiceId findById(int pr_PrServiceId);

    List<PrServiceId> searchByKeyword(String name);

    PrServiceId findByName(String name);


}
