package com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Market;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MarketService {

    Market create(Market market);

    List<Market> read();

    Market update(int po_MarketCode, Market market);

    String delete(int po_MarketCode);

    Market findById(int po_MarketCode);

    List<Market> searchByKeyword(String name);

    Market findByName(String name);

    boolean findByNameexist(String name);


}
