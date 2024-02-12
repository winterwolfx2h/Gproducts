package com.Bcm.Controller.ServiceController;

import com.Bcm.Model.ServiceABE.ServiceDocumentConfig;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.ServiceDocumentConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/service-document-config")
public class ServiceDocumentConfigController {

    @Autowired
    private ServiceDocumentConfigService serviceDocumentConfigService;

    @PostMapping
    public ResponseEntity<?> createServiceDocumentConfig(@RequestBody ServiceDocumentConfig ServiceDocumentConfig) {
        try {
            ServiceDocumentConfig createdServiceDocumentConfig = serviceDocumentConfigService.create(ServiceDocumentConfig);
            return ResponseEntity.ok(createdServiceDocumentConfig);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllServiceDocumentConfigs() {
        try {
            List<ServiceDocumentConfig> ServiceDocumentConfigs = serviceDocumentConfigService.read();
            return ResponseEntity.ok(ServiceDocumentConfigs);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/{SDC_code}")
    public ResponseEntity<?> getServiceDocumentConfigById(@PathVariable("SDC_code") int SDC_code) {
        try {
            ServiceDocumentConfig ServiceDocumentConfig = serviceDocumentConfigService.findById(SDC_code);
            return ResponseEntity.ok(ServiceDocumentConfig);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @PutMapping("/{SDC_code}")
    public ResponseEntity<?> updateServiceDocumentConfig(
            @PathVariable("SDC_code") int SDC_code,
            @RequestBody ServiceDocumentConfig updatedServiceDocumentConfig) {
        try {
            ServiceDocumentConfig updatedProduct = serviceDocumentConfigService.update(SDC_code, updatedServiceDocumentConfig);
            return ResponseEntity.ok(updatedProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @DeleteMapping("/{SDC_code}")
    public ResponseEntity<?> deleteServiceDocumentConfig(@PathVariable("SDC_code") int SDC_code) {
        try {
            String resultMessage = serviceDocumentConfigService.delete(SDC_code);
            return ResponseEntity.ok(resultMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}
