package com.Bcm.Service.Impl.Product;

import com.Bcm.Exception.ProductNotFoundException;
import com.Bcm.Exception.ProductOfferingAlreadyExistsException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.Product.Product;
import com.Bcm.Model.Product.ProductDTO;
import com.Bcm.Repository.Product.ProductRepository;
import com.Bcm.Service.Srvc.ProductSrvc.ProductService;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

  private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
  final ProductRepository productRepository;

  public ProductServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public List<Product> read() {
    logger.info("Reading all products from the repository");
    try {
      List<Product> products = productRepository.findAll();
      logger.info("Successfully fetched {} products", products.size());
      return products;
    } catch (Exception e) {
      logger.error("Error reading products: {}", e.getMessage());
      throw new RuntimeException("An unexpected error occurred while reading Product", e);
    }
  }

  @Override
  public Product getProductById(int productId) throws ProductNotFoundException {
    logger.info("Fetching product by ID: {}", productId);
    return productRepository
        .findById(productId)
        .orElseThrow(
            () -> {
              logger.warn("Product wasn't found with ID: {}", productId);
              return new ProductNotFoundException("Product not found with id: " + productId);
            });
  }

  @Override
  public List<Product> searchByFamilyName(String familyName) {
    logger.info("Searching for products by family name: {}", familyName);
    List<Product> products = productRepository.findByFamilyName(familyName);
    boolean hasLinkedProduct = products.stream().anyMatch(Objects::nonNull);
    if (!hasLinkedProduct) {
      logger.info("No linked products found for family name: {}", familyName);
      return Collections.emptyList();
    }
    logger.info("Found {} products for family name: {}", products.size(), familyName);
    return products;
  }

  @Override
  public Product findById(int productId) {
    logger.info("Finding product by ID: {}", productId);
    try {
      return productRepository
          .findById(productId)
          .orElseThrow(
              () -> {
                logger.warn("Resource not found for product ID: {}", productId);
                return new ResourceNotFoundException("Product with ID " + productId + " not found");
              });
    } catch (ResourceNotFoundException e) {
      logger.error("Product not found: {}", e.getMessage());
      throw new RuntimeException("Product with ID \"" + productId + "\" not found", e);
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
    logger.info("Creating product with name: {}", dto.getName());
    Optional<Product> existingProduct = productRepository.findByName(dto.getName());
    if (existingProduct.isPresent()) {
      logger.error("A product with the name '{}' already exists", dto.getName());
      throw new ProductOfferingAlreadyExistsException(
          "A product with the name '" + dto.getName() + "' already exists.");
    }

    final Product product = getProduct(dto);

    Product savedProduct = productRepository.save(product);
    logger.info("Product created successfully with ID: {}", savedProduct.getProduct_id());
    return savedProduct;
  }

  private Product getProduct(ProductDTO dto) {
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
    return product;
  }

  @Override
  public Product updateProdStockInd(ProductDTO dto, int productId, boolean stockInd) throws ProductNotFoundException {
    logger.info("Updating stock indicator for product ID: {}", productId);
    Product product = getProductById(productId);
    product.setStockInd(stockInd);
    Product updatedProduct = productRepository.save(product);
    logger.info("Stock indicator updated for product ID: {}", productId);
    return updatedProduct;
  }

  @Override
  public boolean existsByName(String name) {
    logger.info("Checking if product exists by name: {}", name);
    boolean exists = productRepository.findByName(name).isPresent();
    logger.info("Product exists by name '{}': {}", name, exists);
    return exists;
  }

  @Override
  public Product updateProductDTO(ProductDTO dto, int productId) throws ProductNotFoundException {
    logger.info("Updating product with ID: {}", productId);
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

    Product updatedProduct = productRepository.save(product);
    logger.info("Product updated successfully with ID: {}", updatedProduct.getProduct_id());
    return updatedProduct;
  }

  @Override
  public Map<String, String> fetchProductResourceDetails(int productId) {
    logger.info("Fetching resource details for product ID: {}", productId);
    List<Object[]> results = productRepository.findProductResourceDetails(productId);

    if (results.isEmpty()) {
      logger.warn("No resource details found for product ID: {}", productId);
      return Map.of("message", "No CFS or Physical Resource is associated with that product.");
    }

    Map<String, String> response = new HashMap<>();
    for (Object[] result : results) {
      String physicalResourceName = (String) result[0];
      String serviceSpecName = (String) result[1];

      response.put("physicalResourceName", physicalResourceName);
      response.put("serviceSpecName", serviceSpecName);
    }
    logger.info("Successfully fetched resource details for product ID: {}", productId);
    return response;
  }

  @Override
  public List<Map<String, Object>> fetchProductDetails(int productId) throws ProductNotFoundException {
    logger.info("Fetching product details for product ID: {}", productId);
    List<Map<String, Object>> results = productRepository.findProductDetails(productId);

    if (results.isEmpty()) {
      logger.warn("Product not found with ID: {}", productId);
      throw new ProductNotFoundException("Product not found with ID: " + productId);
    }
    logger.info("Successfully fetched product details for product ID: {}", productId);
    return results;
  }
}
