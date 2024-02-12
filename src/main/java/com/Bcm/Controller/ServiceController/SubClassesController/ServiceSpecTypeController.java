package com.Bcm.Controller.ServiceController.SubClassesController;

import com.Bcm.Model.ServiceABE.SubClasses.ServiceSpecType;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.SubClassesSrvc.ServiceSpecTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/ServiceSpecType")
public class ServiceSpecTypeController {

    @Autowired
    private ServiceSpecTypeService serviceSpecTypeService;

    @PostMapping
    public ResponseEntity<?> createServiceSpecType(@RequestBody ServiceSpecType serviceSpecType) {
        try {
            ServiceSpecType createdServiceSpecType = serviceSpecTypeService.create(serviceSpecType);
            return ResponseEntity.ok(createdServiceSpecType);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllServiceSpecTypes() {
        try {
            List<ServiceSpecType> serviceSpecTypes = serviceSpecTypeService.read();
            return ResponseEntity.ok(serviceSpecTypes);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/{SST_code}")
    public ResponseEntity<?> getServiceSpecTypeById(@PathVariable("SST_code") int SST_code) {
        try {
            ServiceSpecType serviceSpecType = serviceSpecTypeService.findById(SST_code);
            return ResponseEntity.ok(serviceSpecType);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @PutMapping("/{SST_code}")
    public ResponseEntity<?> updateServiceSpecType(
            @PathVariable("SST_code") int SST_code,
            @RequestBody ServiceSpecType updatedServiceSpecType) {
        try {
            ServiceSpecType updatedGroup = serviceSpecTypeService.update(SST_code, updatedServiceSpecType);
            return ResponseEntity.ok(updatedGroup);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @DeleteMapping("/{SST_code}")
    public ResponseEntity<?> deleteServiceSpecType(@PathVariable("SST_code") int SST_code) {
        try {
            String resultMessage = serviceSpecTypeService.delete(SST_code);
            return ResponseEntity.ok(resultMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchServiceSpecTypesByKeyword(@RequestParam("name") String name) {
        try {
            List<ServiceSpecType> searchResults = serviceSpecTypeService.searchByKeyword(name);
            return ResponseEntity.ok(searchResults);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}
