package com.Bcm.Service.Srvc.ProductOfferingSrvc;

import com.Bcm.Model.ProductOfferingABE.ProductRelation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductRelationService {

    ProductRelation create(ProductRelation productRelation);

    List<ProductRelation> read();

    ProductRelation update(int poRelation_Code, ProductRelation ProductRelation);

    String delete(int poRelation_Code);

    ProductRelation findById(int poRelation_Code);

    List<ProductRelation> searchByKeyword(String type);

    ProductRelation findByType(String type);


}
