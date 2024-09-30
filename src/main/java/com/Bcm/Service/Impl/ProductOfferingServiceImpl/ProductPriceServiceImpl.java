package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import com.Bcm.Exception.DatabaseOperationException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.Product.Product;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import com.Bcm.Model.ProductOfferingABE.ProductPrice;
import com.Bcm.Model.ProductOfferingABE.Tax;
import com.Bcm.Repository.Product.ProductRepository;
import com.Bcm.Repository.ProductOfferingRepo.ProductOfferingRepository;
import com.Bcm.Repository.ProductOfferingRepo.ProductPriceRepository;
import com.Bcm.Repository.ProductOfferingRepo.TaxRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@RequiredArgsConstructor
@Service
public class ProductPriceServiceImpl implements ProductPriceService {

  private static final String PPID = "ProductPrice with ID ";
  final ProductPriceRepository productPriceRepository;
  final ProductOfferingRepository productOfferingRepository;
  final ProductRepository productRepository;
  final TaxRepository taxRepository;

  @Override
  public ProductPrice create(ProductPrice productPrice) {
    try {
      Product product =
          productRepository
              .findById(productPrice.getProduct_id())
              .orElseThrow(() -> new EntityNotFoundException("Product not found"));
      productRepository.save(product);
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
      if (!productPriceRepository.existsById(productPrice.getProductPriceCode())) {
        createdProductPrices.add(productPriceRepository.save(productPrice));

        int productId = productPrice.getProduct_id();
        ProductOffering productOffering =
            productOfferingRepository
                .findById(productId)
                .orElseThrow(
                    () -> new EntityNotFoundException("ProductOffering not found for Product_id: " + productId));

        productOffering.setWorkingStep("Product Price");
        productOfferingRepository.save(productOffering);
      }
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
      throw new ResourceNotFoundException(PPID + productPriceCode + " not found.");
    }
  }

  @Override
  public String delete(int productPriceCode) {
    try {
      productPriceRepository.deleteById(productPriceCode);
      return (PPID + productPriceCode + " was successfully deleted");
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid argument provided for deleting ProductPrice");
    }
  }

  @Override
  public ProductPrice findById(int productPriceCode) {
    try {
      Optional<ProductPrice> optionalProductPrice = productPriceRepository.findById(productPriceCode);
      return optionalProductPrice.orElseThrow(() -> new RuntimeException(PPID + productPriceCode + " not found"));
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

  @Override
  public List<ProductPrice> findByProductId(int productId) {
    return productPriceRepository.findByProductId(productId);
  }

  @Override
  public Map<String, Object> calculatePriceWithTax(float originalPrice, List<Integer> taxCodes) {
    List<Tax> applicableTaxes = taxRepository.findAllById(taxCodes);

    if (applicableTaxes.isEmpty()) {
      throw new RuntimeException("No applicable taxes found for provided tax codes.");
    }

    List<Map<String, Object>> ttcList = new ArrayList<>();
    float ht = originalPrice;

    for (Tax tax : applicableTaxes) {
      float taxValue = (originalPrice * tax.getValue()) / 100;
      float ttc = originalPrice + taxValue;

      Map<String, Object> ttcDetails = new HashMap<>();
      ttcDetails.put("taxCode", tax.getTaxCode());
      ttcDetails.put("taxValue", taxValue);
      ttcDetails.put("TTC", ttc);
      ttcDetails.put("TaxRate", tax.getValue());

      ttcList.add(ttcDetails);
    }

    Map<String, Object> result = new HashMap<>();
    result.put("HT", ht);
    result.put("TTCs", ttcList);

    return result;
  }
}
