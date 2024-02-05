package com.Bcm.Service.Srvc.ProductOfferingSrvc;

import com.Bcm.Model.ProductOfferingABE.BundledProductOfferOption;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BundledProductOfferOptionService {

    BundledProductOfferOption create(BundledProductOfferOption bundledProductOfferOption);

    List<BundledProductOfferOption> read();

    BundledProductOfferOption update(int bdoo_Code, BundledProductOfferOption bundledProductOfferOption);

    String delete(int bdoo_Code);

    BundledProductOfferOption findById(int bdoo_Code);


}
