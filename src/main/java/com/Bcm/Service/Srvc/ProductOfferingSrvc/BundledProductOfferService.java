package com.Bcm.Service.Srvc.ProductOfferingSrvc;

import com.Bcm.Model.ProductOfferingABE.BundledProductOffer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BundledProductOfferService {

    BundledProductOffer create(BundledProductOffer bundledProductOffer);

    List<BundledProductOffer> read();

    BundledProductOffer update(int bdo_Code, BundledProductOffer bundledProductOffer);

    String delete(int bdo_Code);

    BundledProductOffer findById(int bdo_Code);


}

