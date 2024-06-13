package com.Bcm.Service.Srvc.ProductSrvc;

import com.Bcm.Model.Product.Product;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
  List<Product> read();

  List<Product> searchByFamilyName(String familyName);

  Product findById(int Product_id);

  List<Product> searchByKeyword(String name);
}
