package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import com.Bcm.Exception.*;
import com.Bcm.Model.Product.ProductOfferingDTO;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import com.Bcm.Model.ProductOfferingABE.ProductPriceGroup;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Channel;
import com.Bcm.Model.ProductOfferingABE.SubClasses.EligibilityEntity;
import com.Bcm.Repository.ProductOfferingRepo.ProductOfferRelationRepository;
import com.Bcm.Repository.ProductOfferingRepo.ProductOfferingRepository;
import com.Bcm.Repository.ProductOfferingRepo.ProductPriceGroupRepository;
import com.Bcm.Repository.ProductOfferingRepo.ProductRelationRepository;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.ChannelRepository;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.EntityRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.EligibilityService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferingService;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductOfferingServiceImpl implements ProductOfferingService {

  final ProductOfferingRepository productOfferingRepository;
  final ProductRelationRepository productRelationRepository;
  final EligibilityService eligibilityService;
  final ProductOfferRelationRepository productOfferRelationRepository;
  private final ChannelRepository channelRepository;
  private final EntityRepository entityRepository;
  private final ProductPriceGroupRepository productPriceGroupRepository;

  @Override
  public ProductOffering create(ProductOffering productOffering) {
    Optional<ProductOffering> existingProduct = productOfferingRepository.findByName(productOffering.getName());
    /*

    if (productOffering.getEligibility() == null || productOffering.getEligibility().isEmpty()) {
        throw new InvalidInputException("Eligibilities list cannot be empty.");
    }

    List<Integer> eligibilities = productOffering.getEligibility();
    for (Integer eligibilityId : eligibilities) {
        if (!eligibilityService.findByIdExists(eligibilityId)) {
            throw new InvalidInputException("Eligibility with ID '" + eligibilityId + "' does not exist.");
        }
    }

     */

    if (productOffering.getPoParent_Child() == null || productOffering.getPoParent_Child().isEmpty()) {
      productOffering.setPoParent_Child("PO-Parent");
    }

    if (existingProduct.isPresent()) {
      throw new ProductOfferingAlreadyExistsException("A product offering with the same name already exists.");
    }

    productOffering.setStatus("Working state");

    try {
      return productOfferingRepository.save(productOffering);
    } catch (DataIntegrityViolationException e) {
      throw new DatabaseOperationException("Error creating product offering", e);
    } catch (Exception e) {
      throw new RuntimeException("An unexpected error occurred while creating product offering", e);
    }
  }

  /*@Override
  public ProductOffering recreate(int Product_id,ProductOffering productOffering) {
      Optional<ProductOffering> existingProduct = productOfferingRepository.findByName(productOffering.getName());

      if (productOffering.getEligibility() == null || productOffering.getEligibility().isEmpty()) {
          throw new InvalidInputException("Eligibilities list cannot be empty.");
      }

      List<Integer> eligibilities = productOffering.getEligibility();
      for (Integer eligibilityId : eligibilities) {
          if (!eligibilityService.findByIdExists(eligibilityId)) {
              throw new InvalidInputException("Eligibility with ID '" + eligibilityId + "' does not exist.");
          }
      }

      if (productOffering.getPoParent_Child() == null || productOffering.getPoParent_Child().isEmpty()) {
          productOffering.setPoParent_Child("PO-Parent");
      }

      ProductOffering productToSave;
      if (existingProduct.isPresent()) {
          productToSave = existingProduct.get();
          // Update fields with new values
          updateProductOfferingFields(productToSave, productOffering);
      } else {
          // Product does not exist, set status for new creation
          productOffering.setStatus("Working state");
          productToSave = productOffering;
      }

      try {
          return productOfferingRepository.save(productToSave);
      } catch (DataIntegrityViolationException e) {
          throw new DatabaseOperationException("Error creating or updating product offering", e);
      } catch (Exception e) {
          throw new RuntimeException("An unexpected error occurred while processing the request", e);
      }
  }

  private void updateProductOfferingFields(ProductOffering target, ProductOffering source) {
      target.setPoType(source.getPoType());
      target.setExternalId(source.getExternalId());
      target.setParent(source.getParent());
      target.setWorkingStep(source.getWorkingStep());
      target.setSellIndicator(source.getSellIndicator());
      target.setQuantityIndicator(source.getQuantityIndicator());
      target.setCategory(source.getCategory());
      target.setBS_externalId(source.getBS_externalId());
      target.setCS_externalId(source.getCS_externalId());
      target.setBusinessProcess(source.getBusinessProcess());
      target.setEligibility(source.getEligibility());
      target.setPoParent_Child(source.getPoParent_Child());
      target.setLogicalResource(source.getLogicalResource());
      target.setPhysicalResource(source.getPhysicalResource());
      target.setCustomerFacingServiceSpec(source.getCustomerFacingServiceSpec());
      target.setMarkets(source.getMarkets());
      target.setSubmarkets(source.getSubmarkets());
  }*/

  @Override
  public ProductOffering createProductOfferingDTO(ProductOfferingDTO dto) {
    // Check if an existing product offering with the same name already exists
    Optional<ProductOffering> existingProduct = productOfferingRepository.findByName(dto.getName());
    if (existingProduct.isPresent()) {
      throw new ProductOfferingAlreadyExistsException(
          "A product offering with the name '" + dto.getName() + "' already exists.");
    }

    // Create new ProductOffering entity from DTO
    ProductOffering productOffering = new ProductOffering();
    productOffering.setName(dto.getName());
    productOffering.setPoType(dto.getPoType());
    productOffering.setEffectiveFrom(dto.getEffectiveFrom());
    productOffering.setEffectiveTo(dto.getEffectiveTo());
    productOffering.setDescription(dto.getDescription());
    productOffering.setDetailedDescription(dto.getDetailedDescription());
    productOffering.setFamilyName(dto.getFamilyName());
    productOffering.setSubFamily(dto.getSubFamily());
    productOffering.setSellIndicator(dto.getSellIndicator());
    productOffering.setQuantityIndicator(dto.getQuantityIndicator());
    productOffering.setMarkets(dto.getMarkets());
    productOffering.setSubmarkets(dto.getSubmarkets());
    productOffering.setStatus("Working state");

    // Save the new ProductOffering
    ProductOffering savedProductOffering = productOfferingRepository.save(productOffering);

    return savedProductOffering;
  }

  @Override
  public List<ProductOffering> read() {
    try {
      return productOfferingRepository.findAll();
    } catch (Exception e) {
      throw new RuntimeException("An unexpected error occurred while reading product offerings", e);
    }
  }

  @Override
  public ProductOffering update(int po_code, ProductOffering updatedProductOffering) {
    Optional<ProductOffering> existingProductOptional = productOfferingRepository.findById(po_code);

    if (existingProductOptional.isPresent()) {
      ProductOffering existingProduct = existingProductOptional.get();
      existingProduct.setName(updatedProductOffering.getName());
      existingProduct.setParent(updatedProductOffering.getParent());
      /*
      existingProduct.setPoAttributes(updatedProductOffering.getPoAttributes());
      existingProduct.setProductRelation(updatedProductOffering.getProductRelation());
      existingProduct.setPhysicalResource(updatedProductOffering.getPhysicalResource());
      existingProduct.setBusinessProcess(updatedProductOffering.getBusinessProcess());

       */
      existingProduct.setPoParent_Child(updatedProductOffering.getPoParent_Child());
      /*
      existingProduct.setEligibility(updatedProductOffering.getEligibility());

       */
      existingProduct.setWorkingStep(updatedProductOffering.getWorkingStep());

      try {
        if (updatedProductOffering.getName() == null || updatedProductOffering.getDescription() == null) {
          throw new InputException("Name and description cannot be null");
        }

        return productOfferingRepository.save(existingProduct);
      } catch (DataIntegrityViolationException e) {
        throw new DatabaseOperationException("Error updating product offering with ID: " + po_code, e);
      } catch (ObjectOptimisticLockingFailureException e) {
        throw new ConcurrentModificationException(
            "Another user has modified the product offering with ID: " + po_code + ". Please try again.");
      } catch (InputException e) {
        throw e;
      } catch (InvalidInput e) {
        throw e;
      } catch (Exception e) {
        throw new RuntimeException(
            "An unexpected error occurred while updating product offering with ID: " + po_code, e);
      }
    } else {
      throw new ResourceNotFoundException("Could not find product offering with ID: " + po_code);
    }
  }

  @Override
  public String delete(int po_code) {
    if (!productOfferingRepository.existsById(po_code)) {
      throw new ResourceNotFoundException("Product offering with ID " + po_code + " not found");
    }

    try {
      productOfferingRepository.deleteById(po_code);
      return "Product offering with ID " + po_code + " was successfully deleted";
    } catch (Exception e) {
      throw new RuntimeException("An unexpected error occurred while deleting product offering with ID: " + po_code, e);
    }
  }

  @Override
  public ProductOffering findById(int po_code) {
    try {
      return productOfferingRepository
          .findById(po_code)
          .orElseThrow(
              () ->
                  new ResourceNotFoundException(
                      " An unexpected error occurred while finding product offering with ID:" + po_code));
    } catch (ResourceNotFoundException e) {
      throw new RuntimeException("Product offering with ID \"" + po_code + "\" not found", e);
    }
  }

  @Override
  public List<ProductOffering> searchByKeyword(String name) {
    try {
      return productOfferingRepository.searchByKeyword(name);
    } catch (Exception e) {
      throw new RuntimeException(
          "An unexpected error occurred while searching for product offerings by keyword: " + name, e);
    }
  }

  @Override
  public ProductOffering findByName(String name) {
    try {
      Optional<ProductOffering> optionalProductOffering = productOfferingRepository.findByname(name);
      return optionalProductOffering.orElseThrow(
          () -> new RuntimeException("Product Offering with ID " + name + " not found"));
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid argument provided for finding Product Offering");
    }
  }

  @Override
  public List<ProductOffering> findByPoType(String poType) {
    try {
      return productOfferingRepository.findByPoType(poType);
    } catch (Exception e) {
      throw new RuntimeException(
          "An unexpected error occurred while searching for product offerings by poType: " + poType, e);
    }
  }

  @Override
  public boolean existsById(int po_code) {
    return productOfferingRepository.existsById(po_code);
  }

  @Override
  public List<ProductOffering> findByFamilyName(String familyName) {
    try {
      return productOfferingRepository.findByFamilyName(familyName);
    } catch (Exception e) {
      throw new RuntimeException(
          "An unexpected error occurred while searching for product offerings by family name: " + familyName, e);
    }
  }

  @Override
  public ProductOffering changeProductOfferingStatus(int po_code) {
    try {
      ProductOffering existingProduct = findById(po_code);

      switch (existingProduct.getStatus()) {
        case "Working state":
          existingProduct.setStatus("Validated");
          break;

        case "Validated":
          existingProduct.setStatus("Suspended");
          break;

        case "Suspended":
          throw new ProductOfferingLogicException(
              "Product " + existingProduct.getName() + " isn't fit to be offered for sale anymore.");

        default:
          throw new InvalidInputException("Invalid status transition.");
      }

      return productOfferingRepository.save(existingProduct);

    } catch (ResourceNotFoundException e) {
      throw new RuntimeException("Product Offering with ID \"" + po_code + "\" not found", e);
    } catch (ProductOfferingLogicException e) {
      throw new ProductOfferingLogicException(e.getMessage());
    } catch (Exception e) {
      throw new RuntimeException("An unexpected error occurred while changing Product status", e);
    }
  }

  @Override
  public List<ProductOffering> changeMultipleProductStatuses(List<Integer> poCodes) {
    List<ProductOffering> updatedProducts = new ArrayList<>();

    for (int po_code : poCodes) {
      ProductOffering existingProduct = findById(po_code);

      switch (existingProduct.getStatus()) {
        case "Working state":
          existingProduct.setStatus("Validated");
          break;
        case "Validated":
          existingProduct.setStatus("Suspended");
          break;
        case "Suspended":
          throw new ProductOfferingLogicException(
              "Product " + existingProduct.getName() + " isn't fit to be offered for sale anymore.");
        default:
          throw new InvalidInputException("Invalid status transition.");
      }

      productOfferingRepository.save(existingProduct);
      updatedProducts.add(existingProduct);
    }

    return updatedProducts;
  }

  @Override
  public boolean existsByName(String name) {
    return productOfferingRepository.findByName(name).isPresent();
  }

  @Override
  public List<ProductOfferingDTO> getAllProductOfferingDTOs() {
    List<ProductOffering> productOfferings = productOfferingRepository.findByPoType("PO-Plan");
    List<ProductOfferingDTO> dtos = new ArrayList<>();

    for (ProductOffering productOffering : productOfferings) {
      ProductOfferingDTO dto = convertToDTO(productOffering);
      dtos.add(dto);
    }
    return dtos;
  }

  @Override
  public ProductOfferingDTO updateProductOfferingDTO(int po_code, ProductOfferingDTO updatedDTO) {
    Optional<ProductOffering> optionalProductOffering = productOfferingRepository.findById(po_code);
    if (optionalProductOffering.isPresent()) {
      ProductOffering existingProductOffering = optionalProductOffering.get();
      existingProductOffering.setName(updatedDTO.getName());
      existingProductOffering.setPoType(updatedDTO.getPoType());
      existingProductOffering.setEffectiveFrom(updatedDTO.getEffectiveFrom());
      existingProductOffering.setEffectiveTo(updatedDTO.getEffectiveTo());
      existingProductOffering.setDescription(updatedDTO.getDescription());
      existingProductOffering.setDetailedDescription(updatedDTO.getDetailedDescription());
      existingProductOffering.setFamilyName(updatedDTO.getFamilyName());
      existingProductOffering.setSubFamily(updatedDTO.getSubFamily());
      existingProductOffering.setSellIndicator(updatedDTO.getSellIndicator());
      existingProductOffering.setQuantityIndicator(updatedDTO.getQuantityIndicator());

      ProductOffering updatedProductOffering = productOfferingRepository.save(existingProductOffering);
      return convertToDTO(updatedProductOffering);
    } else {
      throw new EntityNotFoundException("ProductOffering with ID " + po_code + " not found");
    }
  }

  private ProductOfferingDTO convertToDTO(ProductOffering productOffering) {
    ProductOfferingDTO dto = new ProductOfferingDTO();
    dto.setProduct_id(productOffering.getProduct_id()); // Ensure the ID is set
    dto.setName(productOffering.getName());
    dto.setPoType(productOffering.getPoType());
    dto.setEffectiveFrom(productOffering.getEffectiveFrom());
    dto.setEffectiveTo(productOffering.getEffectiveTo());
    dto.setDescription(productOffering.getDescription());
    dto.setDetailedDescription(productOffering.getDetailedDescription());
    dto.setFamilyName(productOffering.getFamilyName());
    dto.setSubFamily(productOffering.getSubFamily());
    dto.setSellIndicator(productOffering.getSellIndicator());
    dto.setQuantityIndicator(productOffering.getQuantityIndicator());
    dto.setStatus(productOffering.getStatus());

    String marketsString = String.join(",", productOffering.getMarkets());
    dto.setMarkets(marketsString);

    String submarketsString = String.join(",", productOffering.getSubmarkets());
    dto.setSubmarkets(submarketsString);

    return dto;
  }

  @Override
  public ProductOffering updatePODTORelations(
      ProductOfferingDTO productOfferingDTO, int Product_id, int channelCode, int entityCode, int productPriceCode)
      throws ProductOfferingNotFoundException {
    ProductOffering productOffering = findById(Product_id);
    if (productOffering == null) {
      throw new ProductOfferingNotFoundException("Product Offering not found");
    }

    Channel channel =
        channelRepository
            .findById(channelCode)
            .orElseThrow(() -> new ProductOfferingNotFoundException("Channel not found"));

    EligibilityEntity eligibilityEntity =
        entityRepository
            .findById(entityCode)
            .orElseThrow(() -> new ProductOfferingNotFoundException("Entity not found"));

    ProductPriceGroup productPriceGroup =
        productPriceGroupRepository
            .findById(productPriceCode)
            .orElseThrow(() -> new ProductOfferingNotFoundException("Product Price Group not found"));

    convertToDTO(productOffering);

    productOffering.getChannelCode().clear();
    productOffering.getChannelCode().add(channel);

    productOffering.getEntityCode().clear();
    productOffering.getEntityCode().add(eligibilityEntity);

    productOffering.getProductPriceGroups().clear();
    productOffering.getProductPriceGroups().add(productPriceGroup);

    return productOfferingRepository.save(productOffering);
  }
}
