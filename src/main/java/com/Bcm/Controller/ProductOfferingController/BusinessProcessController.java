package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Exception.MethodsAlreadyExistsException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ProductOfferingABE.BusinessProcess;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.BusinessProcessService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.persistence.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Tag(name = "Business Process Controller", description = "All of the Business Process's methods")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/BusinessProcess")
public class BusinessProcessController {

    final JdbcTemplate base;
    final BusinessProcessService businessProcessService;

    private static final Logger logger = LoggerFactory.getLogger(BusinessProcessController.class);
    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping("/addBusinessProcess")
    public ResponseEntity<?> createBusinessProcess(@RequestBody BusinessProcess businessProcess) {
        try {
            BusinessProcess createdBusinessProcess = businessProcessService.create(businessProcess);
            return ResponseEntity.ok(createdBusinessProcess);
        } catch (MethodsAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
  }

  @GetMapping("/listbusinessProcess")
  public ResponseEntity<List<BusinessProcess>> getAllBusnissProcess() {
    try {
      List<BusinessProcess> businessProcesses = businessProcessService.read();
      return ResponseEntity.ok(businessProcesses);
    } catch (RuntimeException e) {
      return ResponseEntity.status(500).body(null);
    }

    @GetMapping("/business-process/{productId}")
    public ResponseEntity<Object> getBusinessProcessByProductId(@PathVariable int productId) {
        logger.info("Received request for Product ID: {}", productId);

        String sql =
                "SELECT bp.business_process_id, bp.action, bp.action_description, "
                        + "bp.business_process, po.product_id "
                        + "FROM business_process bp "
                        + "LEFT JOIN product_offering po ON bp.business_process_id = po.business_process_id "
                        + "WHERE po.product_id = :productId AND bp.business_process_id IS NOT NULL "
                        + "ORDER BY bp.business_process ASC";

        List<BusinessProcess> results =
                entityManager
                        .createNativeQuery(sql, BusinessProcess.class)
                        .setParameter("productId", productId)
                        .getResultList();

        if (results.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No BusinessProcess found for productId: " + productId);
        }

        return ResponseEntity.ok(results);
    }


    @PutMapping("/{businessProcess_id}")
    public ResponseEntity<?> updateBusnissProcess(
            @PathVariable("businessProcess_id") int businessProcess_id, @RequestBody BusinessProcess updatedBusnissProcess) {
        try {
            BusinessProcess updatedGroup = businessProcessService.update(businessProcess_id, updatedBusnissProcess);
            return ResponseEntity.ok(updatedGroup);
        } catch (MethodsAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }
  }

  @DeleteMapping("/{businessProcess_id}")
  public ResponseEntity<String> deleteBusnissProcess(@PathVariable("businessProcess_id") int businessProcess_id) {
    try {
      String resultMessage = businessProcessService.delete(businessProcess_id);
      return ResponseEntity.ok(resultMessage);
    } catch (RuntimeException e) {
      return ResponseEntity.status(500).body(null);
    }
  }

  @GetMapping("/search")
  public ResponseEntity<List<BusinessProcess>> searchMethodsByKeyword(
      @RequestParam("businessProcess_name") String businessProcess_name) {
    try {
      List<BusinessProcess> searchResults = businessProcessService.searchByKeyword(businessProcess_name);
      return ResponseEntity.ok(searchResults);
    } catch (RuntimeException e) {
      return ResponseEntity.status(500).body(null);
    }
  }

  @CacheEvict(value = "BusinessProcesssCache", allEntries = true)
  public void invalidateBusinessProcesssCache() {}
}
