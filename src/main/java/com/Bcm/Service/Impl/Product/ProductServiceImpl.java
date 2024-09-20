package com.Bcm.Service.Impl.Product;

import com.Bcm.Exception.ProductNotFoundException;
import com.Bcm.Exception.ProductOfferingAlreadyExistsException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.Product.Product;
import com.Bcm.Model.Product.ProductDTO;
import com.Bcm.Repository.Product.ProductRepository;
import com.Bcm.Service.Srvc.ProductSrvc.ProductService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
  public Product getProductById(int Product_id) throws ProductNotFoundException {
    return productRepository
        .findById(Product_id)
        .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + Product_id));
  }

  @Override
  public List<Product> searchByFamilyName(String familyName) {
    List<Product> products = productRepository.findByFamilyName(familyName);
    boolean hasLinkedProduct = products.stream().anyMatch(Product.class::isInstance);
    if (!hasLinkedProduct) {
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

  @Override
  public Product createProductDTO(ProductDTO dto) {
    Optional<Product> existingProduct = productRepository.findByName(dto.getName());
    if (existingProduct.isPresent()) {
      throw new ProductOfferingAlreadyExistsException(
          "A product with the name '" + dto.getName() + "' already exists.");
    }

    Product product = new Product();
    product.setName(dto.getName());
    product.setProductType(dto.getProductType());
    product.setEffectiveFrom(dto.getEffectiveFrom());
    product.setEffectiveTo(dto.getEffectiveTo());
    product.setDescription(dto.getDescription());
    product.setDetailedDescription(dto.getDetailedDescription());
    product.setSellInd(dto.getSellInd());
    product.setQuantityInd(dto.getQuantityInd());
    product.setStockInd(dto.getStockInd());
    product.setFamilyName(dto.getFamilyName());
    product.setSubFamily(dto.getSubFamily());
    product.setStatus("Working state");

    return productRepository.save(product);
  }

  @Override
  public Product updateProdStockInd(ProductDTO dto, int productId, boolean stockInd) throws ProductNotFoundException {
    Product product = getProductById(productId);

    product.setStockInd(stockInd);
    product.setStockInd(stockInd);
    return productRepository.save(product);
  }

  @Override
  public boolean existsByName(String name) {
    return productRepository.findByName(name).isPresent();
  }

  @Override
  public Product updateProductDTO(ProductDTO dto, int productId) throws ProductNotFoundException {
    Product product = getProductById(productId);
    if (product == null) {
      throw new ProductNotFoundException("Product not found");
    }

    product.setName(dto.getName());
    product.setEffectiveFrom(dto.getEffectiveFrom());
    product.setEffectiveTo(dto.getEffectiveTo());
    product.setDescription(dto.getDescription());
    product.setDetailedDescription(dto.getDetailedDescription());
    product.setSellInd(dto.getSellInd());
    product.setQuantityInd(dto.getQuantityInd());

    return productRepository.save(product);
  }
}
