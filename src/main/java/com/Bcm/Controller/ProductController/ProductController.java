package com.Bcm.Controller.ProductController;

import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.Product.Product;
import com.Bcm.Model.Product.ProductSpecificationDTO;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferingService;
import com.Bcm.Service.Srvc.ProductSrvc.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/Product")
public class ProductController {


    final ProductService productService;
    final ProductOfferingService productOfferingService;

    @GetMapping("/ProductList")
    @Cacheable(value = "productCache")
    public ResponseEntity<?> getAllProduct() {
        try {
            List<Product> product = productService.read();
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/productsWithPOBasicPoType")
    public ResponseEntity<?> getProductsByPOBasicPoType() {
        try {
            String poType = "PO-Basic";
            List<ProductOffering> productOfferings = productOfferingService.findByPoType(poType);

            if (productOfferings.isEmpty()) {
                throw new ResourceNotFoundException("No products found for poType: " + poType);
            }

            List<Object> products = productOfferings.stream()
                    .map(productOffering -> {
                        Product product = productOffering.convertToProduct();
                        ProductOffering productOfferingDTO = new ProductOffering();
                        productOfferingDTO.setProduct_id(product.getProduct_id());
                        productOfferingDTO.setName(product.getName());
                        productOfferingDTO.setEffectiveFrom(product.getEffectiveFrom());
                        productOfferingDTO.setEffectiveTo(product.getEffectiveTo());
                        productOfferingDTO.setDescription(product.getDescription());
                        productOfferingDTO.setDetailedDescription(product.getDetailedDescription());
                        productOfferingDTO.setPoType(productOffering.getPoType());
                        productOfferingDTO.setFamilyName(product.getFamilyName());
                        productOfferingDTO.setSubFamily(product.getSubFamily());
                        productOfferingDTO.setParent(productOffering.getParent());
                        productOfferingDTO.setStatus(productOffering.getStatus());
                        productOfferingDTO.setCategory(productOffering.getCategory());
                        productOfferingDTO.setChannels(productOffering.getChannels());
                        productOfferingDTO.setPoParent_Child(productOffering.getPoParent_Child());
                        //productOfferingDTO.setExternalLinkId(productOffering.getExternalLinkId());
                        productOfferingDTO.setCustomerFacingServiceSpec(productOffering.getCustomerFacingServiceSpec());
                        productOfferingDTO.setMarkets(productOffering.getMarkets());
                        productOfferingDTO.setSubmarkets(productOffering.getSubmarkets());
                        productOfferingDTO.setBS_externalId(productOffering.getBS_externalId());
                        productOfferingDTO.setCS_externalId(productOffering.getCS_externalId());
                        productOfferingDTO.setPoAttributes(productOffering.getPoAttributes());
                        productOfferingDTO.setProductRelation(productOffering.getProductRelation());
                        productOfferingDTO.setPhysicalResource(productOffering.getPhysicalResource());
                        productOfferingDTO.setBusinessProcess(productOffering.getBusinessProcess());
                        return productOfferingDTO;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(products);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/searchByFamilyName")
    public ResponseEntity<?> searchProductsByFamilyName(@RequestParam("familyName") String familyName) {
        List<Product> searchResults = productService.searchByFamilyName(familyName);
        return ResponseEntity.ok(searchResults);
    }

    @GetMapping("/searchSpec")
    public ResponseEntity<?> searchSpec(@RequestParam String name) {
        try {
            List<Product> products = productService.searchByKeyword(name);
            List<ProductSpecificationDTO> dtos = new ArrayList<>();

            for (Product product : products) {
                if (product instanceof ProductOffering) {
                    ProductOffering productOffering = (ProductOffering) product;
                    //ProductSpecification productSpecification = productOffering.getProductSpecification();
                    ProductSpecificationDTO dto = new ProductSpecificationDTO();
                    dto.setFamilyName(productOffering.getFamilyName());
                    dto.setSubFamily(productOffering.getSubFamily());
                    //dto.setCategory(productOffering.getProductSpecification().getCategory());
                    //dto.setPoPlanName(productSpecification.getPoPlanName());
                    //dto.setBS_externalId(productSpecification.getBS_externalId());
                    //dto.setCS_externalId(productSpecification.getCS_externalId());
                    dtos.add(dto);
                }
            }

            return ResponseEntity.ok(dtos);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }


    @GetMapping("/productsWithPOPLANPoType")
    public ResponseEntity<?> getProductsByPOPlanPoType() {
        try {
            String poType = "PO-PLAN";
            List<ProductOffering> productOfferings = productOfferingService.findByPoType(poType);

            if (productOfferings.isEmpty()) {
                throw new ResourceNotFoundException("No products found for poType: " + poType);
            }

            List<Object> products = productOfferings.stream()
                    .map(productOffering -> {
                        Product product = productOffering.convertToProduct();
                        ProductOffering productOfferingDTO = new ProductOffering();
                        productOfferingDTO.setProduct_id(product.getProduct_id());
                        productOfferingDTO.setName(product.getName());
                        productOfferingDTO.setEffectiveFrom(product.getEffectiveFrom());
                        productOfferingDTO.setEffectiveTo(product.getEffectiveTo());
                        productOfferingDTO.setDescription(product.getDescription());
                        productOfferingDTO.setDetailedDescription(product.getDetailedDescription());
                        productOfferingDTO.setPoType(productOffering.getPoType());
                        productOfferingDTO.setFamilyName(product.getFamilyName());
                        productOfferingDTO.setSubFamily(product.getSubFamily());
                        productOfferingDTO.setParent(productOffering.getParent());
                        productOfferingDTO.setStatus(productOffering.getStatus());
                        productOfferingDTO.setCategory(productOffering.getCategory());
                        productOfferingDTO.setChannels(productOffering.getChannels());
                        productOfferingDTO.setPoParent_Child(productOffering.getPoParent_Child());
                        //productOfferingDTO.setExternalLinkId(productOffering.getExternalLinkId());
                        productOfferingDTO.setCustomerFacingServiceSpec(productOffering.getCustomerFacingServiceSpec());
                        productOfferingDTO.setMarkets(productOffering.getMarkets());
                        productOfferingDTO.setSubmarkets(productOffering.getSubmarkets());
                        productOfferingDTO.setBS_externalId(productOffering.getBS_externalId());
                        productOfferingDTO.setCS_externalId(productOffering.getCS_externalId());
                        productOfferingDTO.setPoAttributes(productOffering.getPoAttributes());
                        productOfferingDTO.setProductRelation(productOffering.getProductRelation());
                        productOfferingDTO.setPhysicalResource(productOffering.getPhysicalResource());
                        productOfferingDTO.setBusinessProcess(productOffering.getBusinessProcess());
                        return productOfferingDTO;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(products);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}
