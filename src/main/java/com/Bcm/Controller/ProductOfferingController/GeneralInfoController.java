package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Exception.ProductOfferingAlreadyExistsException;
import com.Bcm.Exception.ProductOfferingNotFoundException;
import com.Bcm.Model.Product.GeneralInfoDTO;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import com.Bcm.Repository.ProductOfferingRepo.ProductOfferingRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.GeneralInfoService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferingService;
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

  @PutMapping("/PriceGroup/{productId}")
  public ProductOffering updatePOPrg(
      @RequestBody GeneralInfoDTO generalInfoDTO, @PathVariable int productId, @RequestParam int productPriceGroupCode)
      throws ProductOfferingNotFoundException {

    return generalInfoService.updatePOPrg(generalInfoDTO, productId, productPriceGroupCode);
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
      @RequestParam int serviceId,
      @RequestParam int pr_id)
      throws ProductOfferingNotFoundException {

    return generalInfoService.updatePOSrvcPr(generalInfoDTO, productId, serviceId, pr_id);
  }

  @PutMapping("/eligibility/{Product_id}")
  public ProductOffering updateProductOfferingEligibility(
      @RequestBody GeneralInfoDTO generalInfoDTO,
      @PathVariable int Product_id,
      @RequestParam int channelCode,
      @RequestParam int entityCode,
      @RequestParam int eligibility_id)
      throws ProductOfferingNotFoundException {
    return generalInfoService.updatePOEligibility(generalInfoDTO, Product_id, channelCode, entityCode, eligibility_id);
  }

  @GetMapping("/GetDTOs")
  public ResponseEntity<List<GeneralInfoDTO>> getAllProductOfferingDTOs() {
    List<GeneralInfoDTO> dtos = generalInfoService.getAllGeneralInfoDTOs();
    return ResponseEntity.ok(dtos);
  }

  //
  //  private GeneralInfoDTO convertToDTO(ProductOffering productOffering) {
  //    GeneralInfoDTO dto = new GeneralInfoDTO();
  //    dto.setProduct_id(productOffering.getProduct_id());
  //    dto.setName(productOffering.getName());
  //    dto.setEffectiveFrom(productOffering.getEffectiveFrom());
  //    dto.setEffectiveTo(productOffering.getEffectiveTo());
  //    dto.setDescription(productOffering.getDescription());
  //    dto.setDetailedDescription(productOffering.getDetailedDescription());
  //    dto.setPoType(productOffering.getPoType());
  //    dto.setParent(productOffering.getParent());
  //    dto.setWorkingStep(productOffering.getWorkingStep());
  //    dto.setSellIndicator(productOffering.getSellIndicator());
  //    dto.setQuantityIndicator(productOffering.getQuantityIndicator());
  //    dto.setCategory(productOffering.getCategory());
  //    dto.setStatus(productOffering.getStatus());
  //    dto.setPoParent_Child(productOffering.getPoParent_Child());
  //    dto.setBS_externalId(productOffering.getBS_externalId());
  //    dto.setCS_externalId(productOffering.getCS_externalId());
  //    productOffering.setPR_id(productOffering.getPR_id());
  //    productOffering.setServiceId(productOffering.getServiceId());
  //    return dto;
  //  }

}
