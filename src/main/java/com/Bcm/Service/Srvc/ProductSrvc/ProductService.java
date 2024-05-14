package com.Bcm.Service.Srvc.ProductSrvc;

import com.Bcm.Model.Product.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    List<Product> read();

    List<Product> searchByFamilyName(String familyName);

    Product findById(int Product_id);

    List<Product> searchByKeyword(String name);

}
