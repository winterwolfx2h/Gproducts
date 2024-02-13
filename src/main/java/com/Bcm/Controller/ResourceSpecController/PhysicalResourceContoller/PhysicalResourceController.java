package com.Bcm.Controller.ResourceSpecController.PhysicalResourceContoller;

import com.Bcm.Exception.ErrorMessage;
import com.Bcm.Model.ResourceSpecABE.PhysicalResourceBE.PhysicalResource;
import com.Bcm.Service.Srvc.ResourceSpecService.PhysicalResourceService.PhysicalResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.List;
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/product-offerings")
public class PhysicalResourceController {

    @Autowired
    PhysicalResourceService PhysicalResourceService;

    @PostMapping("/addProdOff")
    public ResponseEntity<PhysicalResource> createPhysicalResource(@RequestBody PhysicalResource PhysicalResource) {
        PhysicalResource createdPhysicalResource = PhysicalResourceService.create(PhysicalResource);
        return ResponseEntity.ok(createdPhysicalResource);
    }

    @GetMapping
    public ResponseEntity<List<PhysicalResource>> getAllPhysicalResources() {
        List<PhysicalResource> PhysicalResources = PhysicalResourceService.read();
        return ResponseEntity.ok(PhysicalResources);
    }

    @GetMapping("/{PRID}")
    public ResponseEntity<PhysicalResource> getPhysicalResourceById(@PathVariable("PRID") int PRID) {
        PhysicalResource PhysicalResource = PhysicalResourceService.findById(PRID);
        return ResponseEntity.ok(PhysicalResource);
    }

    @PutMapping("/{PRID}")

    public ResponseEntity<PhysicalResource> updatePhysicalResource(
            @PathVariable("PRID") int PRID,
            @RequestBody PhysicalResource updatedPhysicalResource) {

        PhysicalResource updatedProduct = PhysicalResourceService.update(PRID, updatedPhysicalResource);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{PRID}")
    public ResponseEntity<String> deletePhysicalResource(@PathVariable("PRID") int PRID) {
        String resultMessage = PhysicalResourceService.delete(PRID);
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