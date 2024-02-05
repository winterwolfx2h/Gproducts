package com.Bcm.Service.Srvc.ProductOfferingSrvc;

import com.Bcm.Model.ProductOfferingABE.ProductOfferingRelationship;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductOfferingRelationshipService {

    ProductOfferingRelationship create(ProductOfferingRelationship productOfferingRelationship);

    List<ProductOfferingRelationship> read();

    ProductOfferingRelationship update(int por_Code, ProductOfferingRelationship productOfferingRelationship);

    String delete(int por_Code);

    ProductOfferingRelationship findById(int por_Code);

}
