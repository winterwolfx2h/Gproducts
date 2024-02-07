package com.Bcm.Controller.ServiceController;

import com.Bcm.Model.ServiceABE.ServiceDocumentConfig;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.ServiceDocumentConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/service-document-config")
public class ServiceDocumentConfigController {

    @Autowired
    private ServiceDocumentConfigService serviceDocumentConfigService;

    @PostMapping
    public ResponseEntity<ServiceDocumentConfig> createServiceDocumentConfig(@RequestBody ServiceDocumentConfig ServiceDocumentConfig) {
        ServiceDocumentConfig createdServiceDocumentConfig = serviceDocumentConfigService.create(ServiceDocumentConfig);
        return ResponseEntity.ok(createdServiceDocumentConfig);
    }

    @GetMapping
    public ResponseEntity<List<ServiceDocumentConfig>> getAllServiceDocumentConfigs() {
        List<ServiceDocumentConfig> ServiceDocumentConfigs = serviceDocumentConfigService.read();
        return ResponseEntity.ok(ServiceDocumentConfigs);
    }

    @GetMapping("/{SDC_code}")
    public ResponseEntity<ServiceDocumentConfig> getServiceDocumentConfigById(@PathVariable("SDC_code") int SDC_code) {
        ServiceDocumentConfig ServiceDocumentConfig = serviceDocumentConfigService.findById(SDC_code);
        return ResponseEntity.ok(ServiceDocumentConfig);
    }

    @PutMapping("/{SDC_code}")
    public ResponseEntity<ServiceDocumentConfig> updateServiceDocumentConfig(
            @PathVariable("SDC_code") int SDC_code,
            @RequestBody ServiceDocumentConfig updatedServiceDocumentConfig) {

        ServiceDocumentConfig updatedProduct = serviceDocumentConfigService.update(SDC_code, updatedServiceDocumentConfig);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{SDC_code}")
    public ResponseEntity<String> deleteServiceDocumentConfig(@PathVariable("SDC_code") int SDC_code) {
        String resultMessage = serviceDocumentConfigService.delete(SDC_code);
        return ResponseEntity.ok(resultMessage);
    }

}
