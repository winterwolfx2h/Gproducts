package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Exception.ProductOfferingAlreadyExistsException;
import com.Bcm.Model.Product.GeneralInfoDTO;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.GeneralInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/GeneralInfo")
@RequiredArgsConstructor

public class GeneralInfoController {
    final GeneralInfoService generalInfoService;

    @GetMapping("/GetDTOs")
    public ResponseEntity<List<GeneralInfoDTO>> getAllGeneralInfoDTOs() {
        List<GeneralInfoDTO> dtos = generalInfoService.getAllGeneralInfoDTOs();
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/AddProdOffDTO")
    @CacheEvict(value = "productOfferingsCache", allEntries = true)
    public ResponseEntity<?> createProductOfferingDTO(@Valid @RequestBody GeneralInfoDTO dto) {
        try {




            // Convert DTO to entity and save
            ProductOffering createdProductOffering = generalInfoService.createGeneralInfoDTO(dto);

            return new ResponseEntity<>(createdProductOffering, HttpStatus.CREATED);

        } catch (ProductOfferingAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (Exception e) {
            e.printStackTrace();  // Print stack trace for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred while creating the Product Offering. Error: " + e.getMessage());
        }
    }




/*
    @PutMapping("/update-dto/{po_code}")
    @CacheEvict(value = "productOfferingsCache", allEntries = true)
    public ResponseEntity<?> updateProductOfferingDTO(@PathVariable int po_code, @RequestBody ProductOfferingDTO updatedDTO) {
        try {
            ProductOffering existingProductOffering = generalInfoService.ge(po_code);
            if (existingProductOffering == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Offering with ID " + po_code + " not found.");
            }
            String newName = updatedDTO.getName();
            if (!existingProductOffering.getName().equals(newName)) {
                if (productOfferingService.existsByName(newName)) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("Product Offering with the name '" + newName + "' already exists.");
                }
            }
            String newFamilyName = updatedDTO.getFamilyName();
            if (newFamilyName != null && !familyService.findByNameexist(newFamilyName)) {
                return ResponseEntity.badRequest().body("Family with name '" + newFamilyName + "' does not exist.");
            }
            String marketName = updatedDTO.getMarkets();
            if (marketName == null || !marketService.findByNameexist(marketName)) {
                return ResponseEntity.badRequest().body("Market with name '" + marketName + "' does not exist.");
            }
            String submarketName = updatedDTO.getSubmarkets();
            if (submarketName == null || !subMarketService.findByNameexist(submarketName)) {
                return ResponseEntity.badRequest().body("Submarket with name '" + submarketName + "' does not exist.");
            }
            existingProductOffering.setFamilyName(newFamilyName);
            ProductOfferingDTO updatedProductOfferingDTO = productOfferingService.updateProductOfferingDTO(po_code, updatedDTO);
            return ResponseEntity.ok(updatedProductOfferingDTO);

        } catch (ProductOfferingAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("A Product Offering with the same name already exists.");
        } catch (InvalidInputException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred while updating the Product Offering.");
        }
    }

 */
}

