package com.Bcm.Service.Impl.Product;

import com.Bcm.Exception.*;
import com.Bcm.Model.ProductOfferingABE.POPlan;
import com.Bcm.Model.product.Product;
import com.Bcm.Repository.Product.ProductRepository;
import com.Bcm.Repository.ProductOfferingRepo.POPlanRepository;
import com.Bcm.Service.Srvc.ProductSrvc.ProductService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Optional;
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


}