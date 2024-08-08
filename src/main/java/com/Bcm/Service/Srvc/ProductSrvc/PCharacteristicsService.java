package com.Bcm.Service.Srvc.ProductSrvc;

import com.Bcm.Model.Product.ProductCharacteristics;

import java.util.List;

public interface PCharacteristicsService {
    ProductCharacteristics create(ProductCharacteristics productCharacteristics);

    List<ProductCharacteristics> read();

    ProductCharacteristics update(int pCharacteristic_code, ProductCharacteristics productCharacteristics);

    ProductCharacteristics saveOrUpdate(int pCharacteristic_code, ProductCharacteristics productCharacteristics);

    String delete(int pCharacteristic_code);

    ProductCharacteristics findById(int pCharacteristic_code);

    ProductCharacteristics findByDescription(String description);

    boolean existsById(int pCharacteristic_code);
}
