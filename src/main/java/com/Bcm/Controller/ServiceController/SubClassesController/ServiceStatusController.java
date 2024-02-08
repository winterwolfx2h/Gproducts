package com.Bcm.Controller.ServiceController.SubClassesController;

import com.Bcm.Model.ServiceABE.SubClasses.ServiceStatus;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.SubClassesSrvc.ServiceStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/ServiceStatus")
public class ServiceStatusController {

    @Autowired
    private ServiceStatusService serviceStatusService;

    @PostMapping
    public ResponseEntity<?> createServiceStatus(@RequestBody ServiceStatus serviceStatus) {
        try {
            ServiceStatus createdServiceStatus = serviceStatusService.create(serviceStatus);
            return ResponseEntity.ok(createdServiceStatus);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllServiceStatuss() {
        try {
            List<ServiceStatus> serviceStatuss = serviceStatusService.read();
            return ResponseEntity.ok(serviceStatuss);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/{SS_code}")
    public ResponseEntity<?> getServiceStatusById(@PathVariable("SS_code") int SS_code) {
        try {
            ServiceStatus serviceStatus = serviceStatusService.findById(SS_code);
            return ResponseEntity.ok(serviceStatus);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @PutMapping("/{SS_code}")
    public ResponseEntity<?> updateServiceStatus(
            @PathVariable("SS_code") int SS_code,
            @RequestBody ServiceStatus updatedServiceStatus) {
        try {
            ServiceStatus updatedGroup = serviceStatusService.update(SS_code, updatedServiceStatus);
            return ResponseEntity.ok(updatedGroup);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @DeleteMapping("/{SS_code}")
    public ResponseEntity<?> deleteServiceStatus(@PathVariable("SS_code") int SS_code) {
        try {
            String resultMessage = serviceStatusService.delete(SS_code);
            return ResponseEntity.ok(resultMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchServiceStatussByKeyword(@RequestParam("name") String name) {
        try {
            List<ServiceStatus> searchResults = serviceStatusService.searchByKeyword(name);
            return ResponseEntity.ok(searchResults);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}
