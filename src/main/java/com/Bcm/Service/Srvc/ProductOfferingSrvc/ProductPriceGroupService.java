package com.Bcm.Service.Srvc.ProductOfferingSrvc;

import com.Bcm.Model.ProductOfferingABE.ProductPriceGroup;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface ProductPriceGroupService {

  ProductPriceGroup create(ProductPriceGroup productPriceGroup);

  List<ProductPriceGroup> read();

  ProductPriceGroup update(int productPriceGroupCode, ProductPriceGroup productPriceGroup);

  String delete(int productPriceGroupCode);

  ProductPriceGroup findById(int productPriceGroupCode);

  List<ProductPriceGroup> searchByKeyword(String name);

  ProductPriceGroup findByName(String name);

  boolean findByNameexist(String name);

  boolean existsById(int productPriceGroupCode);
}
