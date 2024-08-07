package com.Bcm.Service.Srvc.ProductSrvc;

import com.Bcm.Exception.ProductNotFoundException;
import com.Bcm.Exception.ProductOfferingNotFoundException;
import com.Bcm.Model.Product.GeneralInfoDTO;
import com.Bcm.Model.Product.Product;
import com.Bcm.Model.Product.ProductDTO;
import com.Bcm.Model.Product.ProductOfferingDTO;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    List<Product> read();

    List<Product> searchByFamilyName(String familyName);

    Product findById(int Product_id);

    List<Product> searchByKeyword(String name);

    Product createProductDTO(ProductDTO dto);

    Product getProductById(int Product_id) throws ProductNotFoundException;

    Product updateProdStockInd(ProductDTO dto, int productId, boolean stockInd)
            throws ProductNotFoundException;

}
