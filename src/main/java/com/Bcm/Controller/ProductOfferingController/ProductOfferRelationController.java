package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Model.ProductOfferingABE.PrimeryKeyProductRelation;
import com.Bcm.Model.ProductOfferingABE.ProductOfferRelation;
import com.Bcm.Model.ProductOfferingABE.RelationResponse;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferRelationService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/ProductOfferRelation")
@RequiredArgsConstructor
public class ProductOfferRelationController {

    final JdbcTemplate base;
    final ProductOfferRelationService productOfferRelationService;


    String sqlRelation = "SELECT por.product_id, por.related_product_id, po.name, poff.po_type " +
            "FROM public.product_offer_relation por " +
            "JOIN public.product po ON po.product_id = por.product_id " +
            "JOIN public.product_offering poff ON poff.product_id = por.product_id " +
            "WHERE poff.po_type = 'PO-Optional' " +
            "AND por.related_product_id IN " +
            "(SELECT por2.related_product_id " +
            "FROM public.product_offer_relation por2 " +
            "WHERE por2.related_product_id = ?)";


    @GetMapping("/searchRelationName")
    Object searchRelationName(@RequestParam Integer relatedProductId) {
        return base.query(sqlRelation, new Object[]{relatedProductId}, new BeanPropertyRowMapper<>(RelationResponse.class));
    }

    @PostMapping("/addProdOffRelations")
    @CacheEvict(value = "ProdOfferRelationCache", allEntries = true)
    public ResponseEntity<List<ProductOfferRelation>> createProductOfferRelations(@RequestBody List<ProductOfferRelation> productOfferRelations) {
        List<ProductOfferRelation> createdProductOfferRelations = new ArrayList<>();
        for (ProductOfferRelation productOfferRelation : productOfferRelations) {
            createdProductOfferRelations.addAll(productOfferRelationService.create(Collections.singletonList(productOfferRelation)));
        }
        return ResponseEntity.ok(createdProductOfferRelations);
    }

    @GetMapping("/listProdOffrelations")
    @Cacheable(value = "ProdOfferRelationCache")
    public ResponseEntity<?> getAllProductOfferRelations() {
        try {
            List<ProductOfferRelation> productOfferRelations = productOfferRelationService.read();
            return ResponseEntity.ok(productOfferRelations);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProductOfferRelationsByKeyword(@RequestParam("type") String type) {
        try {
            List<ProductOfferRelation> searchResults = productOfferRelationService.searchByKeyword(type);
            return ResponseEntity.ok(searchResults);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/findById")
    public ResponseEntity<?> findProductOfferRelationById(@RequestParam("id") PrimeryKeyProductRelation id) {
        try {
            ProductOfferRelation productOfferRelation = productOfferRelationService.findById(id);
            return ResponseEntity.ok(productOfferRelation);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @DeleteMapping("/deleteById")
    @CacheEvict(value = "ProdOfferRelationCache", allEntries = true)
    public ResponseEntity<?> deleteProductOfferRelationById(@RequestParam("id") PrimeryKeyProductRelation id) {
        try {
            productOfferRelationService.deleteById(id);
            return ResponseEntity.ok("ProductOfferRelation deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

//    @PutMapping("/updateOfferRelation")
//    @CacheEvict(value = "ProdOfferRelationCache", allEntries = true)
//    public ResponseEntity<?> updateProductOfferRelation(@RequestBody List<ProductOfferRelation>  productOfferRelation) {
//        try {
//            ProductOfferRelation updatedProductOfferRelation = productOfferRelationService.update((ProductOfferRelation) productOfferRelation);
//            return ResponseEntity.ok(updatedProductOfferRelation);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }

    @CacheEvict(value = "ProdOfferRelationCache", allEntries = true)
    public void invalidateProdOfferRelationCache() {
    }
}
