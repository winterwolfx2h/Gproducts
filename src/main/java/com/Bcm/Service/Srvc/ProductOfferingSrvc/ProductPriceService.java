package com.Bcm.Service.Srvc.ProductOfferingSrvc;

import com.Bcm.Model.ProductOfferingABE.ProductPrice;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public interface ProductPriceService {

  ProductPrice create(ProductPrice productPrice);

  List<ProductPrice> create(List<ProductPrice> productPrices);

  List<ProductPrice> read();

  ProductPrice update(int productPriceCode, ProductPrice productPrice);

  String delete(int productPriceCode);

  ProductPrice findById(int productPriceCode);

  boolean existsById(int productPriceCode);

  List<ProductPrice> searchByPrice(float cashPrice);

  List<ProductPrice> findByProductId(int productId);

  Map<String, Object> calculatePriceWithTax(float originalPrice, List<Integer> taxCodes);
}
