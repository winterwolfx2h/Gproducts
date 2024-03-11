package com.Bcm.Service.Impl.Product;

import com.Bcm.Model.Product.Product;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
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
    public List<Product> searchByFamilyName(String familyName) {
        return productRepository.findByFamily_Name(familyName);
    }


    @Override
    public List<ProductOffering> findByParentName(String parentName) {
        try {
            return productRepository.findByParent(parentName);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while searching for product offerings by parent name: " + parentName, e);
        }
    }

}