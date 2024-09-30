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
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductOfferingServiceImpl implements ProductOfferingService {

  private static final String NTF = " not found";
  private static final String WS = "Working state";
  private static final String VD = "Validated";
  private static final String SP = "Suspended";
  final ProductOfferingRepository productOfferingRepository;
  final ProductRelationRepository productRelationRepository;
  final ProductOfferRelationRepository productOfferRelationRepository;
  private final ChannelRepository channelRepository;
  private final EntityRepository entityRepository;
  private final ProductPriceGroupRepository productPriceGroupRepository;

  @Override
  public ProductOffering create(ProductOffering productOffering) {
    Optional<ProductOffering> existingProduct = productOfferingRepository.findByName(productOffering.getName());

    if (productOffering.getPoParent_Child() == null || productOffering.getPoParent_Child().isEmpty()) {
      productOffering.setPoParent_Child("PO-Parent");
    }

    if (existingProduct.isPresent()) {
      throw new ProductOfferingAlreadyExistsException("A product offering with the same name already exists.");
    }

    productOffering.setStatus(WS);

    try {
      return productOfferingRepository.save(productOffering);
    } catch (DataIntegrityViolationException e) {
      throw new DatabaseOperationException("Error creating product offering", e);
    } catch (Exception e) {
      throw new RuntimeException("An unexpected error occurred while creating product offering", e);
    }
  }

  @Override
  public ProductOffering createProductOfferingDTO(ProductOfferingDTO dto) {
    Optional<ProductOffering> existingProduct = productOfferingRepository.findByName(dto.getName());
    if (existingProduct.isPresent()) {
      throw new ProductOfferingAlreadyExistsException(
          "A product offering with the name '" + dto.getName() + "' already exists.");
    }

    ProductOffering productOffering = new ProductOffering();
    productOffering.setName(dto.getName());
    productOffering.setPoType(dto.getPoType());
    productOffering.setEffectiveFrom(dto.getEffectiveFrom());
    productOffering.setEffectiveTo(dto.getEffectiveTo());
    productOffering.setDescription(dto.getDescription());
    productOffering.setDetailedDescription(dto.getDetailedDescription());
    productOffering.setFamilyName(dto.getFamilyName());
    productOffering.setSubFamily(dto.getSubFamily());
    productOffering.setSellInd(dto.getSellInd());
    productOffering.setQuantityInd(dto.getQuantityInd());
    productOffering.setStockInd(dto.getStockInd());
    productOffering.setMarkets(dto.getMarkets());
    productOffering.setSubmarkets(dto.getSubmarkets());
    productOffering.setExternalId(dto.getExternalId());
    productOffering.setStatus(WS);

    return productOfferingRepository.save(productOffering);
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
      existingProduct.setPoParent_Child(updatedProductOffering.getPoParent_Child());
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
      } catch (InputException | InvalidInput e) {
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
      throw new ResourceNotFoundException("Product offering with ID " + po_code + NTF);
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
      throw new RuntimeException("Product offering with ID \"" + po_code + NTF, e);
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
      Optional<ProductOffering> optionalProductOffering = productOfferingRepository.findByName(name);
      return optionalProductOffering.orElseThrow(() -> new RuntimeException("Product Offering with ID " + name + NTF));
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
  @Transactional
  public ProductOffering changeProductOfferingStatus(int po_code) {
    try {
      ProductOffering existingProduct =
          productOfferingRepository
              .findById(po_code)
              .orElseThrow(() -> new ResourceNotFoundException("Product Offering with ID \"" + po_code + NTF));

      switch (existingProduct.getStatus()) {
        case WS:
          existingProduct.setStatus(VD);
          break;

        case VD:
          existingProduct.setStatus(SP);
          break;

        case SP:
          throw new ProductOfferingLogicException(
              "Product " + existingProduct.getName() + " isn't fit to be offered for sale anymore.");

        default:
          throw new InvalidInputException("Invalid status transition.");
      }

      return productOfferingRepository.save(existingProduct);

    } catch (ResourceNotFoundException e) {
      throw new RuntimeException("Product Offering with ID \"" + po_code + NTF, e);
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
        case WS:
          existingProduct.setStatus(VD);
          break;
        case VD:
          existingProduct.setStatus(SP);
          break;
        case SP:
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
      existingProductOffering.setSellInd(updatedDTO.getSellInd());
      existingProductOffering.setStockInd(updatedDTO.getStockInd());
      existingProductOffering.setQuantityInd(updatedDTO.getQuantityInd());
      existingProductOffering.setExternalId(updatedDTO.getExternalId());
      existingProductOffering.setGlobal(updatedDTO.isGlobal());

      ProductOffering updatedProductOffering = productOfferingRepository.save(existingProductOffering);
      return convertToDTO(updatedProductOffering);
    } else {
      throw new EntityNotFoundException("ProductOffering with ID " + po_code + NTF);
    }
  }

  private ProductOfferingDTO convertToDTO(ProductOffering productOffering) {
    ProductOfferingDTO dto = new ProductOfferingDTO();
    dto.setProduct_id(productOffering.getProduct_id());
    dto.setName(productOffering.getName());
    dto.setPoType(productOffering.getPoType());
    dto.setEffectiveFrom(productOffering.getEffectiveFrom());
    dto.setEffectiveTo(productOffering.getEffectiveTo());
    dto.setDescription(productOffering.getDescription());
    dto.setDetailedDescription(productOffering.getDetailedDescription());
    dto.setFamilyName(productOffering.getFamilyName());
    dto.setSubFamily(productOffering.getSubFamily());
    dto.setSellInd(productOffering.getSellInd());
    dto.setQuantityInd(productOffering.getQuantityInd());
    dto.setStockInd(productOffering.getStockInd());
    dto.setExternalId(productOffering.getExternalId());
    dto.setGlobal(productOffering.isGlobal());
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
