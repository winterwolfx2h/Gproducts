package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Exception.MethodsAlreadyExistsException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ProductOfferingABE.BusinessProcess;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.BusinessProcessService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Business Process Controller", description = "All of the Business Process's methods")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/BusinessProcess")
public class BusinessProcessController {

  final JdbcTemplate base;
  final BusinessProcessService businessProcessService;
  @PersistenceContext private EntityManager entityManager;

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

  @GetMapping("/listbusinessProcess")
  public ResponseEntity<List<BusinessProcess>> getAllBusnissProcess() {
    try {
      List<BusinessProcess> businessProcesses = businessProcessService.read();
      return ResponseEntity.ok(businessProcesses);
    } catch (RuntimeException e) {
      return ResponseEntity.status(500).body(null);
    }
  }

  @GetMapping("/searchProductAndBusinessProcess")
  public String searchProductAndBusinessProcess(@RequestParam Integer productId) {
    String jpqlQuery =
        "SELECT po.name AS productName, bp.name AS businessProcessName "
            + "FROM ProductOffering po "
            + "JOIN BusinessProcess bp "
            + "ON po.businessProcess_id = bp.businessProcess_id "
            + "WHERE po.Product_id = :productId";

    TypedQuery<Object[]> query = entityManager.createQuery(jpqlQuery, Object[].class);
    query.setParameter("productId", productId);

    List<Object[]> results = query.getResultList();

    if (results.isEmpty()) {
      return "No data found for Product ID: " + productId;
    } else {
      Object[] result = results.get(0);
      String productName = (String) result[0];
      String businessProcessName = (String) result[1];
      return "Product Name: " + productName + ", Business Process Name: " + businessProcessName;
    }
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
  public ResponseEntity<List<BusinessProcess>> searchMethodsByKeyword(@RequestParam("name") String name) {
    try {
      List<BusinessProcess> searchResults = businessProcessService.searchByKeyword(name);
      return ResponseEntity.ok(searchResults);
    } catch (RuntimeException e) {
      return ResponseEntity.status(500).body(null);
    }
  }

  @CacheEvict(value = "BusinessProcesssCache", allEntries = true)
  public void invalidateBusinessProcesssCache() {}
}
