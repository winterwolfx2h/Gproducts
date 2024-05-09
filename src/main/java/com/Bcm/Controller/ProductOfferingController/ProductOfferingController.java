package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Exception.ErrorMessage;
import com.Bcm.Exception.InvalidInputException;
import com.Bcm.Exception.ProductOfferingAlreadyExistsException;
import com.Bcm.Exception.ProductOfferingLogicException;
import com.Bcm.Model.Product.ProductOfferingDTO;
import com.Bcm.Model.ProductOfferingABE.*;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Family;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Market;
import com.Bcm.Model.ProductOfferingABE.SubClasses.SubMarket;
import com.Bcm.Model.ProductResourceABE.PhysicalResource;
import com.Bcm.Model.ServiceABE.CustomerFacingServiceSpec;
import com.Bcm.Repository.ProductOfferingRepo.ProductSpecificationRepository;
import com.Bcm.Service.Srvc.POPlanService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.*;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.FamilyService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.MarketService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.SubMarketService;
import com.Bcm.Service.Srvc.ProductResourceSrvc.LogicalResourceService;
import com.Bcm.Service.Srvc.ProductResourceSrvc.PhysicalResourceService;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.CustomerFacingServiceSpecService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    final POPlanService poplanService;
    final ProductSpecificationRepository productSpecificationRepository;
    final CustomerFacingServiceSpecService customerFacingServiceSpecService;
    final MarketService marketService;
    final SubMarketService subMarketService;

    @PostMapping("/addProdOff")
    @CacheEvict(value = "productOfferingsCache", allEntries = true)
    public ResponseEntity<?> create(@RequestBody ProductOffering productOffering) {
        try {
            // Validate market names and convert to Market objects
            List<Market> validMarkets = marketService.read();
            List<String> marketNames = productOffering.getMarkets();

            if (marketNames == null || marketNames.isEmpty()) {
                return ResponseEntity.badRequest().body("Market names list cannot be empty.");
            }


            List<Market> validIncomingMarkets = marketNames.stream()
                    .map(name -> validMarkets.stream()
                            .filter(validMarket -> validMarket.getName().equals(name))
                            .findFirst())
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());

            if (validIncomingMarkets.size() != marketNames.size()) {
                return ResponseEntity.badRequest().body("Some provided market names are invalid.");
            }

            productOffering.setMarkets(marketNames); // Setting the correct field

            // Validate and convert submarket names to valid SubMarket objects
            List<SubMarket> validSubMarkets = subMarketService.read();
            List<String> submarketNames = productOffering.getSubmarkets();

            if (submarketNames == null || submarketNames.isEmpty()) {
                return ResponseEntity.badRequest().body("Submarket names list cannot be empty.");
            }

            List<SubMarket> validIncomingSubMarkets = submarketNames.stream()
                    .map(name -> validSubMarkets.stream()
                            .filter(validSubMarket -> validSubMarket.getName().equals(name))
                            .findFirst())
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());

            if (validIncomingSubMarkets.size() != submarketNames.size()) {
                return ResponseEntity.badRequest().body("Some provided submarket names are invalid.");
            }

            productOffering.setSubmarkets(submarketNames); // Setting the correct field

            // Validate family name
            String familyName = productOffering.getFamilyName();
            if (familyName == null || !familyService.findByNameexist(familyName)) {
                return ResponseEntity.badRequest().body("Family with name '" + familyName + "' does not exist.");
            }

            // Validate channels
            List<String> validChannels = eligibilityService.read()
                    .stream()
                    .map(Eligibility::getChannel)
                    .collect(Collectors.toList());

            if (!validChannels.containsAll(productOffering.getChannels())) {
                return ResponseEntity.badRequest().body("Invalid channel(s) in the Product Offering.");
            }

            // Validate customer-facing service specs
            List<String> serviceSpecConfigs = productOffering.getCustomerFacingServiceSpec();
            List<String> missingServices = serviceSpecConfigs.stream()
                    .filter(serviceType -> !customerFacingServiceSpecService.findByNameexist(serviceType))
                    .collect(Collectors.toList());

            if (!missingServices.isEmpty()) {
                return ResponseEntity.badRequest().body("Service(s) with Service Spec Type '" + String.join(", ", missingServices) + "' do not exist.");
            }

            if (productOfferingService.existsByName(productOffering.getName())) {
                return ResponseEntity.badRequest().body("A product offering with the same name already exists.");
            }

            // Ensure other related entities exist
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


    @CacheEvict(value = "productOfferingsCache", allEntries = true)
    public void invalidateProductOfferingsCache() {
    }


    @PostMapping("/AddProdOffDTO")
    @CacheEvict(value = "productOfferingsCache", allEntries = true)
    public ResponseEntity<?> createProductOfferingDTO(@RequestBody ProductOfferingDTO dto) {
        try {

            // Validate market names and convert to Market objects
            List<Market> validMarkets = marketService.read();
            List<String> marketNames = dto.getMarkets();

            if (marketNames == null || marketNames.isEmpty()) {
                return ResponseEntity.badRequest().body("Market names list cannot be empty.");
            }


            List<Market> validIncomingMarkets = marketNames.stream()
                    .map(name -> validMarkets.stream()
                            .filter(validMarket -> validMarket.getName().equals(name))
                            .findFirst())
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());

            if (validIncomingMarkets.size() != marketNames.size()) {
                return ResponseEntity.badRequest().body("Some provided market names are invalid.");
            }

            dto.setMarkets(marketNames); // Setting the correct field

            // Validate and convert submarket names to valid SubMarket objects
            List<SubMarket> validSubMarkets = subMarketService.read();
            List<String> submarketNames = dto.getSubmarkets();

            if (submarketNames == null || submarketNames.isEmpty()) {
                return ResponseEntity.badRequest().body("Submarket names list cannot be empty.");
            }

            List<SubMarket> validIncomingSubMarkets = submarketNames.stream()
                    .map(name -> validSubMarkets.stream()
                            .filter(validSubMarket -> validSubMarket.getName().equals(name))
                            .findFirst())
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());

            if (validIncomingSubMarkets.size() != submarketNames.size()) {
                return ResponseEntity.badRequest().body("Some provided submarket names are invalid.");
            }

            dto.setSubmarkets(submarketNames); // Setting the correct field

            // Validate family name
            String familyName = dto.getFamilyName();
            if (familyName == null || !familyService.findByNameexist(familyName)) {
                return ResponseEntity.badRequest().body("Family with name '" + familyName + "' does not exist.");
            }
            ProductOffering createdProductOffering = productOfferingService.createProductOfferingDTO(dto);
            return new ResponseEntity<>(createdProductOffering, HttpStatus.CREATED);
        } catch (ProductOfferingAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ex.getMessage());
        }
    }

    private void ensureRelatedEntitiesExist(ProductOffering productOffering) {
        //ensureProductSpecificationExists(productOffering.getProductSpecification());
        ensurePOAttributesExists((List<POAttributes>) productOffering.getPoAttributes());
        ensureProductRelationExists(productOffering.getProductRelation());
        //ensureProductOfferRelationExists(productOffering.getProductOfferRelation());
        //ensureLogicalResourceExists(productOffering.getLogicalResource());
        //ensurePhysicalResourceExists(productOffering.getPhysicalResource());
        //ensureBusinessProcessExists(productOffering.getBusinessProcess());
        ensureCustomerFacingServiceSpecExists(productOffering.getCustomerFacingServiceSpec());
        ensureFamilyExists(productOffering.getFamilyName());
    }

    private void ensureProductSpecificationExists(ProductSpecification productSpec) {
        if (productSpec != null && productSpec.getPo_SpecCode() != 0) {
            if (!productSpecificationService.existsById(productSpec.getPo_SpecCode())) {
                productSpecificationService.create(productSpec);
            }
        }
    }

    private void ensurePOAttributesExists(List<POAttributes> poAttributesList) {
        if (poAttributesList != null && !poAttributesList.isEmpty()) {
            for (POAttributes poAttribute : poAttributesList) {
                if (!poAttributesService.existsById(poAttribute.getPoAttribute_code())) {
                    poAttributesService.create(poAttribute);
                }
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

    /*private void ensureLogicalResourceExists(LogicalResource logicalResource) {
        if (logicalResource != null && logicalResource.getLogResourceId() != 0) {
            if (!logicalResourceService.existsById(logicalResource.getLogResourceId())) {
                logicalResourceService.create(logicalResource);
            }
        }
    }*/

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
                eligibilityService.create((List<Eligibility>) eligibility);
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

    private void ensureCustomerFacingServiceSpecExists(List<String> customerFacingServiceSpec) {
        if (customerFacingServiceSpec != null && !customerFacingServiceSpec.isEmpty()) {
            for (String serviceSpecType : customerFacingServiceSpec) {
                if (!customerFacingServiceSpecService.findByNameexist(serviceSpecType)) {
                    CustomerFacingServiceSpec newCustomerFacingServiceSpec = new CustomerFacingServiceSpec();
                    newCustomerFacingServiceSpec.setServiceSpecType(serviceSpecType);
                    customerFacingServiceSpecService.create(newCustomerFacingServiceSpec);
                }
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
            @RequestBody ProductOffering updatedProductOffering
    ) {
        try {
            // Retrieve the existing product offering
            ProductOffering existingProductOffering = productOfferingService.findById(po_code);
            if (existingProductOffering == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Offering with ID " + po_code + " not found.");
            }

            // Ensure the new name doesn't conflict with an existing product offering
            String newName = updatedProductOffering.getName();
            if (!existingProductOffering.getName().equals(newName)) {
                if (productOfferingService.existsByName(newName)) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("Product Offering with the name '" + newName + "' already exists.");
                }
            }

            // Validate incoming market names
            List<Market> validMarkets = marketService.read();
            List<String> marketNames = updatedProductOffering.getMarkets();

            if (marketNames == null || marketNames.isEmpty()) {
                return ResponseEntity.badRequest().body("Market names list cannot be empty.");
            }

            List<Market> validIncomingMarkets = marketNames.stream()
                    .map(name -> validMarkets.stream()
                            .filter(market -> market.getName().equals(name))
                            .findFirst())
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());

            if (validIncomingMarkets.size() != marketNames.size()) {
                return ResponseEntity.badRequest().body("Some provided market names are invalid.");
            }

            existingProductOffering.setMarkets(marketNames); // Set the field to the list of names

            // Validate incoming submarket names
            List<SubMarket> validSubMarkets = subMarketService.read();
            List<String> submarketNames = updatedProductOffering.getSubmarkets();

            if (submarketNames == null || submarketNames.isEmpty()) {
                return ResponseEntity.badRequest().body("Submarket names list cannot be empty.");
            }

            List<SubMarket> validIncomingSubMarkets = submarketNames.stream()
                    .map(name -> validSubMarkets.stream()
                            .filter(submarket -> submarket.getName().equals(name))
                            .findFirst())
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());

            if (validIncomingSubMarkets.size() != submarketNames.size()) {
                return ResponseEntity.badRequest().body("Some provided submarket names are invalid.");
            }

            existingProductOffering.setSubmarkets(submarketNames); // Set the field to the list of names

            // Validate family name
            String newFamilyName = updatedProductOffering.getFamilyName();
            if (newFamilyName != null && !familyService.findByNameexist(newFamilyName)) {
                return ResponseEntity.badRequest().body("Family with name '" + newFamilyName + "' does not exist.");
            }

            existingProductOffering.setFamilyName(newFamilyName);

            // Validate channels
            List<String> validChannels = eligibilityService.read()
                    .stream()
                    .map(Eligibility::getChannel)
                    .collect(Collectors.toList());

            if (!validChannels.containsAll(updatedProductOffering.getChannels())) {
                return ResponseEntity.badRequest().body("Invalid channel(s) in the Product Offering.");
            }

            existingProductOffering.setChannels(updatedProductOffering.getChannels());

            // Validate customer-facing service specs
            List<String> serviceSpecConfigs = updatedProductOffering.getCustomerFacingServiceSpec();
            List<String> missingServices = serviceSpecConfigs.stream()
                    .filter(serviceType -> !customerFacingServiceSpecService.findByNameexist(serviceType))
                    .collect(Collectors.toList());

            if (!missingServices.isEmpty()) {
                return ResponseEntity.badRequest().body("Service(s) with Service Spec Type '" + String.join(", ", missingServices) + "' do not exist.");
            }

            existingProductOffering.setCustomerFacingServiceSpec(serviceSpecConfigs);

            // Update other fields
            existingProductOffering.setEffectiveFrom(updatedProductOffering.getEffectiveFrom());
            existingProductOffering.setEffectiveTo(updatedProductOffering.getEffectiveTo());
            existingProductOffering.setDescription(updatedProductOffering.getDescription());
            existingProductOffering.setDetailedDescription(updatedProductOffering.getDetailedDescription());
            existingProductOffering.setPoType(updatedProductOffering.getPoType());

            // Update other relationships and related entities
            ensureRelatedEntitiesExist(existingProductOffering);

            // Save the updated product offering
            ProductOffering updatedResult = productOfferingService.update(po_code, existingProductOffering);

            return ResponseEntity.ok(updatedResult);

        } catch (ProductOfferingAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("A Product Offering with the same name already exists.");
        } catch (InvalidInputException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred while updating the Product Offering.");
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

}
