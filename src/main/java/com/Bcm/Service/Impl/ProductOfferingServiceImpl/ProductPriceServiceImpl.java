package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import com.Bcm.Exception.DatabaseOperationException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ProductOfferingABE.ProductPrice;
import com.Bcm.Repository.ProductOfferingRepo.ProductPriceRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferingService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductPriceService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductPriceServiceImpl implements ProductPriceService {

  final ProductPriceRepository productPriceRepository;
  final ProductOfferingService productOfferingService;

  @Override
  public ProductPrice create(ProductPrice productPrice) {
    try {

      return productPriceRepository.save(productPrice);
    } catch (DataIntegrityViolationException e) {
      throw new DatabaseOperationException("Error creating ProductPrice", e);
    }
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
