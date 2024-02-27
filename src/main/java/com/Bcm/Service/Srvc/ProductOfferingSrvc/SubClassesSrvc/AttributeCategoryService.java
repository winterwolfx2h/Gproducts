package com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc;

import com.Bcm.Model.ProductOfferingABE.SubClasses.AttributeCategory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AttributeCategoryService {

    AttributeCategory create(AttributeCategory attributeCategory);

    List<AttributeCategory> read();

    AttributeCategory update(int po_AttributeCategoryCode, AttributeCategory attributeCategory);

    String delete(int po_AttributeCategoryCode);

    AttributeCategory findById(int po_AttributeCategoryCode);

    AttributeCategory findByName(String name);

    List<AttributeCategory> searchByKeyword(String name);
}

