package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Exception.ProductOfferingAlreadyExistsException;
import com.Bcm.Exception.ProductOfferingNotFoundException;
import com.Bcm.Model.Product.GeneralInfoDTO;
import com.Bcm.Model.Product.Product;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import com.Bcm.Repository.ProductOfferingRepo.ProductOfferingRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.GeneralInfoService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Tag(name = "General Info Controller", description = "All of the General Info's methods")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/GeneralInfo")
@RequiredArgsConstructor
public class GeneralInfoController {
  private static final String INT = "Internal";
  final GeneralInfoService generalInfoService;
  final ProductOfferingService productOfferingService;
  final ProductOfferingRepository productOfferingRepository;

  @Transactional
  @PostMapping("/AddProdOffDTO")
  @CacheEvict(value = "productOfferingsCache", allEntries = true)
  public ResponseEntity<?> createProductOfferingDTO(@Valid @RequestBody GeneralInfoDTO dto) {
    try {
      ProductOffering createdProductOffering = generalInfoService.createGeneralInfoDTO(dto);
      return new ResponseEntity<>(createdProductOffering, HttpStatus.CREATED);

    } catch (ProductOfferingAlreadyExistsException | DuplicateKeyException ex) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    } catch (EntityNotFoundException ex) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("An unexpected error occurred while creating the Product Offering. Error: " + e.getMessage());
    }
  }

  @PutMapping("/Price/{productId}")
  public ProductOffering updatePrice(
      @RequestBody GeneralInfoDTO generalInfoDTO, @PathVariable int productId, @RequestParam int productPriceCode)
      throws ProductOfferingNotFoundException {

    return generalInfoService.updatePOPrice(generalInfoDTO, productId, productPriceCode);
  }

  @PutMapping("/Tax/{productId}")
  public ResponseEntity<?> updateTax(
      @RequestBody GeneralInfoDTO generalInfoDTO, @PathVariable int productId, @RequestParam int taxCode) {

    try {
      Product updatedProduct = generalInfoService.updatePOTax(generalInfoDTO, productId, taxCode);
      return ResponseEntity.ok(updatedProduct);
    } catch (ProductOfferingNotFoundException e) {
      return ResponseEntity.notFound().build();
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("An unexpected error occurred while updating the product. Error: " + e.getMessage());
    }
  }

  @PutMapping("/BusinessProc/{productId}")
  public ProductOffering updatePOBusinessProc(
      @RequestBody GeneralInfoDTO generalInfoDTO, @PathVariable int productId, @RequestParam int businessProcess_id)
      throws ProductOfferingNotFoundException {

    return generalInfoService.updatePOBusinessProc(generalInfoDTO, productId, businessProcess_id);
  }

  @PutMapping("/SrvcPr/{productId}")
  public ProductOffering updatePOSrvcPr(
      @RequestBody GeneralInfoDTO generalInfoDTO,
      @PathVariable int productId,
      @RequestParam(required = false) Integer pr_id,
      @RequestParam(required = false) Integer serviceId)
      throws ProductOfferingNotFoundException {

    return generalInfoService.updatePOSrvcPr(generalInfoDTO, productId, pr_id, serviceId);
  }

  @PutMapping("/StockInd/{productId}")
  public ResponseEntity<ProductOffering> updatePOStockInd(
      @RequestBody GeneralInfoDTO generalInfoDTO,
      @PathVariable int productId,
      @RequestParam(required = false) boolean stockInd) {
    try {
      ProductOffering productOffering = generalInfoService.updatePOStockInd(generalInfoDTO, productId, stockInd);
      return ResponseEntity.ok(productOffering);
    } catch (ProductOfferingNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  List<String> checkPO(GeneralInfoDTO generalInfoDTO) {
    List<String> errors = new ArrayList<>();

    checkName(generalInfoDTO, errors);
    checkEffectiveDates(generalInfoDTO, errors);
    checkCategory(generalInfoDTO, errors);
    checkPoType(generalInfoDTO, errors);

    return errors;
  }

  private void checkName(GeneralInfoDTO generalInfoDTO, List<String> errors) {
    String name = generalInfoDTO.getName();
    if (name == null || name.isEmpty()) {
      errors.add("Name cannot be empty");
    } else if (!isValidNamePrefix(name)) {
      errors.add("Name must start with 'po-basic-', 'po-optional-', or 'po-add-'");
    }
  }

  private boolean isValidNamePrefix(String name) {
    return name.startsWith("po-basic-") || name.startsWith("po-optional-") || name.startsWith("po-add-");
  }

  private void checkEffectiveDates(GeneralInfoDTO generalInfoDTO, List<String> errors) {
    if (generalInfoDTO.getEffectiveFrom() == null || generalInfoDTO.getEffectiveTo() == null) {
      errors.add("Effective from and effective to dates cannot be null");
    } else if (generalInfoDTO.getEffectiveFrom().compareTo(generalInfoDTO.getEffectiveTo()) >= 0) {
      errors.add("Effective from date must be before the effective to date");
    }
  }

  private void checkCategory(GeneralInfoDTO generalInfoDTO, List<String> errors) {
    String category = generalInfoDTO.getCategory();
    if (category == null || category.isEmpty()) {
      errors.add("Category cannot be empty");
    } else if (!isValidCategory(category)) {
      List<String> validCategories = Arrays.asList("Billing", "Charging", "Both", INT);
      errors.add("Category must be one of the following: " + String.join(", ", validCategories));
    }
  }

  private boolean isValidCategory(String category) {
    List<String> validCategories = Arrays.asList("Billing", "Charging", "Both", INT);
    return validCategories.contains(category);
  }

  private void checkPoType(GeneralInfoDTO generalInfoDTO, List<String> errors) {
    String poType = generalInfoDTO.getPoType();
    if (poType == null || poType.isEmpty()) {
      errors.add("PO Type cannot be empty");
    } else if (!isValidPoType(poType)) {
      List<String> validPoTypes = Arrays.asList("PO-Basic", "PO-Optional", "Both", INT);
      errors.add("PO Type must be one of the following: " + String.join(", ", validPoTypes));
    }
  }

  private boolean isValidPoType(String poType) {
    List<String> validPoTypes = Arrays.asList("PO-Basic", "PO-Optional", "Both", INT);
    return validPoTypes.contains(poType);
  }

  @PostMapping("/checkErrorPO")
  public ResponseEntity<?> checkErrorPo(@RequestBody GeneralInfoDTO generalInfoDTO) {

    var errors = checkPO(generalInfoDTO);
    if (!errors.isEmpty()) {
      return ResponseEntity.badRequest().body(errors);
    }
    return ResponseEntity.ok(errors);
  }

  @GetMapping("/GetDTOs")
  public ResponseEntity<List<GeneralInfoDTO>> getAllProductOfferingDTOs() {
    List<GeneralInfoDTO> dtos = generalInfoService.getAllGeneralInfoDTOs();
    return ResponseEntity.ok(dtos);
  }

  @PutMapping("/{productId}")
  public ResponseEntity<?> updateGeneralInfo(@RequestBody GeneralInfoDTO generalInfoDTO, @PathVariable int productId) {
    try {
      ProductOffering updatedProductOffering = generalInfoService.updateGeneralInfoDTO(generalInfoDTO, productId);
      return ResponseEntity.ok(updatedProductOffering);
    } catch (ProductOfferingNotFoundException ex) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("An unexpected error occurred: " + e.getMessage());
    }
  }

  @GetMapping("/GenInfoDTOByID/{Product_id}")
  public ResponseEntity<GeneralInfoDTO> getDTOById(@PathVariable("Product_id") int Product_id)
      throws ProductOfferingNotFoundException {
    GeneralInfoDTO generalInfoDTO = generalInfoService.getDTOById(Product_id);
    return ResponseEntity.ok(generalInfoDTO);
  }
}
