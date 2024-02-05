package com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc;

import com.Bcm.Model.ProductOfferingABE.SubClasses.ProductTechnicalType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductTechnicalTypeService {

    ProductTechnicalType create(ProductTechnicalType productTechnicalType);

    List<ProductTechnicalType> read();

    ProductTechnicalType update(int po_ProdTechTypeCode, ProductTechnicalType productTechnicalType);

    String delete(int po_ProdTechTypeCode);

    ProductTechnicalType findById(int po_ProdTechTypeCode);

    List<ProductTechnicalType> searchByKeyword(String name);
}
