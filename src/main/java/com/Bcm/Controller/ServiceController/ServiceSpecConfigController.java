package com.Bcm.Controller.ServiceController;

import com.Bcm.Model.ServiceABE.ServiceSpecConfig;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.ServiceSpecConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/service-spec-config")
public class ServiceSpecConfigController {

    @Autowired
    private ServiceSpecConfigService serviceSpecConfigService;

    @PostMapping
    public ResponseEntity<ServiceSpecConfig> createServiceSpecConfig(@RequestBody ServiceSpecConfig ServiceSpecConfig) {
        ServiceSpecConfig createdServiceSpecConfig = serviceSpecConfigService.create(ServiceSpecConfig);
        return ResponseEntity.ok(createdServiceSpecConfig);
    }

    @GetMapping
    public ResponseEntity<List<ServiceSpecConfig>> getAllServiceSpecConfigs() {
        List<ServiceSpecConfig> ServiceSpecConfigs = serviceSpecConfigService.read();
        return ResponseEntity.ok(ServiceSpecConfigs);
    }

    @GetMapping("/{SSC_code}")
    public ResponseEntity<ServiceSpecConfig> getServiceSpecConfigById(@PathVariable("SSC_code") int SSC_code) {
        ServiceSpecConfig ServiceSpecConfig = serviceSpecConfigService.findById(SSC_code);
        return ResponseEntity.ok(ServiceSpecConfig);
    }

    @PutMapping("/{SSC_code}")
    public ResponseEntity<ServiceSpecConfig> updateServiceSpecConfig(
            @PathVariable("SSC_code") int SSC_code,
            @RequestBody ServiceSpecConfig updatedServiceSpecConfig) {

        ServiceSpecConfig updatedProduct = serviceSpecConfigService.update(SSC_code, updatedServiceSpecConfig);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{SSC_code}")
    public ResponseEntity<String> deleteServiceSpecConfig(@PathVariable("SSC_code") int SSC_code) {
        String resultMessage = serviceSpecConfigService.delete(SSC_code);
        return ResponseEntity.ok(resultMessage);
    }

}
