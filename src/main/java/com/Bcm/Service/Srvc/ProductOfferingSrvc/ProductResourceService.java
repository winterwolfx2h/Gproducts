package com.Bcm.Service.Srvc.ProductOfferingSrvc;

import com.Bcm.Model.ProductOfferingABE.ProductResource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductResourceService {

    ProductResource create(ProductResource productResource);

    List<ProductResource> read();

    ProductResource update(int PrResId, ProductResource productResource);

    String delete(int PrResId);

    ProductResource findById(int PrResId);

    List<ProductResource> searchByKeyword(String name);

    ProductResource findByName(String name);


}
