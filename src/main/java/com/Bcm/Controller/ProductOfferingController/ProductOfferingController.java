package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Exception.ErrorMessage;
import com.Bcm.Exception.InvalidInputException;
import com.Bcm.Model.ProductOfferingABE.*;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Family;
import com.Bcm.Model.ProductResourceABE.LogicalResource;
import com.Bcm.Model.ProductResourceABE.PhysicalResource;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.*;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.FamilyService;
import com.Bcm.Service.Srvc.ProductResourceSrvc.LogicalResourceService;
import com.Bcm.Service.Srvc.ProductResourceSrvc.PhysicalResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/product-offerings")
public class ProductOfferingController {

    final ProductOfferingService productOfferingService;
    final ProductSpecificationService productSpecificationService;
    final POAttributesService poAttributesService;
    final ProductOfferRelationService productOfferRelationService;
    final ProductRelationService productRelationService;
    final LogicalResourceService logicalResourceService;
    final PhysicalResourceService physicalResourceService;
    final BusinessProcessService businessProcessService;
    final EligibilityService eligibilityService;
    final FamilyService familyService;

    @PostMapping("/addProdOff")
    @CacheEvict(value = "productOfferingsCache", allEntries = true)
    public ResponseEntity<?> create(@RequestBody ProductOffering productOffering) {
        try {
            ensureRelatedEntitiesExist(productOffering);
            ProductOffering createdProduct = productOfferingService.create(productOffering);
            invalidateProductOfferingsCache();
            return ResponseEntity.ok(createdProduct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred while creating the product offering.");
        }
    }

    @CacheEvict(value = "productOfferingsCache", allEntries = true)
    public void invalidateProductOfferingsCache() {
    }

    private void ensureRelatedEntitiesExist(ProductOffering productOffering) {
        ensureProductSpecificationExists(productOffering.getProductSpecification());
        ensurePOAttributesExists(productOffering.getPoAttributes());
        ensureProductRelationExists(productOffering.getProductRelation());
        ensureProductOfferRelationExists(productOffering.getProductOfferRelation());
        ensureLogicalResourceExists(productOffering.getLogicalResource());
        ensurePhysicalResourceExists(productOffering.getPhysicalResource());
        ensureBusinessProcessExists(productOffering.getBusinessProcess());
        ensureEligibilityExists(productOffering.getEligibility());
        ensureFamilyExists(productOffering.getFamilyName());
    }

    private void ensureProductSpecificationExists(ProductSpecification productSpec) {
        if (productSpec != null && productSpec.getPo_SpecCode() != 0) {
            if (!productSpecificationService.existsById(productSpec.getPo_SpecCode())) {
                productSpecificationService.create(productSpec);
            }
        }
    }

    private void ensurePOAttributesExists(POAttributes poAttributes) {
        if (poAttributes != null && poAttributes.getPoAttribute_code() != 0) {
            if (!poAttributesService.existsById(poAttributes.getPoAttribute_code())) {
                poAttributesService.create(poAttributes);
            }
        }
    }

    private void ensureProductRelationExists(ProductRelation productRelation) {
        if (productRelation != null && productRelation.getPoRelation_Code() != 0) {
            if (!productRelationService.existsById(productRelation.getPoRelation_Code())) {
                productRelationService.create(productRelation);
            }
        }
    }

    private void ensureProductOfferRelationExists(ProductOfferRelation productOfferRelation) {
        if (productOfferRelation != null && productOfferRelation.getPoOfferRelation_Code() != 0) {
            if (!productOfferRelationService.existsById(productOfferRelation.getPoOfferRelation_Code())) {
                productOfferRelationService.create(productOfferRelation);
            }
        }
    }

    private void ensureLogicalResourceExists(LogicalResource logicalResource) {
        if (logicalResource != null && logicalResource.getLogResourceId() != 0) {
            if (!logicalResourceService.existsById(logicalResource.getLogResourceId())) {
                logicalResourceService.create(logicalResource);
            }
        }
    }

    private void ensurePhysicalResourceExists(PhysicalResource physicalResource) {
        if (physicalResource != null && physicalResource.getPhyResourceId() != 0) {
            if (!physicalResourceService.existsById(physicalResource.getPhyResourceId())) {
                physicalResourceService.create(physicalResource);
            }
        }
    }

    private void ensureBusinessProcessExists(BusinessProcess businessProcess) {
        if (businessProcess != null && businessProcess.getBusinessProcessId() != 0) {
            if (!businessProcessService.existsById(businessProcess.getBusinessProcessId())) {
                businessProcessService.create(businessProcess);
            }
        }
    }

    private void ensureEligibilityExists(Eligibility eligibility) {
        if (eligibility != null && eligibility.getEligibilityId() != 0) {
            if (!eligibilityService.existsById(eligibility.getEligibilityId())) {
                eligibilityService.create(eligibility);
            }
        }
    }

    private void ensureFamilyExists(String familyName) {
        if (familyName != null && !familyName.isEmpty()) {

            if (!familyService.findByNameexist(familyName)) {

                Family family = new Family();
                family.setName(familyName);
                familyService.create(family);
            }
        }
    }

    @GetMapping("/listProductOfferings")
    @Cacheable(value = "productOfferingsCache")
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
            @PathVariable("po_code") int po_code,
            @RequestBody ProductOffering updatedProductOffering) {
        try {
            ProductOffering existingProductOffering = productOfferingService.findById(po_code);
            if (existingProductOffering == null) {
                return ResponseEntity.notFound().build();
            }

            existingProductOffering.setName(updatedProductOffering.getName());
            existingProductOffering.setEffectiveFrom(updatedProductOffering.getEffectiveFrom());
            existingProductOffering.setEffectiveTo(updatedProductOffering.getEffectiveTo());
            existingProductOffering.setDescription(updatedProductOffering.getDescription());
            existingProductOffering.setDetailedDescription(updatedProductOffering.getDetailedDescription());
            existingProductOffering.setPoType(updatedProductOffering.getPoType());
            existingProductOffering.setFamilyName(updatedProductOffering.getFamilyName());
            existingProductOffering.setSubFamily(updatedProductOffering.getSubFamily());
            existingProductOffering.setShdes(updatedProductOffering.getShdes());
            existingProductOffering.setParent(updatedProductOffering.getParent());
            existingProductOffering.setExternalLinkId(updatedProductOffering.getExternalLinkId());
            existingProductOffering.setProductSpecification(updatedProductOffering.getProductSpecification());
            existingProductOffering.setPoAttributes(updatedProductOffering.getPoAttributes());
            existingProductOffering.setProductRelation(updatedProductOffering.getProductRelation());
            existingProductOffering.setProductOfferRelation(updatedProductOffering.getProductOfferRelation());
            existingProductOffering.setLogicalResource(updatedProductOffering.getLogicalResource());
            existingProductOffering.setPhysicalResource(updatedProductOffering.getPhysicalResource());
            existingProductOffering.setBusinessProcess(updatedProductOffering.getBusinessProcess());
            existingProductOffering.setEligibility(updatedProductOffering.getEligibility());

            ProductOffering updatedProductOfferingResult = productOfferingService.update(po_code, existingProductOffering);
            return ResponseEntity.ok(updatedProductOfferingResult);
        } catch (InvalidInputException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
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
        ErrorMessage message = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
