package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.BCMDrools.ProductOfferingDrools.ProductOfferingValidationService;
import com.Bcm.Exception.ErrorMessage;
import com.Bcm.Exception.InvalidInputException;
import com.Bcm.Exception.ProductOfferingAlreadyExistsException;
import com.Bcm.Exception.ProductOfferingLogicException;
import com.Bcm.Model.Product.ProductOfferingDTO;
import com.Bcm.Model.ProductOfferingABE.POAttributes;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import com.Bcm.Model.ProductOfferingABE.ProductRelation;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Family;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Market;
import com.Bcm.Model.ProductOfferingABE.SubClasses.SubMarket;
import com.Bcm.Model.ServiceABE.CustomerFacingServiceSpec;
import com.Bcm.Repository.ProductOfferingRepo.ProductSpecificationRepository;
import com.Bcm.Service.Srvc.POPlanService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.*;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.ChannelService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.FamilyService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.MarketService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.SubMarketService;
import com.Bcm.Service.Srvc.ProductResourceSrvc.LogicalResourceService;
import com.Bcm.Service.Srvc.ProductResourceSrvc.PhysicalResourceService;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.CustomerFacingServiceSpecService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.util.*;
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
    final ChannelService channelService;
    final POPlanService poplanService;

    final ProductSpecificationRepository productSpecificationRepository;
    final CustomerFacingServiceSpecService customerFacingServiceSpecService;
    final MarketService marketService;
    final SubMarketService subMarketService;
    final ProductOfferingValidationService validationService;


    @PostMapping("/addProdOff")
    @CacheEvict(value = "productOfferingsCache", allEntries = true)
    public ResponseEntity<?> create(@RequestBody ProductOffering productOffering) {
        try {
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

            productOffering.setMarkets(marketNames);
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

            productOffering.setSubmarkets(submarketNames);

            String familyName = productOffering.getFamilyName();
            if (familyName == null || !familyService.findByNameexist(familyName)) {
                return ResponseEntity.badRequest().body("Family with name '" + familyName + "' does not exist.");
            }

            // Validate channels
            List<String> channels = productOffering.getChannels();
            if (channels == null || channels.isEmpty()) {
                return ResponseEntity.badRequest().body("Channel names list cannot be empty.");
            }

            for (String channel : channels) {
                if (!channelService.findByNameexist(channel)) {
                    return ResponseEntity.badRequest().body("Channel with name '" + channels + "' does not exist.");
                }
            }

            /*List<String> validChannels = eligibilityService.read()
                    .stream()
                    .map(Eligibility::getChannel)
                    .collect(Collectors.toList());

            if (!validChannels.containsAll(productOffering.getChannels())) {
                return ResponseEntity.badRequest().body("Invalid channel(s) in the Product Offering.");
            }*/

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
    public ResponseEntity<?> createProductOfferingDTO(@Valid @RequestBody ProductOfferingDTO dto) {
        try {
            // Validate family name
            String familyName = dto.getFamilyName();
            if (familyName == null || !familyService.findByNameexist(familyName)) {
                return ResponseEntity.badRequest().body("Family with name '" + familyName + "' does not exist.");
            }

            // Validate market name
            String marketName = dto.getMarkets();
            if (marketName == null || !marketService.findByNameexist(marketName)) {
                return ResponseEntity.badRequest().body("Market with name '" + marketName + "' does not exist.");
            }

            // Validate submarket name
            String submarketName = dto.getSubmarkets();
            if (submarketName == null || !subMarketService.findByNameexist(submarketName)) {
                return ResponseEntity.badRequest().body("Submarket with name '" + submarketName + "' does not exist.");
            }

            // Check for duplicate product offering by name
            if (productOfferingService.existsByName(dto.getName())) {
                return ResponseEntity.badRequest().body("A product offering with the same name already exists.");
            }

            // Convert DTO to entity and save
            ProductOffering createdProductOffering = productOfferingService.createProductOfferingDTO(dto);
            return new ResponseEntity<>(createdProductOffering, HttpStatus.CREATED);

        } catch (ProductOfferingAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (Exception e) {
            e.printStackTrace();  // Print stack trace for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred while creating the Product Offering. Error: " + e.getMessage());
        }
    }


    private void ensureRelatedEntitiesExist(ProductOffering productOffering) {
        ensurePOAttributesExists((List<POAttributes>) productOffering.getPoAttributes());
        ensureProductRelationExists(productOffering.getProductRelation());
        ensureCustomerFacingServiceSpecExists(productOffering.getCustomerFacingServiceSpec());
        ensureFamilyExists(productOffering.getFamilyName());
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
            ProductOffering existingProductOffering = productOfferingService.findById(po_code);
            if (existingProductOffering == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Offering with ID " + po_code + " not found.");
            }

            String newName = updatedProductOffering.getName();
            if (!existingProductOffering.getName().equals(newName)) {
                if (productOfferingService.existsByName(newName)) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("Product Offering with the name '" + newName + "' already exists.");
                }
            }

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

            existingProductOffering.setMarkets(marketNames);
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

            existingProductOffering.setSubmarkets(submarketNames);

            String newFamilyName = updatedProductOffering.getFamilyName();
            if (newFamilyName != null && !familyService.findByNameexist(newFamilyName)) {
                return ResponseEntity.badRequest().body("Family with name '" + newFamilyName + "' does not exist.");
            }
            existingProductOffering.setFamilyName(newFamilyName);

            // Validate and set channel names (assuming channels are handled as a list)
            List<String> channels = updatedProductOffering.getChannels();
            if (channels == null || channels.isEmpty()) {
                return ResponseEntity.badRequest().body("Channel names list cannot be empty.");
            }

            List<String> invalidChannels = channels.stream()
                    .filter(channel -> !channelService.findByNameexist(channel))
                    .collect(Collectors.toList());

            if (!invalidChannels.isEmpty()) {
                return ResponseEntity.badRequest().body("Channels with names '" + String.join(", ", invalidChannels) + "' do not exist.");
            }
            existingProductOffering.setChannels(channels);

            List<String> serviceSpecConfigs = updatedProductOffering.getCustomerFacingServiceSpec();
            List<String> missingServices = serviceSpecConfigs.stream()
                    .filter(serviceType -> !customerFacingServiceSpecService.findByNameexist(serviceType))
                    .collect(Collectors.toList());

            if (!missingServices.isEmpty()) {
                return ResponseEntity.badRequest().body("Service(s) with Service Spec Type '" + String.join(", ", missingServices) + "' do not exist.");
            }

            existingProductOffering.setCustomerFacingServiceSpec(serviceSpecConfigs);
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


    List<String> check(ProductOfferingDTO productOfferingDTO) {
        List<String> errors = new ArrayList<>();

        if (productOfferingDTO.getName() == null || productOfferingDTO.getName().isEmpty()) {
            errors.add("Name cannot be empty");
        } else {
            if (!productOfferingDTO.getName().startsWith("po_plan")) {
                errors.add("Name must start with 'po_plan'");
            }
        }


        if (productOfferingDTO.getEffectiveFrom() == null || productOfferingDTO.getName().isEmpty() || productOfferingDTO.getEffectiveTo() == null|| productOfferingDTO.getName().isEmpty()) {
            errors.add("Effective from and effective to dates cannot be null");
        } else {
            if (productOfferingDTO.getEffectiveFrom().compareTo(productOfferingDTO.getEffectiveTo()) >= 0) {
                errors.add("Effective from date must be before the effective to date");
            }
        }

        if (productOfferingDTO.getSubFamily() == null || productOfferingDTO.getSubFamily().isEmpty()) {
            errors.add("Sub Family cannot be empty");
        } else {

            List<String> errorSubFamily = Arrays.asList("offer PrePayed", "offer PostPayed");
            if (!errorSubFamily.contains(productOfferingDTO.getSubFamily())) {
                errors.add("SubFamily must be one of the following: " + String.join(", ", errorSubFamily));
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


        List<SubMarket> validSubMarket = subMarketService.read();
        List<String> submarkets = new ArrayList<>();


        for (SubMarket subMarket : validSubMarket) {
            submarkets.add(subMarket.getName());
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
            if (!productOfferingDTO.getName().startsWith("po_plan")) {
                errors.add("Name must start with 'po_plan'");
            }
        }


        if (productOfferingDTO.getEffectiveFrom() == null || productOfferingDTO.getName().isEmpty() || productOfferingDTO.getEffectiveTo() == null|| productOfferingDTO.getName().isEmpty()) {
            errors.add("Effective from and effective to dates cannot be null");
        } else {
            if (productOfferingDTO.getEffectiveFrom().compareTo(productOfferingDTO.getEffectiveTo()) >= 0) {
                errors.add("Effective from date must be before the effective to date");
            }
        }

        if (productOfferingDTO.getSubFamily() == null || productOfferingDTO.getSubFamily().isEmpty()) {
            errors.add("Sub Family cannot be empty");
        } else {

            List<String> errorSubFamily = Arrays.asList("offer PrePayed", "offer PostPayed");
            if (!errorSubFamily.contains(productOfferingDTO.getSubFamily())) {
                errors.add("SubFamily must be one of the following: " + String.join(", ", errorSubFamily));
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
        List<String> markets = validMarket.stream().map(e -> e.getName().toLowerCase()).toList();

        if (!markets.contains(productOfferingDTO.getMarkets())) {
            errors.add("markets must be one of the following: " + String.join(", ", markets));
        }

        //IF SUBMARKET IS NULL OR SUBMARKET DOES NOT EXIST
        List<SubMarket> validSubMarket = subMarketService.read();
        List<String> submarkets = validSubMarket.stream().map(e -> e.getName().toLowerCase()).toList();

        if (productOfferingDTO.getSubmarkets() == null || productOfferingDTO.getSubmarkets().isEmpty()) {
            errors.add("submarkets cannot be empty");
        } else {

            if (!submarkets.contains(productOfferingDTO.getSubmarkets())) {
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

            var errors = check(productOfferingService.findById(po_code));
            if (errors.isEmpty()) {
                ProductOffering updatedProduct = productOfferingService.changeProductOfferingStatus(po_code);
                return ResponseEntity.ok(updatedProduct);
            }
            return ResponseEntity.badRequest().body(errors);

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
    public ResponseEntity<?> updateProductOfferingDTO(@PathVariable int po_code, @RequestBody ProductOfferingDTO updatedDTO) {
        try {
            ProductOffering existingProductOffering = productOfferingService.findById(po_code);
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

}
