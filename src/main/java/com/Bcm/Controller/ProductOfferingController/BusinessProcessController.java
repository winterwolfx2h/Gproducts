package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ProductOfferingABE.BusinessProcess;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.BusinessProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/BusinessProcess")
public class BusinessProcessController {

    final BusinessProcessService businessProcessService;

    @PostMapping
    public ResponseEntity<BusinessProcess> createBusinessProcess(
            @RequestParam Integer type_id,
            @RequestParam Integer product_id,
            @RequestBody BusinessProcess businessProcess) {

        businessProcess.setType_id(type_id);
        businessProcess.setProduct_id(product_id);

        try {
            BusinessProcess createdBusinessProcess = businessProcessService.createBusinessProcess(businessProcess);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBusinessProcess);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    //
//    @PostMapping("/addBusinessProcesses")
//    @CacheEvict(value = "BusinessProcesssCache", allEntries = true)
//    public ResponseEntity<?> create(@RequestBody List<BusinessProcess> businessProcesses) {
//        try {
//            List<BusinessProcess> createdBusinessProcesses = businessProcessService.create(businessProcesses);
//            return ResponseEntity.ok(createdBusinessProcesses);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
//        }
//    }
//
//
//    @GetMapping("/listBusinessProcesss")
//    @Cacheable(value = "BusinessProcesssCache")
//    public ResponseEntity<?> getAllBusinessProcesss() {
//        try {
//            List<BusinessProcess> BusinessProcesss = businessProcessService.read();
//            return ResponseEntity.ok(BusinessProcesss);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
//        }
//    }
//
//    @GetMapping("/{businessProcessId}")
//    public ResponseEntity<?> getBusinessProcessById(@PathVariable("businessProcessId") int businessProcessId) {
//        try {
//            BusinessProcess BusinessProcess = businessProcessService.findById(businessProcessId);
//            return ResponseEntity.ok(BusinessProcess);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
//        }
//    }
//
//    @PutMapping("/{businessProcessId}")
//    @CacheEvict(value = "BusinessProcesssCache", allEntries = true)
//    public ResponseEntity<?> updateBusinessProcess(
//            @PathVariable("businessProcessId") int businessProcessId,
//            @RequestBody BusinessProcess updatedBusinessProcess) {
//        try {
//            BusinessProcess updatedGroup = businessProcessService.update(businessProcessId, updatedBusinessProcess);
//            return ResponseEntity.ok(updatedGroup);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
//        }
//    }
//
//    @DeleteMapping("/{businessProcessId}")
//    @CacheEvict(value = "BusinessProcesssCache", allEntries = true)
//    public ResponseEntity<?> deleteBusinessProcess(@PathVariable("businessProcessId") int businessProcessId) {
//        try {
//            String resultMessage = businessProcessService.delete(businessProcessId);
//            return ResponseEntity.ok(resultMessage);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
//        }
//    }
//
//    @GetMapping("/search")
//    public ResponseEntity<?> searchBusinessProcesssByKeyword(@RequestParam("bussinessProcType") String bussinessProcType) {
//        try {
//            List<BusinessProcess> searchResults = businessProcessService.searchByKeyword(bussinessProcType);
//            return ResponseEntity.ok(searchResults);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
//        }
//    }
//
    @CacheEvict(value = "BusinessProcesssCache", allEntries = true)
    public void invalidateBusinessProcesssCache() {
    }
}
