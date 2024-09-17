package com.Bcm.Service.Srvc.ProductOfferingSrvc;

import com.Bcm.Model.ProductOfferingABE.Tax;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaxService {

    Tax create(Tax tax);

    List<Tax> create(List<Tax> taxes);

    List<Tax> read();

    Tax update(int taxCode, Tax tax);

    String delete(int taxCode);

    Tax findById(int taxCode);

    boolean existsById(int taxCode);

    List<Tax> searchByName(String name);
}
