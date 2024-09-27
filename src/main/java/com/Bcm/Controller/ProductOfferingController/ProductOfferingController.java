package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.BCMDrools.ProductOfferingDrools.ProductOfferingValidationService;
import com.Bcm.Exception.ErrorMessage;
import com.Bcm.Exception.InvalidInputException;
import com.Bcm.Exception.ProductOfferingAlreadyExistsException;
import com.Bcm.Exception.ProductOfferingLogicException;
import com.Bcm.Model.Product.*;
import com.Bcm.Model.ProductOfferingABE.DependentCfsDto;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import com.Bcm.Model.ProductOfferingABE.ProductPriceGroup;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Channel;
import com.Bcm.Model.ProductOfferingABE.SubClasses.EligibilityEntity;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Family.FamilyRequestDTO;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Market.Market;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Market.SubMarket;
import com.Bcm.Model.ServiceABE.CustomerFacingServiceSpec;
import com.Bcm.Repository.Product.ProductRepository;
import com.Bcm.Repository.ProductOfferingRepo.ProductOfferingRepository;
import com.Bcm.Repository.ProductOfferingRepo.ProductPriceGroupRepository;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.ChannelRepository;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.EntityRepository;
import com.Bcm.Repository.ServiceConfigRepo.CustomerFacingServiceSpecRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.*;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.ChannelService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.FamilyService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.MarketService;
import com.Bcm.Service.Srvc.ProductResourceSrvc.LogicalResourceService;
import com.Bcm.Service.Srvc.ProductResourceSrvc.PhysicalResourceService;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.CustomerFacingServiceSpecService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@Tag(name = "Product Offering Controller", description = "All of the Product Offering's methods")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/product-offerings")
public class ProductOfferingController {

  private static final String Success = "Product channels inserted successfully";
  private static final String FAM = "Family with name '";
  private static final String EXT = " does not exist.";
  private static final String PID = "Product Offering with ID ";
  final ProductOfferingService productOfferingService;
  final POAttributesService poAttributesService;
  final ProductOfferRelationService productOfferRelationService;
  final ProductRelationService productRelationService;
  final LogicalResourceService logicalResourceService;
  final PhysicalResourceService physicalResourceService;
  final BusinessProcessService businessProcessService;
  final FamilyService familyService;
  final ChannelService channelService;
  final CustomerFacingServiceSpecService customerFacingServiceSpecService;
  final MarketService marketService;
  final ChannelRepository channelRepository;
  final EntityRepository entityRepository;
  final ProductPriceGroupRepository productPriceGroupRepository;
  final CustomerFacingServiceSpecRepository customerFacingServiceSpecRepository;
  final ProductOfferingValidationService validationService;
  private final ProductOfferingRepository productOfferingRepository;
  private final ProductRepository productRepository;

  @PostMapping("/addProdOff")
  @CacheEvict(value = "productOfferingsCache", allEntries = true)
  public ResponseEntity<?> create(@RequestBody ProductOffering productOffering) {
    try {
      String familyName = productOffering.getFamilyName();
      if (familyName == null || !familyService.findByNameexist(familyName)) {
        return ResponseEntity.badRequest().body(FAM + familyName + EXT);
      }

      if (productOfferingService.existsByName(productOffering.getName())) {
        return ResponseEntity.badRequest().body("A product offering with the same name already exists.");
      }

      ensureRelatedEntitiesExist(productOffering);

      ProductOffering createdProductOffering = productOfferingService.create(productOffering);

      return ResponseEntity.ok(createdProductOffering);

    } catch (DataIntegrityViolationException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Data integrity violation: " + Objects.requireNonNull(e.getRootCause()).getMessage());
    } catch (ProductOfferingAlreadyExistsException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (RuntimeException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("An unexpected error occurred while creating the Product Offering: " + e.getMessage());
    }
  }

  @PostMapping("/AddProdOffDTO")
  @CacheEvict(value = "productOfferingsCache", allEntries = true)
  public ResponseEntity<?> createProductOfferingDTO(@Valid @RequestBody ProductOfferingDTO dto) {
    try {
      String familyName = dto.getFamilyName();
      if (familyName == null || !familyService.findByNameexist(familyName)) {
        return ResponseEntity.badRequest().body(FAM + familyName + EXT);
      }

      String marketName = dto.getMarkets();
      if (marketName == null || !marketService.findByNameexist(marketName)) {
        return ResponseEntity.badRequest().body("Market with name '" + marketName + EXT);
      }

      String submarketName = dto.getSubmarkets();
      if (submarketName == null || !marketService.findBySubMarketNameExist(submarketName)) {
        return ResponseEntity.badRequest().body("Submarket with name '" + submarketName + EXT);
      }

      if (productOfferingService.existsByName(dto.getName())) {
        return ResponseEntity.badRequest().body("A product offering with the same name already exists.");
      }

      ProductOffering createdProductOffering = productOfferingService.createProductOfferingDTO(dto);

      return new ResponseEntity<>(createdProductOffering, HttpStatus.CREATED);

    } catch (ProductOfferingAlreadyExistsException ex) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("An unexpected error occurred while creating the Product Offering. Error: " + e.getMessage());
    }
  }

