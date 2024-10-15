package com.GestionProduit.Controller;

import com.GestionProduit.Exception.ProduitNotFoundException;
import com.GestionProduit.Model.Category;
import com.GestionProduit.Model.DTO.PriceCalculationRequest;
import com.GestionProduit.Model.DTO.ProductTaxDTO;
import com.GestionProduit.Model.Produit;
import com.GestionProduit.Model.Tax;
import com.GestionProduit.Repository.ProduitRepository;
import com.GestionProduit.Repository.TaxRepository;
import com.GestionProduit.NLP.NLPService;
import com.GestionProduit.Service.ProduitService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Tag(name = "Product Controller", description = "All of the Product's methods")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/produits")
public class ProduitController {

  private static final Logger logger = LoggerFactory.getLogger(ProduitController.class);
  final ProduitService produitService;
  private final ProduitRepository produitRepository;
  final TaxRepository taxRepository;
  final NLPService nlpService;
  private static final String PNF = "Product not found";
  private static final String DEX = " does not exist";

  @PostMapping("/add")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Produit> createProduit(@RequestBody Produit produit) {
    logger.info("Request to create produit: {}", produit);
    try {

      Produit newProduit = new Produit();
      newProduit.setNom(produit.getNom());
      newProduit.setPrix(produit.getPrix());
      newProduit.setQuantite(produit.getQuantite());
      newProduit.setCategoryId(produit.getCategoryId());

      if (produit.getCategoryId() != null) {
        Category category = new Category();
        category.setId(produit.getCategoryId());
        newProduit.setCatp(category);
      }

      Produit savedProduit = produitService.create(newProduit);
      logger.info("Produit created successfully: {}", savedProduit);
      return ResponseEntity.status(HttpStatus.CREATED).body(savedProduit);
    } catch (ProduitNotFoundException e) {
      logger.warn("Failed to create produit: {}. Reason: {}", produit, e.getMessage());
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    } catch (Exception e) {
      logger.error("Error creating produit: {}", e.getMessage(), e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @GetMapping("/listProduit")
  public ResponseEntity<List<Produit>> listProduit() {
    logger.info("Request to list all produits");
    List<Produit> produits = produitService.read();
    logger.info("Retrieved produits: {}", produits);
    return ResponseEntity.ok(produits);
  }

  @GetMapping("/search")
  public ResponseEntity<List<Produit>> searchProduits(@RequestParam String mc) {
    List<String> keywords = nlpService.extractKeywords(mc);
    List<Produit> produits = produitService.searchByKeywords(keywords);

    return ResponseEntity.ok(produits);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Produit> getProduit(@PathVariable Long id) {
    logger.info("Request to get produit with ID: {}", id);
    try {
      Produit produit = produitService.findById(id);
      logger.info("Produit found: {}", produit);
      return ResponseEntity.ok(produit);
    } catch (Exception e) {
      logger.warn( PNF + "with ID: {}. Error: {}", id, e.getMessage());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
  }

  @PutMapping("/updateProduit/{id}")
  public ResponseEntity<Produit> updateProduit(@PathVariable Long id, @RequestBody Produit updatedProduit) {
    logger.info("Request to update produit with ID: {}", id);
    try {
      Produit savedProduit = produitService.update(id, updatedProduit);
      logger.info("Produit updated successfully: {}", savedProduit);
      return ResponseEntity.ok(savedProduit);
    } catch (EntityNotFoundException e) {
      logger.warn("Produit not found with ID: {}. Error: {}", id, e.getMessage());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    } catch (Exception e) {
      logger.error("Error updating produit with ID {}: {}", id, e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @DeleteMapping("/deleteProduit/{id}")
  public ResponseEntity<Object> deleteProduit(@PathVariable Long id) {
    logger.info("Request to delete produit with ID: {}", id);
    try {
      String message = produitService.delete(id);
      logger.info(message);
      return ResponseEntity.ok(message);
    } catch (ProduitNotFoundException e) {
      logger.warn("Error deleting produit: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (Exception e) {
      logger.error("Error deleting produit with ID {}: {}", id, e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @PostMapping("/insertProductTax")
  @Transactional
  public ResponseEntity<String> insertProductTax(@RequestBody List<ProductTaxDTO> productTaxDTO) {
    logger.info("Inserting product tax for {} tax entries", productTaxDTO.size());
    if (productTaxDTO.isEmpty()) {
      logger.error("At least one Product Tax must be provided");
      return ResponseEntity.badRequest().body("At least one Product_Tax must be provided");
    }
    try {
      for (ProductTaxDTO dto : productTaxDTO) {
        Produit product =
            produitRepository
                .findById(dto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product with id " + dto.getProductId() + DEX));
        Tax tax =
            taxRepository
                .findById(dto.getTaxCode())
                .orElseThrow(() -> new IllegalArgumentException("Tax with tax code " + dto.getTaxCode() + DEX));
        product.getTaxes().add(tax);
      }

      for (ProductTaxDTO dto : productTaxDTO) {
        Produit product =
            produitRepository.findById(dto.getProductId()).orElseThrow(() -> new IllegalArgumentException(PNF));
        produitRepository.save(product);
      }

      logger.info("Product tax inserted successfully");
      return ResponseEntity.ok("Product Tax inserted successfully");
    } catch (Exception e) {
      logger.error("Error inserting product tax: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inserting product tax");
    }
  }

  @PostMapping("/calculateTTC")
  public ResponseEntity<Map<String, Object>> calculateTTC(@RequestBody PriceCalculationRequest request) {
    logger.info("Received request to calculate TTC for product price code: {}", request.getId());

    try {
      double originalPrice = produitService.findById((long) request.getId()).getPrix();
      List<Integer> taxCodes = request.getTaxCodes();

      logger.info("Original price retrieved: {}. Tax codes: {}", originalPrice, taxCodes);

      Map<String, Object> result = produitService.calculatePriceWithTax(originalPrice, taxCodes);
      logger.info("TTC calculation successful. Result returned to client.");
      return ResponseEntity.ok(result);
    } catch (ProduitNotFoundException e) {
      logger.warn("Error calculating TTC: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", PNF));
    } catch (Exception e) {
      logger.error("Error occurred while calculating TTC: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(Collections.singletonMap("error", "An error occurred, consult the console for more details."));
    }
  }
}
