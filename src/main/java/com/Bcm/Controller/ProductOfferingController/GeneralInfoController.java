package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Exception.ProductOfferingAlreadyExistsException;
import com.Bcm.Exception.ProductOfferingNotFoundException;
import com.Bcm.Model.Product.GeneralInfoDTO;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Tag(name = "General Info Controller", description = "All of the General Info's methods")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/GeneralInfo")
@RequiredArgsConstructor
public class GeneralInfoController {
  final GeneralInfoService generalInfoService;
  final ProductOfferingService productOfferingService;
  final ProductOfferingRepository productOfferingRepository;
  final JdbcTemplate base;

  @Transactional
  @PostMapping("/AddProdOffDTO")
  @CacheEvict(value = "productOfferingsCache", allEntries = true)
  public ResponseEntity<?> createProductOfferingDTO(@Valid @RequestBody GeneralInfoDTO dto) {
    try {
      // Convert DTO to entity and save
      ProductOffering createdProductOffering = generalInfoService.createGeneralInfoDTO(dto);
      return new ResponseEntity<>(createdProductOffering, HttpStatus.CREATED);

    } catch (ProductOfferingAlreadyExistsException ex) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    } catch (EntityNotFoundException ex) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    } catch (DuplicateKeyException ex) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
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

  @PutMapping("/BusinessProc/{productId}")
  public ProductOffering updatePOBusinessProc(
      @RequestBody GeneralInfoDTO generalInfoDTO, @PathVariable int productId, @RequestParam int businessProcess_id)
      throws ProductOfferingNotFoundException {

    return generalInfoService.updatePOBusinessProc(generalInfoDTO, productId, businessProcess_id);
  }

  @PutMapping("/SrvcPr/{productId}")
  public ProductOffering updatePOBusinessProc(
      @RequestBody GeneralInfoDTO generalInfoDTO,
      @PathVariable int productId,
      @RequestParam int pr_id,
      @RequestParam int serviceId)
      throws ProductOfferingNotFoundException {

    return generalInfoService.updatePOSrvcPr(generalInfoDTO, productId, pr_id, serviceId);
  }

  @PutMapping("/eligibility/{Product_id}")
  public ProductOffering updateProductOfferingEligibility(
      @RequestBody GeneralInfoDTO generalInfoDTO,
      @PathVariable int Product_id,
      @RequestParam int channelCode,
      @RequestParam int entityCode,
      @RequestParam int eligibility_id,
      @RequestParam int productPriceGroupCode)
      throws ProductOfferingNotFoundException {
    return generalInfoService.updatePOEligibility(
        generalInfoDTO, Product_id, channelCode, entityCode, eligibility_id, productPriceGroupCode);
  }

  List<String> checkPO(GeneralInfoDTO generalInfoDTO) {
    List<String> errors = new ArrayList<>();

    if (generalInfoDTO.getName() == null || generalInfoDTO.getName().isEmpty()) {
      errors.add("Name cannot be empty");
    } else {
      if (!(generalInfoDTO.getName().startsWith("po_Basic-")
          || generalInfoDTO.getName().startsWith("po-optional-")
          || generalInfoDTO.getName().startsWith("po-addon-"))) {
        errors.add("Name must start with 'po_Basic-', 'po-optional-', or 'po-addon-'");
      }
    }

    if (generalInfoDTO.getEffectiveFrom() == null
        || generalInfoDTO.getName().isEmpty()
        || generalInfoDTO.getEffectiveTo() == null
        || generalInfoDTO.getName().isEmpty()) {
      errors.add("Effective from and effective to dates cannot be null");
    } else {
      if (generalInfoDTO.getEffectiveFrom().compareTo(generalInfoDTO.getEffectiveTo()) >= 0) {
        errors.add("Effective from date must be before the effective to date");
      }
    }

    if (generalInfoDTO.getCategory() == null || generalInfoDTO.getCategory().isEmpty()) {
      errors.add(" Category cannot be empty");
    } else {

      List<String> errorCategory = Arrays.asList("Billing System", "Charging System", "Both", "Internal");
      if (!errorCategory.contains(generalInfoDTO.getCategory())) {
        errors.add("category must be one of the following: " + String.join(", ", errorCategory));
      }
    }

    if (generalInfoDTO.getPoType() == null || generalInfoDTO.getPoType().isEmpty()) {
      errors.add(" poType cannot be empty");
    } else {

      List<String> errorPOTYPE = Arrays.asList("PO-Basic", "PO-Optional", "Both", "Internal");
      if (!errorPOTYPE.contains(generalInfoDTO.getPoType())) {
        errors.add("category must be one of the following: " + String.join(", ", errorPOTYPE));
      }
    }

    return errors;
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
}