  private void ensureRelatedEntitiesExist(ProductOffering productOffering) {
    ensureFamilyExists(productOffering.getFamilyName());
  }

  private void ensureFamilyExists(String familyName) {
    if (familyName != null && !familyName.isEmpty() && !familyService.findByNameexist(familyName)) {

      FamilyRequestDTO family = new FamilyRequestDTO();
      family.setName(familyName);
      familyService.createOrUpdateFamily(family);
    }
  }

  @GetMapping("/listProductOfferings")
  public ResponseEntity<List<ProductOffering>> getAllProductOfferings() {
    List<ProductOffering> productOfferings = productOfferingService.read();
    return ResponseEntity.ok(productOfferings);
  }

  @GetMapping("/{po_code}")
  public ResponseEntity<ProductOffering> getProductOfferingById(@PathVariable("po_code") int po_code) {
    ProductOffering productOffering = productOfferingService.findById(po_code);
    return ResponseEntity.ok(productOffering);
  }

  @PutMapping("/{po_code}")
  @CacheEvict(value = "productOfferingsCache", allEntries = true)
  public ResponseEntity<?> updateProductOffering(
      @PathVariable("po_code") int po_code, @RequestBody ProductOffering updatedProductOffering) {
    try {
      ProductOffering existingProductOffering = productOfferingService.findById(po_code);
      if (existingProductOffering == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PID + po_code + " not found.");
      }

      String newName = updatedProductOffering.getName();
      if (!existingProductOffering.getName().equals(newName) && productOfferingService.existsByName(newName)) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body("Product Offering with the name '" + newName + "' already exists.");
      }

      String newFamilyName = updatedProductOffering.getFamilyName();
      if (newFamilyName != null && !familyService.findByNameexist(newFamilyName)) {
        return ResponseEntity.badRequest().body(FAM + newFamilyName + EXT);
      }
      existingProductOffering.setFamilyName(newFamilyName);
      existingProductOffering.setEffectiveFrom(updatedProductOffering.getEffectiveFrom());
      existingProductOffering.setEffectiveTo(updatedProductOffering.getEffectiveTo());
      existingProductOffering.setDescription(updatedProductOffering.getDescription());
      existingProductOffering.setDetailedDescription(updatedProductOffering.getDetailedDescription());
      existingProductOffering.setPoType(updatedProductOffering.getPoType());

      ensureRelatedEntitiesExist(existingProductOffering);

      ProductOffering updatedResult = productOfferingService.update(po_code, existingProductOffering);

