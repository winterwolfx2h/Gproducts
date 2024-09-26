package com.Bcm.Service.Impl.Product;

import com.Bcm.Exception.ProductNotFoundException;
import com.Bcm.Exception.ProductOfferingAlreadyExistsException;
import com.Bcm.Model.Product.Product;
import com.Bcm.Model.Product.ProductDTO;
import com.Bcm.Repository.Product.ProductRepository;
import com.Bcm.Service.Srvc.ProductSrvc.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

  final ProductRepository productRepository;
  private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

  public ProductServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public List<Product> read() {
    logger.info("Fetching all products");
    try {
      List<Product> products = productRepository.findAll();
      logger.info("Successfully fetched {} products", products.size());
      return products;
    } catch (Exception e) {
      logger.error("An unexpected error occurred while reading products: {}", e.getMessage());
      throw new RuntimeException("An unexpected error occurred while reading products", e);
    }
  }

  @Override
  public Product getProductById(int Product_id) throws ProductNotFoundException {
    logger.info("Fetching product with ID: {}", Product_id);
    return productRepository
            .findById(Product_id)
            .orElseThrow(() -> {
              logger.error("Product not found with id: {}", Product_id);
              return new ProductNotFoundException("Product not found with id: " + Product_id);
            });
  }

  @Override
  public List<Product> searchByFamilyName(String familyName) {
    logger.info("Searching products by family name: {}", familyName);
    List<Product> products = productRepository.findByFamilyName(familyName);
    if (products.isEmpty()) {
      logger.warn("No linked products found for family name: {}", familyName);
      return Collections.emptyList();
    }
    logger.info("Found {} products for family name: {}", products.size(), familyName);
    return products;
  }

  @Override
  public Product findById(int Product_id) {
    logger.info("Finding product with ID: {}", Product_id);
    try {
      Product product = productRepository.findById(Product_id)
              .orElseThrow(() -> {
                logger.error("Product with ID {} not found", Product_id);
                return new ProductNotFoundException("Product with ID " + Product_id + " not found");
              });
      logger.info("Product found: {}", product.getName());
      return product;
    } catch (ProductNotFoundException e) {
      logger.error("Product with ID {} not found: {}", Product_id, e.getMessage());
      throw new RuntimeException("Product with ID \"" + Product_id + "\" not found", e);
    }
  }

  @Override
  public List<Product> searchByKeyword(String name) {
    logger.info("Searching products by keyword: {}", name);
    List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
    logger.info("Found {} products for keyword: {}", products.size(), name);
    return products;
  }

  @Override
  public Product createProductDTO(ProductDTO dto) {
    logger.info("Creating new product with name: {}", dto.getName());
    Optional<Product> existingProduct = productRepository.findByName(dto.getName());
    if (existingProduct.isPresent()) {
      logger.error("A product with the name '{}' already exists.", dto.getName());
      throw new ProductOfferingAlreadyExistsException("A product with the name '" + dto.getName() + "' already exists.");
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

    Product createdProduct = productRepository.save(product);
    logger.info("Successfully created product: {}", createdProduct.getName());
    return createdProduct;
  }

  @Override
  public Product updateProdStockInd(ProductDTO dto, int productId, boolean stockInd) throws ProductNotFoundException {
    logger.info("Updating stock indicator for product ID: {}", productId);
    Product product = getProductById(productId);
    product.setStockInd(stockInd);
    Product updatedProduct = productRepository.save(product);
    logger.info("Updated stock indicator for product ID {}: {}", productId, stockInd);
    return updatedProduct;
  }

  @Override
  public boolean existsByName(String name) {
    logger.info("Checking if product exists by name: {}", name);
    boolean exists = productRepository.findByName(name).isPresent();
    logger.info("Product with name '{}' exists: {}", name, exists);
    return exists;
  }

  @Override
  public Product updateProductDTO(ProductDTO dto, int productId) throws ProductNotFoundException {
    logger.info("Updating product with ID: {}", productId);
    Product product = getProductById(productId);
    product.setName(dto.getName());
    product.setEffectiveFrom(dto.getEffectiveFrom());
    product.setEffectiveTo(dto.getEffectiveTo());
    product.setDescription(dto.getDescription());
    product.setDetailedDescription(dto.getDetailedDescription());
    product.setSellInd(dto.getSellInd());
    product.setQuantityInd(dto.getQuantityInd());

    Product updatedProduct = productRepository.save(product);
    logger.info("Successfully updated product ID {}: {}", productId, updatedProduct.getName());
    return updatedProduct;
  }

  @Override
  public Map<String, String> fetchProductResourceDetails(int productId) {
    logger.info("Fetching product resource details for product ID: {}", productId);
    List<Object[]> results = productRepository.findProductResourceDetails(productId);

    if (results.isEmpty()) {
      logger.warn("No CFS or Physical Resource associated with product ID: {}", productId);
      return Map.of("message", "No CFS or Physical Resource is associated with that product.");
    }

    Map<String, String> response = new HashMap<>();
    for (Object[] result : results) {
      String physicalResourceName = (String) result[0];
      String serviceSpecName = (String) result[1];

      response.put("physicalResourceName", physicalResourceName != null ? physicalResourceName : "N/A");
      response.put("serviceSpecName", serviceSpecName != null ? serviceSpecName : "N/A");
      logger.info("Fetched resource detail - Physical Resource Name: {}, Service Spec Name: {}", physicalResourceName, serviceSpecName);
    }
    return response;
  }

  @Override
  public List<Map<String, Object>> fetchProductDetails(int productId) throws ProductNotFoundException {
    logger.info("Fetching product details for product ID: {}", productId);
    List<Map<String, Object>> results = productRepository.findProductDetails(productId);

    if (results.isEmpty()) {
      logger.error("Product not found with ID: {}", productId);
      throw new ProductNotFoundException("Product not found with ID: " + productId);
    }

    logger.info("Fetched details for product ID: {}", productId);
    return results;
  }
}