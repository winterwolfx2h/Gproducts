package com.Bcm.Service.Srvc.ProductOfferingSrvc;

import com.Bcm.Model.ProductOfferingABE.ProductSpecification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductSpecificationService {

    ProductSpecification create(ProductSpecification productSpecification);

    List<ProductSpecification> read();

    ProductSpecification update(int po_SpecCode, ProductSpecification productSpecification);

    String delete(int po_SpecCode);

    ProductSpecification findById(int po_SpecCode);

    List<ProductSpecification> searchByKeyword(String name);

    ProductSpecification findByName(String name);


}