      return ResponseEntity.ok(updatedResult);

    } catch (ProductOfferingAlreadyExistsException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("A Product Offering with the same name already exists.");
    } catch (InvalidInputException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("An unexpected error occurred while updating the Product Offering.");
    }
  }

  @DeleteMapping("/{po_code}")
  @CacheEvict(value = "productOfferingsCache", allEntries = true)
  public ResponseEntity<String> deleteProductOffering(@PathVariable("po_code") int po_code) {
    String resultMessage = productOfferingService.delete(po_code);
    return ResponseEntity.ok(resultMessage);
  }

  @GetMapping("/search")
  public ResponseEntity<List<ProductOffering>> searchProductOfferingsByKeyword(@RequestParam("name") String name) {
    List<ProductOffering> searchResults = productOfferingService.searchByKeyword(name);
    return ResponseEntity.ok(searchResults);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
    ErrorMessage message =
        new ErrorMessage(
            HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date(), ex.getMessage(), request.getDescription(false));
    return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  List<String> check(ProductOfferingDTO productOfferingDTO) {
    List<String> errors;
    errors = new ArrayList<>();

    validateName(productOfferingDTO, errors);
    validateEffectiveDates(productOfferingDTO, errors);
    validateSubFamily(productOfferingDTO, errors);
    validateFamilyName(productOfferingDTO, errors);
    validateMarkets(productOfferingDTO, errors);
    validateSubMarkets(productOfferingDTO, errors);

    return errors;
  }

  private void validateName(ProductOfferingDTO productOfferingDTO, List<String> errors) {
    String name = productOfferingDTO.getName();
    if (name == null || name.isEmpty()) {
      errors.add("Name cannot be empty");
    } else if (!name.startsWith("PO-PLAN_")) {
      errors.add("Name must start with 'PO-PLAN_'");
    }
  }

  private void validateEffectiveDates(ProductOfferingDTO productOfferingDTO, List<String> errors) {
    if (productOfferingDTO.getEffectiveFrom() == null || productOfferingDTO.getEffectiveTo() == null) {
      errors.add("Effective from and effective to dates cannot be null");
    } else if (productOfferingDTO.getEffectiveFrom().compareTo(productOfferingDTO.getEffectiveTo()) >= 0) {
      errors.add("Effective from date must be before the effective to date");
    }
  }

  private void validateSubFamily(ProductOfferingDTO productOfferingDTO, List<String> errors) {
    String subFamily = productOfferingDTO.getSubFamily();
    if (subFamily == null || subFamily.isEmpty()) {
      errors.add("Sub Family cannot be empty");
    } else {
      List<String> validSubFamilyNames =
          familyService.readSubFamilies().stream().map(e -> e.getSubFamilyName().toLowerCase()).toList();

      if (!validSubFamilyNames.contains(subFamily.toLowerCase())) {
        errors.add("SubFamily must be one of the following: " + String.join(", ", validSubFamilyNames));
      }
    }
  }

  private void validateFamilyName(ProductOfferingDTO productOfferingDTO, List<String> errors) {
    String familyName = productOfferingDTO.getFamilyName();
    if (familyName == null || familyName.isEmpty()) {
      errors.add("FamilyName cannot be empty");
    } else {
      List<String> validFamilyNames = familyService.read().stream().map(e -> e.getName().toLowerCase()).toList();

      if (!validFamilyNames.contains(familyName.toLowerCase())) {
        errors.add("FamilyName must be one of the following: " + String.join(", ", validFamilyNames));
      }
    }
  }

  private void validateMarkets(ProductOfferingDTO productOfferingDTO, List<String> errors) {
    List<String> validMarketNames = marketService.read().stream().map(Market::getName).toList();

    if (productOfferingDTO.getMarkets() == null || !validMarketNames.contains(productOfferingDTO.getMarkets())) {
      errors.add("Markets must be one of the following: " + String.join(", ", validMarketNames));
    }
  }

  private void validateSubMarkets(ProductOfferingDTO productOfferingDTO, List<String> errors) {
    List<String> validSubMarketNames =
        marketService.readSubMarkets().stream().map(SubMarket::getSubMarketName).toList();

    if (productOfferingDTO.getSubmarkets() == null || productOfferingDTO.getSubmarkets().isEmpty()) {
      errors.add("Submarkets cannot be empty");
    } else if (!validSubMarketNames.contains(productOfferingDTO.getSubmarkets())) {
      errors.add("Sub market must be one of the following: " + String.join(", ", validSubMarketNames));
    }
  }

  List<String> check(ProductOffering productOfferingDTO) {
    List<String> errors = new ArrayList<>();

    validateName(productOfferingDTO, errors);
    validateEffectiveDates(productOfferingDTO, errors);
    validateSubFamily(productOfferingDTO, errors);
    validateFamilyName(productOfferingDTO, errors);
    validateMarkets(productOfferingDTO, errors);
    validateSubMarkets(productOfferingDTO, errors);

    return errors;
  }

  private void validateName(ProductOffering productOfferingDTO, List<String> errors) {
    String name = productOfferingDTO.getName();
    if (name == null || name.isEmpty()) {
      errors.add("Name cannot be empty");
    } else if (!name.startsWith("PO-PLAN_")) {
      errors.add("Name must start with 'PO-PLAN_'");
    }
  }

  private void validateEffectiveDates(ProductOffering productOfferingDTO, List<String> errors) {
    if (productOfferingDTO.getEffectiveFrom() == null || productOfferingDTO.getEffectiveTo() == null) {
      errors.add("Effective from and effective to dates cannot be null");
    } else if (productOfferingDTO.getEffectiveFrom().compareTo(productOfferingDTO.getEffectiveTo()) >= 0) {
      errors.add("Effective from date must be before the effective to date");
    }
  }

  private void validateSubFamily(ProductOffering productOfferingDTO, List<String> errors) {
    String subFamily = productOfferingDTO.getSubFamily();
    if (subFamily == null || subFamily.isEmpty()) {
      errors.add("Sub Family cannot be empty");
    } else {
      List<String> validSubFamilyNames =
          familyService.readSubFamilies().stream().map(e -> e.getSubFamilyName().toLowerCase()).toList();

      if (!validSubFamilyNames.contains(productOfferingDTO.getFamilyName().toLowerCase())) {
        errors.add("SubFamily must be one of the following: " + String.join(", ", validSubFamilyNames));
      }
    }
  }

  private void validateFamilyName(ProductOffering productOfferingDTO, List<String> errors) {
    String familyName = productOfferingDTO.getFamilyName();
    if (familyName == null || familyName.isEmpty()) {
      errors.add("FamilyName cannot be empty");
    } else {
      List<String> validFamilyNames = familyService.read().stream().map(e -> e.getName().toLowerCase()).toList();

      if (!validFamilyNames.contains(familyName.toLowerCase())) {
        errors.add("FamilyName must be one of the following: " + String.join(", ", validFamilyNames));
      }
    }
  }

  private void validateMarkets(ProductOffering productOfferingDTO, List<String> errors) {
    List<String> validMarketNames = marketService.read().stream().map(Market::getName).toList();

    if (productOfferingDTO.getMarkets() == null
        || validMarketNames.stream().noneMatch(e -> productOfferingDTO.getMarkets().contains(e))) {
      errors.add("Markets must be one of the following: " + String.join(", ", validMarketNames));
    }
  }

  private void validateSubMarkets(ProductOffering productOfferingDTO, List<String> errors) {
    List<String> validSubMarketNames =
        marketService.readSubMarkets().stream().map(SubMarket::getSubMarketName).toList();

    if (productOfferingDTO.getSubmarkets() == null || productOfferingDTO.getSubmarkets().isEmpty()) {
      errors.add("Submarkets cannot be empty");
    } else if (validSubMarketNames.stream().noneMatch(e -> productOfferingDTO.getSubmarkets().contains(e))) {
      errors.add("Submarket must be one of the following: " + String.join(", ", validSubMarketNames));
    }
  }

  @PostMapping("/checkError")
  public ResponseEntity<?> checkError(@RequestBody ProductOfferingDTO productOfferingDTO) {

    var errors = check(productOfferingDTO);
    if (!errors.isEmpty()) {
      return ResponseEntity.badRequest().body(errors);
    }
    return ResponseEntity.ok(errors);
  }

  @PutMapping("/changeStatus/{po_code}")
  @CacheEvict(value = "productOfferingsCache", allEntries = true)
  public ResponseEntity<?> changeProductStatus(@PathVariable int po_code) {
    try {
      ProductOffering updatedProduct = productOfferingService.changeProductOfferingStatus(po_code);
      return ResponseEntity.ok(updatedProduct);

    } catch (ProductOfferingLogicException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }
  }

  @PutMapping("/changeStatus/multiple")
  @CacheEvict(value = "productOfferingsCache", allEntries = true)
  public ResponseEntity<?> changeMultipleProductStatuses(@RequestBody List<Integer> poCodes) {
    try {
      List<ProductOffering> updatedProducts = productOfferingService.changeMultipleProductStatuses(poCodes);
      return ResponseEntity.ok(updatedProducts);
    } catch (ProductOfferingLogicException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }
  }

  @GetMapping("/GetDTOs")
  public ResponseEntity<List<ProductOfferingDTO>> getAllProductOfferingDTOs() {
    List<ProductOfferingDTO> dtos = productOfferingService.getAllProductOfferingDTOs();
    return ResponseEntity.ok(dtos);
  }

  @PutMapping("/update-dto/{po_code}")
  @CacheEvict(value = "productOfferingsCache", allEntries = true)
  public ResponseEntity<?> updateProductOfferingDTO(
      @PathVariable int po_code, @RequestBody ProductOfferingDTO updatedDTO) {
    try {
      ProductOffering existingProductOffering = productOfferingService.findById(po_code);
      if (existingProductOffering == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PID + po_code + " not found.");
      }
      String newName = updatedDTO.getName();
      if (!existingProductOffering.getName().equals(newName) && productOfferingService.existsByName(newName)) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body("Product Offering with the name '" + newName + "' already exists.");
      }

      String newFamilyName = updatedDTO.getFamilyName();
      if (newFamilyName != null && !familyService.findByNameexist(newFamilyName)) {
        return ResponseEntity.badRequest().body(FAM + newFamilyName + EXT);
      }
      String marketName = updatedDTO.getMarkets();
      if (marketName == null || !marketService.findByNameexist(marketName)) {
        return ResponseEntity.badRequest().body("Market with name '" + marketName + EXT);
      }
      String submarketName = updatedDTO.getSubmarkets();
      if (submarketName == null || !marketService.findBySubMarketNameExist(submarketName)) {
        return ResponseEntity.badRequest().body("Submarket with name '" + submarketName + EXT);
      }
      existingProductOffering.setFamilyName(newFamilyName);
      ProductOfferingDTO updatedProductOfferingDTO =
          productOfferingService.updateProductOfferingDTO(po_code, updatedDTO);
      return ResponseEntity.ok(updatedProductOfferingDTO);

    } catch (ProductOfferingAlreadyExistsException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("A Product Offering with the same name already exists.");
    } catch (InvalidInputException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("An unexpected error occurred while updating the Product Offering.");
    }
  }

  @PostMapping("/insertProductChannels")
  public ResponseEntity<String> insertProductChannels(@RequestBody List<ProductChannelDTO> productChannelDTOs) {
    if (productChannelDTOs.isEmpty()) {
      throw new IllegalArgumentException("At least one productChannelDTO must be provided");
    }

    Integer productId = productChannelDTOs.get(0).getProductId();
    ProductOffering productOffering = productOfferingService.findById(productId);
    if (productOffering == null) {
      throw new IllegalArgumentException(PID + productId + EXT);
    }

    for (ProductChannelDTO dto : productChannelDTOs) {
      Channel channel =
          channelRepository
              .findById(dto.getChannelCode())
              .orElseThrow(() -> new IllegalArgumentException("Channel with code " + dto.getChannelCode() + EXT));
      productOffering.getChannelCode().add(channel);
    }

    productOfferingRepository.save(productOffering);
    return ResponseEntity.ok(Success);
  }

  @PostMapping("/insertProductEntity")
  public ResponseEntity<String> insertProductEntity(@RequestBody List<ProductEntityDTO> productEntityDTO) {
    if (productEntityDTO.isEmpty()) {
      throw new IllegalArgumentException("At least one productEntityDTO must be provided");
    }

    Integer productId = productEntityDTO.get(0).getProductId();
    ProductOffering productOffering = productOfferingService.findById(productId);
    if (productOffering == null) {
      throw new IllegalArgumentException(PID + productId + EXT);
    }

    for (ProductEntityDTO dto : productEntityDTO) {
      EligibilityEntity entity =
          entityRepository
              .findById(dto.getEntityCode())
              .orElseThrow(() -> new IllegalArgumentException("Entity with code " + dto.getEntityCode() + EXT));
      productOffering.getEntityCode().add(entity);
    }

    productOfferingRepository.save(productOffering);
    return ResponseEntity.ok("Product entities inserted successfully");
  }

  @PostMapping("/insertProductGroup")
  public ResponseEntity<String> insertProductGroup(@RequestBody List<ProductGroupDto> productGroupDtos) {
    if (productGroupDtos.isEmpty()) {
      throw new IllegalArgumentException("At least one productGroupDtos must be provided");
    }

    Integer productId = productGroupDtos.get(0).getProductId();
    ProductOffering productOffering = productOfferingService.findById(productId);
    if (productOffering == null) {
      throw new IllegalArgumentException(PID + productId + EXT);
    }

    for (ProductGroupDto dto : productGroupDtos) {
      ProductPriceGroup priceGroup =
          productPriceGroupRepository
              .findById(dto.getProductPriceGroupCode())
              .orElseThrow(
                  () ->
                      new IllegalArgumentException(
                          "Product Price Group with code " + dto.getProductPriceGroupCode() + EXT));
      productOffering.getProductPriceGroups().add(priceGroup);
    }

    productOfferingRepository.save(productOffering);
    return ResponseEntity.ok("Product groups inserted successfully");
  }

  @PostMapping("/updateProductChannels")
  public ResponseEntity<String> updateProductChannels(@RequestBody List<ProductChannelDTO> productChannelDTOs) {
    if (productChannelDTOs.isEmpty()) {
      throw new IllegalArgumentException("At least one productChannelDTO must be provided");
    }

    int productId = productChannelDTOs.get(0).getProductId();
    ProductOffering productOffering = productOfferingService.findById(productId);
    if (productOffering == null) {
      throw new IllegalArgumentException(PID + productId + EXT);
    }

    for (ProductChannelDTO dto : productChannelDTOs) {
      Channel channel =
          channelRepository
              .findById(dto.getChannelCode())
              .orElseThrow(() -> new IllegalArgumentException("Channel with code " + dto.getChannelCode() + EXT));
      if (!productOffering.getChannelCode().contains(channel)) {
        productOffering.getChannelCode().add(channel);
      }
    }

    productOfferingRepository.save(productOffering);
    return ResponseEntity.ok("Product channels updated successfully");
  }

  @PostMapping("/updateProductEntity")
  public ResponseEntity<String> updateProductEntity(@RequestBody List<ProductEntityDTO> productEntityDTOs) {
    if (productEntityDTOs.isEmpty()) {
      throw new IllegalArgumentException("At least one productEntityDTO must be provided");
    }

    int productId = productEntityDTOs.get(0).getProductId();
    ProductOffering productOffering = productOfferingService.findById(productId);
    if (productOffering == null) {
      throw new IllegalArgumentException(PID + productId + EXT);
    }

    for (ProductEntityDTO dto : productEntityDTOs) {
      EligibilityEntity entity =
          entityRepository
              .findById(dto.getEntityCode())
              .orElseThrow(() -> new IllegalArgumentException("Entity with code " + dto.getEntityCode() + EXT));
      if (!productOffering.getEntityCode().contains(entity)) {
        productOffering.getEntityCode().add(entity);
      }
    }

    productOfferingRepository.save(productOffering);
    return ResponseEntity.ok("Product entities updated successfully");
  }

  @PostMapping("/updateProductGroup")
  public ResponseEntity<String> updateProductGroup(@RequestBody List<ProductGroupDto> productGroupDtos) {
    if (productGroupDtos.isEmpty()) {
      throw new IllegalArgumentException("At least one productGroupDtos must be provided");
    }

    int productId = productGroupDtos.get(0).getProductId();
    ProductOffering productOffering = productOfferingService.findById(productId);
    if (productOffering == null) {
      throw new IllegalArgumentException(PID + productId + EXT);
    }

    for (ProductGroupDto dto : productGroupDtos) {
      ProductPriceGroup priceGroup =
          productPriceGroupRepository
              .findById(dto.getProductPriceGroupCode())
              .orElseThrow(
                  () ->
                      new IllegalArgumentException(
                          "Product Price Group with code " + dto.getProductPriceGroupCode() + EXT));
      if (!productOffering.getProductPriceGroups().contains(priceGroup)) {
        productOffering.getProductPriceGroups().add(priceGroup);
      }
    }

    productOfferingRepository.save(productOffering);
    return ResponseEntity.ok("Product groups updated successfully");
  }

  @PostMapping("/insertDependentCfs")
  public ResponseEntity<String> insertDependentCfs(@RequestBody List<DependentCfsDto> dependentCfsDtos) {
    if (dependentCfsDtos.isEmpty()) {
      throw new IllegalArgumentException("At least one dependentCfsDtos must be provided");
    }

    Product productOffering =
        productRepository
            .findById(dependentCfsDtos.get(0).getProductId())
            .orElseThrow(() -> new IllegalArgumentException("Product offering does not exist"));

    for (DependentCfsDto dto : dependentCfsDtos) {
      CustomerFacingServiceSpec dependentCfs =
          customerFacingServiceSpecRepository
              .findById(dto.getDependentCfs())
              .orElseThrow(() -> new IllegalArgumentException("Dependent CFS with id " + dto.getDependentCfs() + EXT));
      productOffering.getServiceId().add(dependentCfs);
    }

    productRepository.save(productOffering);
    return ResponseEntity.ok("Dependent CFS inserted successfully");
  }
}
