package com.Bcm.Service.Srvc.ProductOfferingSrvc;

import com.Bcm.Model.ProductOfferingABE.Eligibility;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EligibilityService {
    List<Eligibility> create(List<Eligibility> eligibilityList);

    List<Eligibility> read();

    Eligibility update(int eligibilityId, Eligibility Eligibility);

    String delete(int eligibilityId);

    Eligibility findById(int eligibilityId);

    List<Eligibility> searchByKeyword(String channel);

    Eligibility findByChannel(String channel);

    boolean existsById(int eligibilityId);

    boolean existsByChannel(String channel);

}

