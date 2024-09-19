package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Exception.DatabaseOperationException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ProductOfferingABE.Tax;
import com.Bcm.Repository.Product.ProductRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.TaxService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Tax Controller", description = "All of the Tax's methods")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/Tax")
@RequiredArgsConstructor
public class TaxController {

  final TaxService taxService;
  final ProductRepository productRepository;
  final JdbcTemplate base;

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
}
