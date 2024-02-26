package com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Provider;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProviderService {

    Provider create(Provider Provider);

    List<Provider> read();

    Provider update(int po_ProviderCode, Provider Provider);

    String delete(int po_ProviderCode);

    Provider findById(int po_ProviderCode);

    Provider findByName(String name);

    List<Provider> searchByKeyword(String name);


}
