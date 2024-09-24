package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.BCMDrools.ProductOfferingDrools.ProductOfferingValidationService;
import com.Bcm.Exception.ErrorMessage;
import com.Bcm.Exception.InvalidInputException;
import com.Bcm.Exception.ProductOfferingAlreadyExistsException;
import com.Bcm.Exception.ProductOfferingLogicException;
import com.Bcm.Model.Product.ProductChannelDTO;
import com.Bcm.Model.Product.ProductEntityDTO;
import com.Bcm.Model.Product.ProductGroupDto;
import com.Bcm.Model.Product.ProductOfferingDTO;
import com.Bcm.Model.ProductOfferingABE.DependentCfsDto;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Family.FamilyRequestDTO;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Market.Market;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Market.SubMarket;
import com.Bcm.Repository.ProductOfferingRepo.ProductOfferRelationRepository;
import com.Bcm.Repository.ProductOfferingRepo.ProductOfferingRepository;
import com.Bcm.Service.Impl.ProductOfferingServiceImpl.ProductOfferingServiceImpl;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.*;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.ChannelService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.FamilyService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.MarketService;
import com.Bcm.Service.Srvc.ProductResourceSrvc.LogicalResourceService;
import com.Bcm.Service.Srvc.ProductResourceSrvc.PhysicalResourceService;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.CustomerFacingServiceSpecService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Tag(name = "Product Offering Controller", description = "All of the Product Offering's methods")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/product-offerings")
public class ProductOfferingController {

  private static final String Success = "Product channels inserted successfully";
  private static final String FAM = "Family with name '";
  private static final String EXT = "' does not exist.";
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
  final ProductOfferingValidationService validationService;
  final JdbcTemplate base;
  private final ProductOfferingRepository productOfferingRepository;
  private final ProductOfferRelationRepository productOfferRelationRepository;
  private final ProductOfferingServiceImpl productOfferingServiceImpl;

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
          .body("Data integrity violation: " + e.getRootCause().getMessage());
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
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Offering with ID " + po_code + " not found.");
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
    List<String> errors = new ArrayList<>();

    if (productOfferingDTO.getName() == null || productOfferingDTO.getName().isEmpty()) {
      errors.add("Name cannot be empty");
    } else {
      if (!productOfferingDTO.getName().startsWith("PO-PLAN_")) {
        errors.add("Name must start with 'PO-PLAN_'");
      }
    }

    if (productOfferingDTO.getEffectiveFrom() == null
        || productOfferingDTO.getName().isEmpty()
        || productOfferingDTO.getEffectiveTo() == null) {
      errors.add("Effective from and effective to dates cannot be null");
    } else {
      if (productOfferingDTO.getEffectiveFrom().compareTo(productOfferingDTO.getEffectiveTo()) >= 0) {
        errors.add("Effective from date must be before the effective to date");
      }
    }

    if (productOfferingDTO.getSubFamily() == null || productOfferingDTO.getSubFamily().isEmpty()) {
      errors.add("Sub Family cannot be empty");
    } else {

      var validSubFamilyNames = familyService.readSubFamilies();
      List<String> subFamilyNames = validSubFamilyNames.stream().map(e -> e.getSubFamilyName().toLowerCase()).toList();
      if (subFamilyNames.contains(productOfferingDTO.getFamilyName().toLowerCase())) {
        errors.add("subFamily must be one of the following: " + String.join(", ", subFamilyNames));
      }
    }

    var validFamilyNames = familyService.read();
    List<String> familyNames = validFamilyNames.stream().map(e -> e.getName().toLowerCase()).toList();
    if (productOfferingDTO.getFamilyName() == null || productOfferingDTO.getFamilyName().isEmpty()) {
      errors.add("FamilyName cannot be empty");
    } else {

      if (!familyNames.contains(productOfferingDTO.getFamilyName().toLowerCase())) {
        errors.add("FamilyName must be one of the following: " + String.join(", ", familyNames));
      }
    }

    List<Market> validMarket = marketService.read();
    List<String> markets = new ArrayList<>();
    for (Market market : validMarket) {
      markets.add(market.getName());
    }

    if (!markets.contains(productOfferingDTO.getMarkets())) {
      errors.add("markets must be one of the following: " + String.join(", ", markets));
    }

    List<SubMarket> validSubMarket = marketService.readSubMarkets();
    List<String> submarkets = new ArrayList<>();

