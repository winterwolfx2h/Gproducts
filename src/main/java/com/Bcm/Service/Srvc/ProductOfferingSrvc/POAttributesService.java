package com.Bcm.Service.Srvc.ProductOfferingSrvc;

import com.Bcm.Model.ProductOfferingABE.POAttributes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface POAttributesService {


    POAttributes create(POAttributes POAttributes);

    List<POAttributes> read();

    POAttributes update(int poAttribute_code, POAttributes POAttributes);

    String delete(int poAttribute_code);

    POAttributes findById(int poAttribute_code);

    List<POAttributes> searchByKeyword(String name);

    POAttributes findByName(String name);


}
