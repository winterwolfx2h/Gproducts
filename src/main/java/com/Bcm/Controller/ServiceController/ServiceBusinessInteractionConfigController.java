package com.Bcm.Controller.ServiceController;

import com.Bcm.Model.ServiceABE.ServiceBusinessInteractionConfig;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.ServiceBusinessInteractionConfigService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<ServiceBusinessInteractionConfig> createServiceBusinessInteractionConfig(@RequestBody ServiceBusinessInteractionConfig ServiceBusinessInteractionConfig) {
        ServiceBusinessInteractionConfig createdServiceBusinessInteractionConfig = serviceBusinessInteractionConfigService.create(ServiceBusinessInteractionConfig);
        return ResponseEntity.ok(createdServiceBusinessInteractionConfig);
    }

    @GetMapping
    public ResponseEntity<List<ServiceBusinessInteractionConfig>> getAllServiceBusinessInteractionConfigs() {
        List<ServiceBusinessInteractionConfig> ServiceBusinessInteractionConfigs = serviceBusinessInteractionConfigService.read();
        return ResponseEntity.ok(ServiceBusinessInteractionConfigs);
    }

    @GetMapping("/{SBIC_code}")
    public ResponseEntity<ServiceBusinessInteractionConfig> getServiceBusinessInteractionConfigById(@PathVariable("SBIC_code") int SBIC_code) {
        ServiceBusinessInteractionConfig ServiceBusinessInteractionConfig = serviceBusinessInteractionConfigService.findById(SBIC_code);
        return ResponseEntity.ok(ServiceBusinessInteractionConfig);
    }

    @PutMapping("/{SBIC_code}")
    public ResponseEntity<ServiceBusinessInteractionConfig> updateServiceBusinessInteractionConfig(
            @PathVariable("SBIC_code") int SBIC_code,
            @RequestBody ServiceBusinessInteractionConfig updatedServiceBusinessInteractionConfig) {

        ServiceBusinessInteractionConfig updatedProduct = serviceBusinessInteractionConfigService.update(SBIC_code, updatedServiceBusinessInteractionConfig);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{SBIC_code}")
    public ResponseEntity<String> deleteServiceBusinessInteractionConfig(@PathVariable("SBIC_code") int SBIC_code) {
        String resultMessage = serviceBusinessInteractionConfigService.delete(SBIC_code);
        return ResponseEntity.ok(resultMessage);
    }

}
