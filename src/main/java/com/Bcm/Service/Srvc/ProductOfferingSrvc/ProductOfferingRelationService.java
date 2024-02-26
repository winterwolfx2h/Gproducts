package com.Bcm.Service.Srvc.ProductOfferingSrvc;

import com.Bcm.Model.ProductOfferingABE.ProductOfferingRelation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductOfferingRelationService {

    ProductOfferingRelation create(ProductOfferingRelation productOfferingRelation);

    List<ProductOfferingRelation> read();

    ProductOfferingRelation update(int poRelation_Code, ProductOfferingRelation productOfferingRelation);

    String delete(int poRelation_Code);

    ProductOfferingRelation findById(int poRelation_Code);

    List<ProductOfferingRelation> searchByKeyword(String name);

    ProductOfferingRelation findByName(String name);
}
