package com.Bcm.Controller.ResourceSpecController.ResourceConfigController;

import com.Bcm.Exception.ErrorMessage;
import com.Bcm.Model.ResourceSpecABE.ResourceConfigBE.ResourceOrderAndUsage;
import com.Bcm.Service.Srvc.ResourceSpecService.ResourceConfigService.ResourceOrderAndUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/ResourceOrderAndUsage")
public class ResourceOrderAndUsageController {

    @Autowired
    ResourceOrderAndUsageService ResourceOrderAndUsageService;

    @PostMapping("/addProdOff")
    public ResponseEntity<ResourceOrderAndUsage> createResourceOrderAndUsage(@RequestBody ResourceOrderAndUsage ResourceOrderAndUsage) {
        ResourceOrderAndUsage createdResourceOrderAndUsage = ResourceOrderAndUsageService.create(ResourceOrderAndUsage);
        return ResponseEntity.ok(createdResourceOrderAndUsage);
    }

    @GetMapping
    public ResponseEntity<List<ResourceOrderAndUsage>> getAllResourceOrderAndUsages() {
        List<ResourceOrderAndUsage> ResourceOrderAndUsages = ResourceOrderAndUsageService.read();
        return ResponseEntity.ok(ResourceOrderAndUsages);
    }

    @GetMapping("/{ROUID}")
    public ResponseEntity<ResourceOrderAndUsage> getResourceOrderAndUsageById(@PathVariable("ROUID") int ROUID) {
        ResourceOrderAndUsage ResourceOrderAndUsage = ResourceOrderAndUsageService.findById(ROUID);
        return ResponseEntity.ok(ResourceOrderAndUsage);
    }

    @PutMapping("/{ROUID}")

    public ResponseEntity<ResourceOrderAndUsage> updateResourceOrderAndUsage(
            @PathVariable("ROUID") int ROUID,
            @RequestBody ResourceOrderAndUsage updatedResourceOrderAndUsage) {

        ResourceOrderAndUsage updatedProduct = ResourceOrderAndUsageService.update(ROUID, updatedResourceOrderAndUsage);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{ROUID}")
    public ResponseEntity<String> deleteResourceOrderAndUsage(@PathVariable("ROUID") int ROUID) {
        String resultMessage = ResourceOrderAndUsageService.delete(ROUID);
        return ResponseEntity.ok(resultMessage);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}