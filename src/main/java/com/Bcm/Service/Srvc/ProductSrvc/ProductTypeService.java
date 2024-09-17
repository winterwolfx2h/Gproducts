package com.Bcm.Service.Srvc.ProductSrvc;

import com.Bcm.Model.Product.ProductType;

import java.util.List;

public interface ProductTypeService {
    ProductType create(ProductType productType);

    List<ProductType> read();

    ProductType update(int productTypeCode, ProductType productType);

    String delete(int productTypeCode);

    ProductType findById(int productTypeCode);

    ProductType findByTypeName(String typeName);

    List<ProductType> searchByKeyword(String typeName);

    boolean existsById(int productTypeCode);
}
