package com.GestionProduit.Controller;

import com.GestionProduit.Exception.DatabaseOperationException;
import com.GestionProduit.Exception.ResourceNotFoundException;
import com.GestionProduit.Model.Tax;
import com.GestionProduit.Repository.ProduitRepository;
import com.GestionProduit.Service.TaxService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Tax Controller", description = "All of the Tax's methods")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/Tax")
@RequiredArgsConstructor
public class TaxController {

  private static final Logger logger = LoggerFactory.getLogger(TaxController.class);
  final TaxService taxService;
  final ProduitRepository produitRepository;
  @PersistenceContext private EntityManager entityManager;

  @PostMapping("/addTax")
  public ResponseEntity<?> createTax(@RequestBody Tax tax) {
    try {

      Tax createdTax = taxService.create(tax);
      return ResponseEntity.status(HttpStatus.CREATED).body(createdTax);
    } catch (DatabaseOperationException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @GetMapping("/listTax")
  public ResponseEntity<List<Tax>> getAllTaxes() {
    try {
      List<Tax> taxes = taxService.read();
      return ResponseEntity.ok(taxes);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @GetMapping("/{taxCode}")
  public ResponseEntity<Tax> getTaxById(@PathVariable("taxCode") int taxCode) {
    try {
      Tax tax = taxService.findById(taxCode);
      return ResponseEntity.ok(tax);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
  }

  @PutMapping("/{taxCode}")
  public ResponseEntity<?> updateTax(@PathVariable("taxCode") int taxCode, @RequestBody Tax updatedTax) {
    try {
      Tax updatedGroup = taxService.update(taxCode, updatedTax);
      return ResponseEntity.ok(updatedGroup);
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
    }
  }

  @DeleteMapping("/{taxCode}")
  public ResponseEntity<String> deleteTax(@PathVariable("taxCode") int taxCode) {
    try {
      String resultMessage = taxService.delete(taxCode);
      return ResponseEntity.ok(resultMessage);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @GetMapping("/search")
  public ResponseEntity<List<Tax>> searchTaxByName(@RequestParam("name") String name) {
    try {
      List<Tax> searchResults = taxService.searchByName(name);
      return ResponseEntity.ok(searchResults);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @GetMapping("/product/{id}")
  public ResponseEntity<List<Tax>> getTaxForProduct(@PathVariable long id) {
    logger.info("Received request for Product ID: {}", id);

    List<Tax> results = taxService.findTaxesByProductId(id);

    if (results.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    return ResponseEntity.ok(results);
  }
}