    for (SubMarket subMarket : validSubMarket) {
      submarkets.add(subMarket.getSubMarketName());
    }
    if (productOfferingDTO.getSubmarkets() == null || productOfferingDTO.getSubmarkets().isEmpty()) {
      errors.add("submarkets cannot be empty");
    } else {

      if (!submarkets.contains(productOfferingDTO.getSubmarkets())) {
        errors.add("sub market must be one of the following: " + String.join(", ", submarkets));
      }
    }
    return errors;
  }

  List<String> check(ProductOffering productOfferingDTO) {
    List<String> errors = new ArrayList<>();

    if (productOfferingDTO.getName() == null || productOfferingDTO.getName().isEmpty()) {
      errors.add("Name cannot be empty");
    } else {
      if (!productOfferingDTO.getName().startsWith("PO-PLAN_")) {
        errors.add("Name must start with 'PO-PLAN_'");
      }
    }

    if (productOfferingDTO.getEffectiveFrom() == null
        || productOfferingDTO.getName().isEmpty()
        || productOfferingDTO.getEffectiveTo() == null) {
      errors.add("Effective from and effective to dates cannot be null");
    } else {
      if (productOfferingDTO.getEffectiveFrom().compareTo(productOfferingDTO.getEffectiveTo()) >= 0) {
        errors.add("Effective from date must be before the effective to date");
      }
    }

    if (productOfferingDTO.getSubFamily() == null || productOfferingDTO.getSubFamily().isEmpty()) {
      errors.add("Sub Family cannot be empty");
    } else {

      var validSubFamilyNames = familyService.readSubFamilies();
      List<String> subFamilyNames = validSubFamilyNames.stream().map(e -> e.getSubFamilyName().toLowerCase()).toList();
      if (subFamilyNames.contains(productOfferingDTO.getFamilyName().toLowerCase())) {
        errors.add("subFamily must be one of the following: " + String.join(", ", subFamilyNames));
      }
    }

    var validFamilyNames = familyService.read();
    List<String> familyNames = validFamilyNames.stream().map(e -> e.getName().toLowerCase()).toList();
    if (productOfferingDTO.getFamilyName() == null || productOfferingDTO.getFamilyName().isEmpty()) {
      errors.add("FamilyName cannot be empty");
    } else {

      if (!familyNames.contains(productOfferingDTO.getFamilyName().toLowerCase())) {
        errors.add("FamilyName must be one of the following: " + String.join(", ", familyNames));
      }
    }

    List<Market> validMarket = marketService.read();
    List<String> markets = validMarket.stream().map(Market::getName).toList();

    if (markets.stream().noneMatch(e -> productOfferingDTO.getMarkets().contains(e))) {
      errors.add("markets must be one of the following: " + String.join(", ", markets));
    }

    List<SubMarket> validSubMarket = marketService.readSubMarkets();
    List<String> submarkets = validSubMarket.stream().map(SubMarket::getSubMarketName).toList();

    if (productOfferingDTO.getSubmarkets() == null || productOfferingDTO.getSubmarkets().isEmpty()) {
      errors.add("submarkets cannot be empty");
    } else {

      if (submarkets.stream().noneMatch(e -> productOfferingDTO.getSubmarkets().contains(e))) {
        errors.add("sub market must be one of the following: " + String.join(", ", submarkets));
      }
    }
    return errors;
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
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Offering with ID " + po_code + " not found.");
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

    String sql = "INSERT INTO product_channel (product_id, channel_code) VALUES (?, ?)";

    base.batchUpdate(
        sql,
        new BatchPreparedStatementSetter() {
          @Override
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            ps.setInt(1, productChannelDTOs.get(i).getProductId());
            ps.setInt(2, productChannelDTOs.get(i).getChannelCode());
          }

          @Override
          public int getBatchSize() {
            return productChannelDTOs.size();
          }
        });

    return ResponseEntity.ok(Success);
  }

  @PostMapping("/insertProductEntity")
  public ResponseEntity<String> insertProductEntity(@RequestBody List<ProductEntityDTO> productEntityDTO) {
    if (productEntityDTO.isEmpty()) {
      throw new IllegalArgumentException("At least one productEntityDTO must be provided");
    }

    String sql = "INSERT INTO product_entity (product_id, entity_code) VALUES (?, ?)";

    base.batchUpdate(
        sql,
        new BatchPreparedStatementSetter() {
          @Override
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            ps.setInt(1, productEntityDTO.get(i).getProductId());
            ps.setInt(2, productEntityDTO.get(i).getEntityCode());
          }

          @Override
          public int getBatchSize() {
            return productEntityDTO.size();
          }
        });

    return ResponseEntity.ok(Success);
  }

  @PostMapping("/insertProductGroup")
  public ResponseEntity<String> insertProductGROUP(@RequestBody List<ProductGroupDto> productGroupDtos) {
    if (productGroupDtos.isEmpty()) {
      throw new IllegalArgumentException("At least one productGroupDtos must be provided");
    }

    String sql = "INSERT INTO product_pricegroup (product_id, product_price_group_code) VALUES (?, ?)";

    base.batchUpdate(
        sql,
        new BatchPreparedStatementSetter() {
          @Override
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            ps.setInt(1, productGroupDtos.get(i).getProductId());
            ps.setInt(2, productGroupDtos.get(i).getProductPriceGroupCode());
          }

          @Override
          public int getBatchSize() {
            return productGroupDtos.size();
          }
        });

    return ResponseEntity.ok(Success);
  }

  @PostMapping("/updateProductChannels")
  public ResponseEntity<String> updateProductChannels(@RequestBody List<ProductChannelDTO> productChannelDTOs) {
    if (productChannelDTOs.isEmpty()) {
      throw new IllegalArgumentException("At least one productChannelDTO must be provided");
    }

    int productId = productChannelDTOs.get(0).getProductId();

    String deleteSql = "DELETE FROM product_channel WHERE product_id = ?";
    base.update(deleteSql, productId);

    String insertSql = "INSERT INTO product_channel (product_id, channel_code) VALUES (?, ?)";
    base.batchUpdate(
        insertSql,
        new BatchPreparedStatementSetter() {
          @Override
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            ps.setInt(1, productChannelDTOs.get(i).getProductId());
            ps.setInt(2, productChannelDTOs.get(i).getChannelCode());
          }

          @Override
          public int getBatchSize() {
            return productChannelDTOs.size();
          }
        });

    return ResponseEntity.ok("Product channels updated successfully");
  }

  @PostMapping("/updateProductEntity")
  public ResponseEntity<String> updateProductEntity(@RequestBody List<ProductEntityDTO> productEntityDTOs) {
    if (productEntityDTOs.isEmpty()) {
      throw new IllegalArgumentException("At least one productEntityDTO must be provided");
    }

    int productId = productEntityDTOs.get(0).getProductId();

    String deleteSql = "DELETE FROM product_entity WHERE product_id = ?";
    base.update(deleteSql, productId);

    String insertSql = "INSERT INTO product_entity (product_id, entity_code) VALUES (?, ?)";
    base.batchUpdate(
        insertSql,
        new BatchPreparedStatementSetter() {
          @Override
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            ps.setInt(1, productEntityDTOs.get(i).getProductId());
            ps.setInt(2, productEntityDTOs.get(i).getEntityCode());
          }

          @Override
          public int getBatchSize() {
            return productEntityDTOs.size();
          }
        });

    return ResponseEntity.ok("Product entities updated successfully");
  }

  @PostMapping("/updateProductGroup")
  public ResponseEntity<String> updateProductGroup(@RequestBody List<ProductGroupDto> productGroupDtos) {
    if (productGroupDtos.isEmpty()) {
      throw new IllegalArgumentException("At least one productGroupDtos must be provided");
    }

    int productId = productGroupDtos.get(0).getProductId();

    String deleteSql = "DELETE FROM product_pricegroup WHERE product_id = ?";
    base.update(deleteSql, productId);

    String insertSql = "INSERT INTO product_pricegroup (product_id, product_price_group_code) VALUES (?, ?)";
    base.batchUpdate(
        insertSql,
        new BatchPreparedStatementSetter() {
          @Override
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            ps.setInt(1, productGroupDtos.get(i).getProductId());
            ps.setInt(2, productGroupDtos.get(i).getProductPriceGroupCode());
          }

          @Override
          public int getBatchSize() {
            return productGroupDtos.size();
          }
        });

    return ResponseEntity.ok("Product groups updated successfully");
  }

  @PostMapping("/insertDependentCfs")
  public ResponseEntity<String> insertDependentCfs(@RequestBody List<DependentCfsDto> dependentCfsDtos) {
    if (dependentCfsDtos.isEmpty()) {
      throw new IllegalArgumentException("At least one dependentCfsDtos must be provided");
    }

    String query = "SELECT COUNT(*) FROM product WHERE product_id = ?";
    for (DependentCfsDto dto : dependentCfsDtos) {
      int count = base.queryForObject(query, new Object[] {dto.getProductId()}, Integer.class);
      if (count == 0) {
        throw new IllegalArgumentException("Product offering with id " + dto.getProductId() + " does not exist");
      }
    }

    String sql = "INSERT INTO product_depend_cfs (product_id, dependent_cfs) VALUES (?, ?)";

    base.batchUpdate(
        sql,
        new BatchPreparedStatementSetter() {
          @Override
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            ps.setInt(1, dependentCfsDtos.get(i).getProductId());
            ps.setInt(2, dependentCfsDtos.get(i).getDependentCfs());
          }

          @Override
          public int getBatchSize() {
            return dependentCfsDtos.size();
          }
        });

    return ResponseEntity.ok("dependentCfs inserted successfully");
  }
}
