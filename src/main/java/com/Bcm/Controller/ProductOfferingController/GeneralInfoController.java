package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Exception.ProductOfferingAlreadyExistsException;
import com.Bcm.Exception.ProductOfferingNotFoundException;
import com.Bcm.Model.Product.GeneralInfoDTO;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import com.Bcm.Repository.ProductOfferingRepo.ProductOfferingRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.GeneralInfoService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public ResponseEntity<?> createProductOfferingDTO(@Valid @RequestBody GeneralInfoDTO dto, @RequestParam Integer channel, @RequestParam Integer entity) {
        try {
            // Convert DTO to entity and save
            ProductOffering createdProductOffering = generalInfoService.createGeneralInfoDTO(dto);

            base.update("INSERT INTO public.productoffering_channel(" +
                    " Product_id, po_channel_code) " +
                    " VALUES (?, ?);", new Object[]{createdProductOffering.getProduct_id(), channel});


            base.update("INSERT INTO public.productoffering_entity(" +
                    " Product_id, entity_code) " +
                    " VALUES (?, ?);", new Object[]{createdProductOffering.getProduct_id(), entity});


            return new ResponseEntity<>(createdProductOffering, HttpStatus.CREATED);

        } catch (ProductOfferingAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (Exception e) {
            e.printStackTrace();  // Print stack trace for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred while creating the Product Offering. Error: " + e.getMessage());
        }
    }

    @PutMapping("/{Product_id}")
    public ProductOffering updateProductOffering(@RequestBody GeneralInfoDTO generalInfoDTO, @PathVariable int Product_id, @RequestParam int prId, @RequestParam int serviceId) throws ProductOfferingNotFoundException {
        return generalInfoService.updateProductOffering(generalInfoDTO, Product_id, prId, serviceId);
    }

    @PutMapping("/eligibility/{Product_id}")
    public ProductOffering updateProductOfferingEligibility(@RequestBody GeneralInfoDTO generalInfoDTO, @PathVariable int Product_id, @RequestParam int eligibility_id) throws ProductOfferingNotFoundException {
        return generalInfoService.updateProductOfferingEligibility(generalInfoDTO, Product_id, eligibility_id);
    }

    @GetMapping("/GetDTOs")
    public ResponseEntity<List<GeneralInfoDTO>> getAllProductOfferingDTOs() {
        List<GeneralInfoDTO> dtos = generalInfoService.getAllGeneralInfoDTOs();
        return ResponseEntity.ok(dtos);
    }

    private GeneralInfoDTO convertToDTO(ProductOffering productOffering) {
        GeneralInfoDTO dto = new GeneralInfoDTO();
        dto.setProduct_id(productOffering.getProduct_id());
        dto.setName(productOffering.getName());
        dto.setEffectiveFrom(productOffering.getEffectiveFrom());
        dto.setEffectiveTo(productOffering.getEffectiveTo());
        dto.setDescription(productOffering.getDescription());
        dto.setDetailedDescription(productOffering.getDetailedDescription());
        dto.setPoType(productOffering.getPoType());
        dto.setParent(productOffering.getParent());
        dto.setWorkingStep(productOffering.getWorkingStep());
        dto.setSellIndicator(productOffering.getSellIndicator());
        dto.setQuantityIndicator(productOffering.getQuantityIndicator());
        dto.setCategory(productOffering.getCategory());
        dto.setStatus(productOffering.getStatus());
        dto.setPoParent_Child(productOffering.getPoParent_Child());
        dto.setBS_externalId(productOffering.getBS_externalId());
        dto.setCS_externalId(productOffering.getCS_externalId());
        productOffering.setPR_id(productOffering.getPR_id());
        productOffering.setServiceId(productOffering.getServiceId());
        return dto;
    }
}
