package com.Bcm.Service.Srvc.ProductOfferingSrvc;

import com.Bcm.Model.ProductOfferingABE.Tax;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface TaxService {

    Tax create(Tax tax);

    List<Tax> read();

    Tax update(int taxCode, Tax tax);

    String delete(int taxCode);

    Tax findById(int taxCode);

    boolean existsById(int taxCode);

    List<Tax> searchByName(String name);
}