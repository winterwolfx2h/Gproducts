package com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc;

import com.Bcm.Model.ProductOfferingABE.SubClasses.SubMarket;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface SubMarketService {

  SubMarket create(SubMarket SubMarket);

  List<SubMarket> read();

  SubMarket update(int po_SubMarketCode, SubMarket SubMarket);

  String delete(int po_SubMarketCode);

  SubMarket findById(int po_SubMarketCode);

  List<SubMarket> searchByKeyword(String name);

  SubMarket findByName(String name);

  boolean findByNameexist(String name);
}
