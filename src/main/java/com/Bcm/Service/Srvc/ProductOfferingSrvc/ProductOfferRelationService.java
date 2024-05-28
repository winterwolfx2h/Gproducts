package com.Bcm.Service.Srvc.ProductOfferingSrvc;

import com.Bcm.Model.ProductOfferingABE.ProductOfferRelation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductOfferRelationService {


    ProductOfferRelation create(ProductOfferRelation ProductOfferRelation);

    List<ProductOfferRelation> read();

    ProductOfferRelation update(int pOfferRelationCode, ProductOfferRelation ProductOfferRelation);

    String delete(int pOfferRelationCode);

    ProductOfferRelation findById(int pOfferRelationCode);

    List<ProductOfferRelation> searchByKeyword(String type);

    ProductOfferRelation findByType(String type);

    boolean existsById(int pOfferRelationCode);
}
