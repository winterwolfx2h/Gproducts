package com.Bcm.Controller.ResourceSpecController.ResourceConfigController;

import com.Bcm.Exception.ErrorMessage;
import com.Bcm.Model.ResourceSpecABE.ResourceConfigBE.ResourceConfiguration;
import com.Bcm.Service.Srvc.ResourceSpecService.ResourceConfigService.ResourceConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/ResourceConfiguration")
public class ResourceConfigurationController {

    @Autowired
    ResourceConfigurationService ResourceConfigurationService;

    @PostMapping("/addProdOff")
    public ResponseEntity<ResourceConfiguration> createResourceConfiguration(@RequestBody ResourceConfiguration ResourceConfiguration) {
        ResourceConfiguration createdResourceConfiguration = ResourceConfigurationService.create(ResourceConfiguration);
        return ResponseEntity.ok(createdResourceConfiguration);
    }

    @GetMapping
    public ResponseEntity<List<ResourceConfiguration>> getAllResourceConfigurations() {
        List<ResourceConfiguration> ResourceConfigurations = ResourceConfigurationService.read();
        return ResponseEntity.ok(ResourceConfigurations);
    }

    @GetMapping("/{LRID}")
    public ResponseEntity<ResourceConfiguration> getResourceConfigurationById(@PathVariable("LRID") int LRID) {
        ResourceConfiguration ResourceConfiguration = ResourceConfigurationService.findById(LRID);
        return ResponseEntity.ok(ResourceConfiguration);
    }

    @PutMapping("/{LRID}")

    public ResponseEntity<ResourceConfiguration> updateResourceConfiguration(
            @PathVariable("LRID") int LRID,
            @RequestBody ResourceConfiguration updatedResourceConfiguration) {

        ResourceConfiguration updatedProduct = ResourceConfigurationService.update(LRID, updatedResourceConfiguration);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{LRID}")
    public ResponseEntity<String> deleteResourceConfiguration(@PathVariable("LRID") int LRID) {
        String resultMessage = ResourceConfigurationService.delete(LRID);
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