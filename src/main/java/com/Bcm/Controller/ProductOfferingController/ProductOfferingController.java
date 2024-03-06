package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Exception.ErrorMessage;
import com.Bcm.Model.ProductOfferingABE.*;
import com.Bcm.Model.ProductResourceABE.LogicalResource;
import com.Bcm.Model.ProductResourceABE.PhysicalResource;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.*;
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

    final  ProductOfferingService productOfferingService;
    final  ProductSpecificationService productSpecificationService;
    final  POAttributesService poAttributesService;
    final  ProductOfferRelationService productOfferRelationService;
    final  ProductRelationService productRelationService;
    final  LogicalResourceService logicalResourceService;
    final  PhysicalResourceService physicalResourceService;
    final  BusinessProcessService businessProcessService;
    final  EligibilityService eligibilityService;

    @PostMapping("/addProdOff")
    public ResponseEntity<?> create(@RequestBody ProductOffering productOffering) {
        String productSpecName = productOffering.getProductSpecification().getName();
        String poAttributeName = productOffering.getPoAttributes().getAttributeValDesc();
        String productOfferRelationName = productOffering.getProductOfferRelation().getName();
        String productRelationName = productOffering.getProductRelation().getType();
        String logicalResourceName = productOffering.getLogicalResource().getLogicalResourceType();
        String physicalResourceName = productOffering.getPhysicalResource().getPhysicalResourceType();
        String businessProcessName = productOffering.getBusinessProcess().getBussinessProcType();
        String eligibilityName = productOffering.getEligibility().getChannel();

        ProductSpecification productSpec = productSpecificationService.findByName(productSpecName);
        POAttributes poAttributes = poAttributesService.findByAttributeValDesc(poAttributeName);
        ProductRelation productRelation = productRelationService.findByType(productRelationName);
        ProductOfferRelation productOfferRelation = productOfferRelationService.findByName(productOfferRelationName);
        LogicalResource logicalResource = logicalResourceService.findByLogicalResourceType(logicalResourceName);
        PhysicalResource physicalResource = physicalResourceService.findByPhysicalResourceType(physicalResourceName);
        BusinessProcess businessProcess = businessProcessService.findByBussinessProcType(businessProcessName);
        Eligibility eligibility = eligibilityService.findByChannel(eligibilityName);

        if (productSpec != null && poAttributes != null
                && productRelation != null && productOfferRelation != null
                && logicalResource != null  && physicalResource != null
                && businessProcess != null && eligibility != null) {
            productOffering.setProductSpecification(productSpec);
            productOffering.setPoAttributes(poAttributes);
            productOffering.setProductRelation(productRelation);
            productOffering.setProductOfferRelation(productOfferRelation);
            productOffering.setLogicalResource(logicalResource);
            productOffering.setPhysicalResource(physicalResource);
            productOffering.setBusinessProcess(businessProcess);
            productOffering.setEligibility(eligibility);

            ProductOffering createdProduct = productOfferingService.create(productOffering);
            return ResponseEntity.ok(createdProduct);
        } else {
            StringBuilder errorMessage = new StringBuilder("The following entities were not found:");
            if (productSpec == null) errorMessage.append(" ProductSpecification with name: ").append(productSpecName);
            if (poAttributes == null) errorMessage.append(" POAttributes with name: ").append(poAttributeName);
            if (productRelation == null) errorMessage.append(" ProductRelation with name: ").append(productRelationName);
            if (productOfferRelation == null) errorMessage.append(" ProductOfferRelation with name: ").append(productOfferRelationName);
            if (logicalResource == null) errorMessage.append(" LogicalResource with name: ").append(logicalResourceName);
            if (physicalResource == null) errorMessage.append(" PhysicalResource with name: ").append(physicalResourceName);
            if (businessProcess == null) errorMessage.append(" BusinessProcess with Business Process Type: ").append(businessProcessName);
            if (eligibility == null) errorMessage.append(" ProductResource with Channel: ").append(eligibilityName);
            return ResponseEntity.badRequest().body(errorMessage.toString());
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
    public ResponseEntity<?> updateProductOffering(
            @PathVariable("po_code") int po_code,
            @RequestBody ProductOffering updatedProductOffering) {

        try {
            ProductOffering updatedProductOfferingResult = productOfferingService.update(po_code, updatedProductOffering);
            return ResponseEntity.ok(updatedProductOfferingResult);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
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
