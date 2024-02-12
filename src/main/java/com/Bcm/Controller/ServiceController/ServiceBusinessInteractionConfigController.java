package com.Bcm.Controller.ServiceController;

import com.Bcm.Model.ServiceABE.ServiceBusinessInteractionConfig;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.ServiceBusinessInteractionConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/service-business-interaction-config")
public class ServiceBusinessInteractionConfigController {

    @Autowired
    private ServiceBusinessInteractionConfigService serviceBusinessInteractionConfigService;

    @PostMapping
    public ResponseEntity<?> createServiceBusinessInteractionConfig(@RequestBody ServiceBusinessInteractionConfig ServiceBusinessInteractionConfig) {
        try {
            ServiceBusinessInteractionConfig createdServiceBusinessInteractionConfig = serviceBusinessInteractionConfigService.create(ServiceBusinessInteractionConfig);
            return ResponseEntity.ok(createdServiceBusinessInteractionConfig);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllServiceBusinessInteractionConfigs() {
        try {
            List<ServiceBusinessInteractionConfig> ServiceBusinessInteractionConfigs = serviceBusinessInteractionConfigService.read();
            return ResponseEntity.ok(ServiceBusinessInteractionConfigs);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/{SBIC_code}")
    public ResponseEntity<?> getServiceBusinessInteractionConfigById(@PathVariable("SBIC_code") int SBIC_code) {
        try {
            ServiceBusinessInteractionConfig ServiceBusinessInteractionConfig = serviceBusinessInteractionConfigService.findById(SBIC_code);
            return ResponseEntity.ok(ServiceBusinessInteractionConfig);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @PutMapping("/{SBIC_code}")
    public ResponseEntity<?> updateServiceBusinessInteractionConfig(
            @PathVariable("SBIC_code") int SBIC_code,
            @RequestBody ServiceBusinessInteractionConfig updatedServiceBusinessInteractionConfig) {
        try {
            ServiceBusinessInteractionConfig updatedProduct = serviceBusinessInteractionConfigService.update(SBIC_code, updatedServiceBusinessInteractionConfig);
            return ResponseEntity.ok(updatedProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @DeleteMapping("/{SBIC_code}")
    public ResponseEntity<?> deleteServiceBusinessInteractionConfig(@PathVariable("SBIC_code") int SBIC_code) {
        try {
            String resultMessage = serviceBusinessInteractionConfigService.delete(SBIC_code);
            return ResponseEntity.ok(resultMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}
