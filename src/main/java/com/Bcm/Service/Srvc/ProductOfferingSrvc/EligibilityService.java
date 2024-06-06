package com.Bcm.Service.Srvc.ProductOfferingSrvc;

import com.Bcm.Model.ProductOfferingABE.Eligibility;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EligibilityService {

   Eligibility create(Eligibility eligibility);

    List<Eligibility> read();

    Eligibility update(int eligibilityId, Eligibility Eligibility);

    String delete(int eligibilityId);

    Eligibility findById(int eligibilityId);

    boolean existsById(int eligibilityId);

    boolean findByIdExists(int eligibilityId);


}

