package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Exception.DatabaseOperationException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ProductOfferingABE.Tax;
import com.Bcm.Repository.Product.ProductRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.TaxService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Tag(name = "Tax Controller", description = "All of the Tax's methods")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/Tax")
@RequiredArgsConstructor
public class TaxController {

  final TaxService taxService;
  final ProductRepository productRepository;
  final JdbcTemplate base;
  private static final Logger logger = LoggerFactory.getLogger(TaxController.class);
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

  @GetMapping("/product/{productId}")
  public ResponseEntity<Object> getTaxForProduct(@PathVariable int productId) {

    logger.info("Received request for Product ID: {}", productId);

    String sql =
        "select p.product_id, t.tax_code, t.name, t.customer_category, t.tax_type, t.value, t.external_id,"
            + " t.valid_from, t.valid_to  FROM tax t  JOIN product_tax pt ON t.tax_code = pt.tax_code  JOIN product p"
            + " ON pt.product_id = p.product_id  WHERE p.product_id = :productId  AND t.tax_code IS  NOT NULL Order BY"
            + " p.product_id; ";

    List<Tax> results =
        entityManager.createNativeQuery(sql, Tax.class).setParameter("productId", productId).getResultList();

    if (results.isEmpty()) {

      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Tax found for productId: " + productId);
    }

    return ResponseEntity.ok(results);
  }
}
