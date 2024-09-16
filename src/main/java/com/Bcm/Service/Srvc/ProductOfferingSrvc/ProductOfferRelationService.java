package com.Bcm.Service.Srvc.ProductOfferingSrvc;

import com.Bcm.Model.ProductOfferingABE.ProductOfferRelation;
import com.Bcm.Model.ProductOfferingABE.RelationResponse;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface ProductOfferRelationService {

  List<ProductOfferRelation> create(List<ProductOfferRelation> productOfferRelations);

  List<ProductOfferRelation> read();

  List<ProductOfferRelation> searchByKeyword(String type);

  ProductOfferRelation findById(Integer productOfferRel_code); // Modification pour utiliser l'ID primaire

  void deleteById(Integer productOfferRel_code); // Modification pour utiliser l'ID primaire

  List<RelationResponse> searchByProductID(Integer productId);
}
