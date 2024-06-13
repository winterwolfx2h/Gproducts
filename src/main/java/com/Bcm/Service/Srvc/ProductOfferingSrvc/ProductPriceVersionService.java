package com.Bcm.Service.Srvc.ProductOfferingSrvc;

import com.Bcm.Model.ProductOfferingABE.ProductPriceVersion;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface ProductPriceVersionService {

  ProductPriceVersion create(ProductPriceVersion productPriceVersion);

  List<ProductPriceVersion> read();

  ProductPriceVersion update(int productPriceVersionCode, ProductPriceVersion productPriceVersion);

  String delete(int productPriceVersionCode);

  ProductPriceVersion findById(int productPriceVersionCode);

  List<ProductPriceVersion> searchByKeyword(String name);

  ProductPriceVersion findByName(String name);

  boolean findByNameexist(String name);

  boolean existsById(int productPriceVersionCode);
}
