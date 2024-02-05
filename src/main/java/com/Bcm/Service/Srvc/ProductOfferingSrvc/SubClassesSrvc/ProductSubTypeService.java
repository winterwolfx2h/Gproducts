package com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc;

import com.Bcm.Model.ProductOfferingABE.SubClasses.ProductSubType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductSubTypeService {

    ProductSubType create(ProductSubType productSubType);

    List<ProductSubType> read();

    ProductSubType update(int po_ProdSubTypeCode, ProductSubType productSubType);

    String delete(int po_ProdSubTypeCode);

    ProductSubType findById(int po_ProdSubTypeCode);

    List<ProductSubType> searchByKeyword(String name);
}
