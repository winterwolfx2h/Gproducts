package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Exception.MethodsAlreadyExistsException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ProductOfferingABE.BusinessProcess;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.BusinessProcessService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Tag(name = "Business Process Controller", description = "All of the Business Process's methods")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/BusinessProcess")
public class BusinessProcessController {

    final JdbcTemplate base;
    final BusinessProcessService businessProcessService;
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
    public ResponseEntity<String> searchProductAndBusinessProcess(@RequestParam Integer productId) {
        // Check if product exists
        String checkProductQuery = "SELECT COUNT(po) FROM ProductOffering po WHERE po.Product_id = :productId";
        TypedQuery<Long> checkProduct = entityManager.createQuery(checkProductQuery, Long.class);
        checkProduct.setParameter("productId", productId);

        Long productCount = checkProduct.getSingleResult();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseJson = mapper.createObjectNode();

        if (productCount == 0) {
            responseJson.put("message", "Product does not exist for Product ID: " + productId);
            ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
            try {
                return new ResponseEntity<>(writer.writeValueAsString(responseJson), HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                return new ResponseEntity<>("{\"error\":\"Failed to generate JSON\"}", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            String jpqlQuery =
                    "SELECT bp.businessProcess_id AS businessProcess_id, bp.businessProcess_name AS businessProcessName "
                            + "FROM ProductOffering po "
                            + "JOIN BusinessProcess bp "
                            + "ON po.businessProcess_id = bp.businessProcess_id "
                            + "WHERE po.Product_id = :productId";

            TypedQuery<Object[]> query = entityManager.createQuery(jpqlQuery, Object[].class);
            query.setParameter("productId", productId);

            List<Object[]> results = query.getResultList();

            if (results.isEmpty()) {
                responseJson.put("message", "No associated business processes found for Product ID: " + productId);
                ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
                try {
                    return new ResponseEntity<>(writer.writeValueAsString(responseJson), HttpStatus.NOT_FOUND);
                } catch (Exception e) {
                    return new ResponseEntity<>("{\"error\":\"Failed to generate JSON\"}", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                Object[] result = results.get(0);
                Integer businessProcess_id = (Integer) result[0];
                String businessProcessName = (String) result[1];
                responseJson.put("businessProcess_id", businessProcess_id);
                responseJson.put("businessProcessName", businessProcessName);

                ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
                try {
                    return new ResponseEntity<>(writer.writeValueAsString(responseJson), HttpStatus.OK);
                } catch (Exception e) {
                    return new ResponseEntity<>("{\"error\":\"Failed to generate JSON\"}", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
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
    public ResponseEntity<List<BusinessProcess>> searchMethodsByKeyword(@RequestParam("businessProcess_name") String businessProcess_name) {
        try {
            List<BusinessProcess> searchResults = businessProcessService.searchByKeyword(businessProcess_name);
            return ResponseEntity.ok(searchResults);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @CacheEvict(value = "BusinessProcesssCache", allEntries = true)
    public void invalidateBusinessProcesssCache() {
    }
}