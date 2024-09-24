package com.Bcm.Service.Srvc.ProductOfferingSrvc;

import com.Bcm.Exception.InvalidInputException;
import com.Bcm.Model.ProductOfferingABE.POAttributes;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface POAttributesService {

  POAttributes create(POAttributes poAttributes);

  List<POAttributes> read();

  POAttributes update(int poAttribute_code, POAttributes poAttributes);

  POAttributes saveOrUpdate(int poAttribute_code, POAttributes poAttributes);

  String delete(int poAttribute_code);

  POAttributes findById(int poAttribute_code);

  POAttributes findByDescription(String description);

  boolean existsById(int poAttribute_code);

  List<POAttributes> readByProductId(int productId);

  boolean validateDependentCfs(String dependentCfs);

  void setDefaultValueDescriptions(POAttributes poAttribute);

  boolean isCategoryValid(String attributeCategoryName);

  POAttributes createSinglePOAttribute(POAttributes poAttribute) throws InvalidInputException;

  private POAttributes createPOAttribute(POAttributes poAttribute) throws InvalidInputException {
    return create(poAttribute);
  }
}
