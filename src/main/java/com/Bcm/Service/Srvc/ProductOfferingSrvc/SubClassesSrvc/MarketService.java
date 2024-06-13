package com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Market;
import java.util.List;
import org.springframework.stereotype.Service;

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
