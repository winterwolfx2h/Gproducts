package com.Bcm.Service.Srvc.ProductSrvc;

import com.Bcm.Exception.ProductNotFoundException;
import com.Bcm.Model.Product.Product;
import com.Bcm.Model.Product.ProductDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    List<Product> read();

    List<Product> searchByFamilyName(String familyName);

    Product findById(int Product_id);

    List<Product> searchByKeyword(String name);

    boolean existsByName(String name);

    Product createProductDTO(ProductDTO dto);

    Product getProductById(int Product_id) throws ProductNotFoundException;

    Product updateProdStockInd(ProductDTO dto, int productId, boolean stockInd) throws ProductNotFoundException;

    Product updateProductDTO(ProductDTO dto, int productId) throws ProductNotFoundException;
}
