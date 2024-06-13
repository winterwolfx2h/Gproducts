package com.Bcm.Service.Impl.Product;

import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.Product.Product;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import com.Bcm.Repository.Product.ProductRepository;
import com.Bcm.Service.Srvc.ProductSrvc.ProductService;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

  final ProductRepository productRepository;

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
    List<Product> products = productRepository.findByFamilyName(familyName);
    boolean hasLinkedProductOffering = products.stream().anyMatch(product -> product instanceof ProductOffering);
    if (!hasLinkedProductOffering) {
      return Collections.emptyList();
    }
    return products;
  }

  @Override
  public Product findById(int Product_id) {
    try {
      return productRepository
          .findById(Product_id)
          .orElseThrow(() -> new ResourceNotFoundException("Product with ID " + Product_id + " not found"));
    } catch (ResourceNotFoundException e) {
      throw new RuntimeException("Product with ID \"" + Product_id + "\" not found", e);
    }
  }

  @Override
  public List<Product> searchByKeyword(String name) {
    return productRepository.findByNameContainingIgnoreCase(name);
  }
}
