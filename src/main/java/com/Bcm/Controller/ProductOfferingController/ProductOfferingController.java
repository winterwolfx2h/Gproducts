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

    /*@PostMapping("/addProdOff")
    public ResponseEntity<?> create(@RequestBody ProductOffering productOffering) {
        int productSpecName = productOffering.getProductSpecification().getPo_SpecCode();
        String poAttributeName = productOffering.getPoAttributes().getAttributeValDesc();
        String productOfferRelationName = productOffering.getProductOfferRelation().getName();
        String productRelationName = productOffering.getProductRelation().getType();
        String logicalResourceName = productOffering.getLogicalResource().getLogicalResourceType();
        String physicalResourceName = productOffering.getPhysicalResource().getPhysicalResourceType();
        String businessProcessName = productOffering.getBusinessProcess().getBussinessProcType();
        String eligibilityName = productOffering.getEligibility().getChannel();
        String familyName = productOffering.getFamily().getName();

        ProductSpecification productSpec = productSpecificationService.findById(productSpecName);
        POAttributes poAttributes = poAttributesService.findByAttributeValDesc(poAttributeName);
        ProductRelation productRelation = productRelationService.findByType(productRelationName);
        ProductOfferRelation productOfferRelation = productOfferRelationService.findByName(productOfferRelationName);
        LogicalResource logicalResource = logicalResourceService.findByLogicalResourceType(logicalResourceName);
        PhysicalResource physicalResource = physicalResourceService.findByPhysicalResourceType(physicalResourceName);
        BusinessProcess businessProcess = businessProcessService.findByBussinessProcType(businessProcessName);
        Eligibility eligibility = eligibilityService.findByChannel(eligibilityName);
        Family family = familyService.findByName(familyName);

        if (productSpec != null && poAttributes != null && productRelation != null
                && productOfferRelation != null && logicalResource != null
                && physicalResource != null && businessProcess != null
                && eligibility != null && family != null) {

            productOffering.setProductSpecification(productSpec);
            productOffering.setPoAttributes(poAttributes);
            productOffering.setProductRelation(productRelation);
            productOffering.setProductOfferRelation(productOfferRelation);
            productOffering.setLogicalResource(logicalResource);
            productOffering.setPhysicalResource(physicalResource);
            productOffering.setBusinessProcess(businessProcess);
            productOffering.setEligibility(eligibility);
            productOffering.setFamily(family);

            ProductOffering createdProduct = productOfferingService.create(productOffering);
            return ResponseEntity.ok(createdProduct);
        } else {
            StringBuilder errorMessage = new StringBuilder("The following entities were not found:");
            if (productSpec == null) errorMessage.append(" ProductSpecification with name: ").append(productSpecName);
            if (poAttributes == null) errorMessage.append(" POAttributes with name: ").append(poAttributeName);
            if (productRelation == null)
                errorMessage.append(" ProductRelation with name: ").append(productRelationName);
            if (productOfferRelation == null)
                errorMessage.append(" ProductOfferRelation with name: ").append(productOfferRelationName);
            if (logicalResource == null)
                errorMessage.append(" LogicalResource with name: ").append(logicalResourceName);
            if (physicalResource == null)
                errorMessage.append(" PhysicalResource with name: ").append(physicalResourceName);
            if (businessProcess == null)
                errorMessage.append(" BusinessProcess with Business Process Type: ").append(businessProcessName);
            if (eligibility == null) errorMessage.append(" ProductResource with Channel: ").append(eligibilityName);
            if (family == null) errorMessage.append(" Family with name: ").append(familyName);
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }
    }*/

    @PostMapping("/addProdOff")
    public ResponseEntity<?> create(@RequestBody ProductOffering productOffering) {
        try {
            ensureRelatedEntitiesExist(productOffering);
            ProductOffering createdProduct = productOfferingService.create(productOffering);
            return ResponseEntity.ok(createdProduct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred while creating the product offering.");
        }
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
    public ResponseEntity<List<ProductOffering>> getAllProductOfferings() {
        List<ProductOffering> productOfferings = productOfferingService.read();
        return ResponseEntity.ok(productOfferings);
    }

    @GetMapping("/{po_code}")
    public ResponseEntity<ProductOffering> getProductOfferingById(@PathVariable("po_code") int po_code) {
        ProductOffering productOffering = productOfferingService.findById(po_code);
        return ResponseEntity.ok(productOffering);
    }

    /*@PutMapping("/{po_code}")
    public ResponseEntity<?> updateProductOffering(
            @PathVariable("po_code") int po_code,
            @RequestBody ProductOffering updatedProductOffering) {
        try {
            ProductOffering existingProductOffering = productOfferingService.findById(po_code);
            if (existingProductOffering == null) {
                return ResponseEntity.notFound().build();
            }

            int productSpecName = updatedProductOffering.getProductSpecification().getPo_SpecCode();
            String poAttributeName = updatedProductOffering.getPoAttributes().getAttributeValDesc();
            String productOfferRelationName = updatedProductOffering.getProductOfferRelation().getName();
            String productRelationName = updatedProductOffering.getProductRelation().getType();
            String logicalResourceName = updatedProductOffering.getLogicalResource().getLogicalResourceType();
            String physicalResourceName = updatedProductOffering.getPhysicalResource().getPhysicalResourceType();
            String businessProcessName = updatedProductOffering.getBusinessProcess().getBussinessProcType();
            String eligibilityName = updatedProductOffering.getEligibility().getChannel();
            String familyName = updatedProductOffering.getFamilyName();

            ProductSpecification productSpec = productSpecificationService.findById(productSpecName);
            POAttributes poAttributes = poAttributesService.findByAttributeValDesc(poAttributeName);
            ProductRelation productRelation = productRelationService.findByType(productRelationName);
            ProductOfferRelation productOfferRelation = productOfferRelationService.findByName(productOfferRelationName);
            LogicalResource logicalResource = logicalResourceService.findByLogicalResourceType(logicalResourceName);
            PhysicalResource physicalResource = physicalResourceService.findByPhysicalResourceType(physicalResourceName);
            BusinessProcess businessProcess = businessProcessService.findByBussinessProcType(businessProcessName);
            Eligibility eligibility = eligibilityService.findByChannel(eligibilityName);
            Family family = familyService.findByName(familyName);

            if (productSpec == null || poAttributes == null || productRelation == null
                    || productOfferRelation == null || logicalResource == null
                    || physicalResource == null || businessProcess == null
                    || eligibility == null || family == null) {
                return ResponseEntity.badRequest().body("One or more entities weren't found.");
            }

            existingProductOffering.setName(existingProductOffering.getName());
            existingProductOffering.setEffectiveFrom(existingProductOffering.getEffectiveFrom());
            existingProductOffering.setEffectiveTo(existingProductOffering.getEffectiveTo());
            existingProductOffering.setDescription(existingProductOffering.getDescription());
            existingProductOffering.setPoType(existingProductOffering.getPoType());
            existingProductOffering.setFamilyName(existingProductOffering.getFamilyName());
            existingProductOffering.setSubFamily(existingProductOffering.getSubFamily());
            existingProductOffering.setShdes(existingProductOffering.getShdes());
            existingProductOffering.setParent(existingProductOffering.getParent());
            existingProductOffering.setExternalLinkId(existingProductOffering.getExternalLinkId());
            existingProductOffering.setProductSpecification(productSpec);
            existingProductOffering.setPoAttributes(poAttributes);
            existingProductOffering.setProductRelation(productRelation);
            existingProductOffering.setProductOfferRelation(productOfferRelation);
            existingProductOffering.setLogicalResource(logicalResource);
            existingProductOffering.setPhysicalResource(physicalResource);
            existingProductOffering.setBusinessProcess(businessProcess);
            existingProductOffering.setEligibility(eligibility);

            ProductOffering updatedProductOfferingResult = productOfferingService.update(po_code, existingProductOffering);
            return ResponseEntity.ok(updatedProductOfferingResult);
        } catch (InvalidInputException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }*/

    @PutMapping("/{po_code}")
    public ResponseEntity<?> updateProductOffering(
            @PathVariable("po_code") int po_code,
            @RequestBody ProductOffering updatedProductOffering) {
        try {
            ProductOffering existingProductOffering = productOfferingService.findById(po_code);
            if (existingProductOffering == null) {
                return ResponseEntity.notFound().build();
            }

            // Update the fields of the existingProductOffering with the values from updatedProductOffering
            existingProductOffering.setName(updatedProductOffering.getName());
            existingProductOffering.setEffectiveFrom(updatedProductOffering.getEffectiveFrom());
            existingProductOffering.setEffectiveTo(updatedProductOffering.getEffectiveTo());
            existingProductOffering.setDescription(updatedProductOffering.getDescription());
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

            // Call the update method in the service
            ProductOffering updatedProductOfferingResult = productOfferingService.update(po_code, existingProductOffering);
            return ResponseEntity.ok(updatedProductOfferingResult);
        } catch (InvalidInputException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/{po_code}")
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
