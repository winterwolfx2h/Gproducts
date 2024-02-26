package com.Bcm.Service.Impl.Product;

import com.Bcm.Model.product.Product;
import com.Bcm.Repository.Product.ProductRepository;
import com.Bcm.Service.Srvc.ProductSrvc.ProductService;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    final
    ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }




    @Override
    public List<Product> read() {
        try {
            return productRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while reading Product", e);
        }
    }

    @Override
    public List<Product> searchWithFamily(String name) {
        try {
            return productRepository.findAllWithFamily(name);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while reading products with Families", e);
        }
    }


}