package com.Bcm.Service.Srvc.ProductOfferingSrvc;

import com.Bcm.Model.ProductOfferingABE.POAttributes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface POAttributesService {


    POAttributes create(POAttributes poAttributes);

    List<POAttributes> read();

    POAttributes update(int poAttribute_code, POAttributes poAttributes);

    String delete(int poAttribute_code);

    POAttributes findById(int poAttribute_code);


    POAttributes findByDescription(String description);

    boolean existsById(int poAttribute_code);


}
