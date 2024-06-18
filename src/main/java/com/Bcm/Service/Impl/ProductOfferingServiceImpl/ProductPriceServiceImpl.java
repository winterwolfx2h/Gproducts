package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import com.Bcm.Exception.DatabaseOperationException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import com.Bcm.Model.ProductOfferingABE.ProductPrice;
import com.Bcm.Repository.ProductOfferingRepo.ProductOfferingRepository;
import com.Bcm.Repository.ProductOfferingRepo.ProductPriceRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductPriceService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductPriceServiceImpl implements ProductPriceService {

  final ProductPriceRepository productPriceRepository;
  final ProductOfferingRepository productOfferingRepository;

  @Override
  public ProductPrice create(ProductPrice productPrice) {
    try {
      // Fetch the product by its Product_id
      ProductOffering productOffering =
          productOfferingRepository
              .findById(productPrice.getProduct_id())
              .orElseThrow(() -> new EntityNotFoundException("Product not found"));

      // Update the product's working step
      productOffering.setWorkingStep("Product Price");
      productOfferingRepository.save(productOffering);
      return productPriceRepository.save(productPrice);
    } catch (DataIntegrityViolationException e) {
      throw new DatabaseOperationException("Error creating ProductPrice", e);
    }
  }

  @Override
  @Transactional
  public List<ProductPrice> create(List<ProductPrice> productPrices) {
    List<ProductPrice> createdProductPrices = new ArrayList<>();

    for (ProductPrice productPrice : productPrices) {
      createdProductPrices.add(productPriceRepository.save(productPrice));

      // Fetch the ProductOffering by its Product_id
      int productId = productPrice.getProduct_id();
      ProductOffering productOffering =
          productOfferingRepository
              .findById(productId)
              .orElseThrow(() -> new EntityNotFoundException("ProductOffering not found for Product_id: " + productId));

      // Update the ProductOffering's working step
      productOffering.setWorkingStep("Product Price");
      productOfferingRepository.save(productOffering);
    }

    return createdProductPrices;
  }

  @Override
  public List<ProductPrice> read() {
    try {
      return productPriceRepository.findAll();
    } catch (Exception e) {
      throw new RuntimeException("Error occurred while retrieving ProductPrices");
    }
  }

  @Override
  public ProductPrice update(int productPriceCode, ProductPrice updatedProductPrice) {
    Optional<ProductPrice> existingProductPriceOptional = productPriceRepository.findById(productPriceCode);
    if (existingProductPriceOptional.isPresent()) {
      ProductPrice existingProductPrice = existingProductPriceOptional.get();
      existingProductPrice.setOneTimeAmount(updatedProductPrice.getOneTimeAmount());
      existingProductPrice.setCashPrice(updatedProductPrice.getCashPrice());
      existingProductPrice.setRecuringAmount(updatedProductPrice.getRecuringAmount());
      return productPriceRepository.save(existingProductPrice);
    } else {
      throw new ResourceNotFoundException("ProductPrice with ID " + productPriceCode + " not found.");
    }
  }

  @Override
  public String delete(int productPriceCode) {
    try {
      ProductPrice productPrice = findById(productPriceCode);
      productPriceRepository.deleteById(productPriceCode);
      return ("ProductPrice with ID " + productPriceCode + " was successfully deleted");
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid argument provided for deleting ProductPrice");
    }
  }

  @Override
  public ProductPrice findById(int productPriceCode) {
    try {
      Optional<ProductPrice> optionalProductPrice = productPriceRepository.findById(productPriceCode);
      return optionalProductPrice.orElseThrow(
          () -> new RuntimeException("ProductPrice with ID " + productPriceCode + " not found"));
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid argument provided for finding ProductPrice");
    }
  }

  @Override
  public boolean existsById(int productPriceCode) {
    return productPriceRepository.existsById(productPriceCode);
  }

  @Override
  public List<ProductPrice> searchByPrice(float price) {
    try {
      return productPriceRepository.searchByPrice(price);
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid argument provided for searching ProductPrice by keyword");
    }
  }
}
