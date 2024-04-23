package com.Bcm.Controller.ServiceController;

import com.Bcm.Exception.BusinessLogicException;
import com.Bcm.Exception.ServiceAlreadyExistsException;
import com.Bcm.Exception.ServiceLogicException;
import com.Bcm.Model.ProductOfferingABE.POPlan;
import com.Bcm.Model.ServiceABE.ServiceSpecConfig;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.ServiceSpecConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/service-spec-config")
public class ServiceSpecConfigController {

    @Autowired
    private ServiceSpecConfigService serviceSpecConfigService;

    @PostMapping("/addServiceSpecConfig")
    public ResponseEntity<?> createServiceSpecConfig(@RequestBody ServiceSpecConfig ServiceSpecConfig) {
        try {
            ServiceSpecConfig createdServiceSpecConfig = serviceSpecConfigService.create(ServiceSpecConfig);
            return ResponseEntity.ok(createdServiceSpecConfig);
        } catch (ServiceAlreadyExistsException e) {
            return ResponseEntity.badRequest().body("Service with the same name already exists");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/listServiceSpecConfigs")
    public ResponseEntity<?> getAllServiceSpecConfigs() {
        try {
            List<ServiceSpecConfig> ServiceSpecConfigs = serviceSpecConfigService.read();
            return ResponseEntity.ok(ServiceSpecConfigs);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/{SSC_code}")
    public ResponseEntity<?> getServiceSpecConfigById(@PathVariable("SSC_code") int SSC_code) {
        try {
            ServiceSpecConfig ServiceSpecConfig = serviceSpecConfigService.findById(SSC_code);
            return ResponseEntity.ok(ServiceSpecConfig);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @PutMapping("/{SSC_code}")
    public ResponseEntity<?> updateServiceSpecConfig(
            @PathVariable("SSC_code") int SSC_code,
            @RequestBody ServiceSpecConfig updatedServiceSpecConfig) {
        try {
            ServiceSpecConfig updatedProduct = serviceSpecConfigService.update(SSC_code, updatedServiceSpecConfig);
            return ResponseEntity.ok(updatedProduct);
        } catch (ServiceAlreadyExistsException e) {
            return ResponseEntity.badRequest().body("Service with the same name already exists");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @DeleteMapping("/{SSC_code}")
    public ResponseEntity<?> deleteServiceSpecConfig(@PathVariable("SSC_code") int SSC_code) {
        try {
            String resultMessage = serviceSpecConfigService.delete(SSC_code);
            return ResponseEntity.ok(resultMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @PutMapping("/changeStatus/{SSC_code}")
    public ResponseEntity<?> changeServiceSpecConfigStatus(@PathVariable int SSC_code) {
        try {
            ServiceSpecConfig updatedService = serviceSpecConfigService.changeServiceStatus(SSC_code);
            return ResponseEntity.ok(updatedService);

        } catch (ServiceLogicException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }


}
