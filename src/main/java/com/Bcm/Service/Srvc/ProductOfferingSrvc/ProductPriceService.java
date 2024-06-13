package com.Bcm.Service.Srvc.ProductOfferingSrvc;

import com.Bcm.Model.ProductOfferingABE.ProductPrice;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface ProductPriceService {

  ProductPrice create(ProductPrice productPrice);

  List<ProductPrice> read();

  ProductPrice update(int productPriceCode, ProductPrice productPrice);

  String delete(int productPriceCode);

  ProductPrice findById(int productPriceCode);

  boolean existsById(int productPriceCode);

  List<ProductPrice> searchByPrice(float cashPrice);
}
