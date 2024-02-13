package com.Bcm.Controller.ResourceSpecController.PhysicalResourceContoller;

import com.Bcm.Exception.ErrorMessage;
import com.Bcm.Model.ResourceSpecABE.PhysicalResourceBE.PhysicalComponent;
import com.Bcm.Service.Srvc.ResourceSpecService.PhysicalResourceService.PhysicalComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.List;
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/PhysicalComponent")
public class PhysicalComponentController {

    @Autowired
    PhysicalComponentService PhysicalComponentService;

    @PostMapping("/addProdOff")
    public ResponseEntity<PhysicalComponent> createPhysicalComponent(@RequestBody PhysicalComponent PhysicalComponent) {
        PhysicalComponent createdPhysicalComponent = PhysicalComponentService.create(PhysicalComponent);
        return ResponseEntity.ok(createdPhysicalComponent);
    }

    @GetMapping
    public ResponseEntity<List<PhysicalComponent>> getAllPhysicalComponents() {
        List<PhysicalComponent> PhysicalComponents = PhysicalComponentService.read();
        return ResponseEntity.ok(PhysicalComponents);
    }

    @GetMapping("/{PCpID}")
    public ResponseEntity<PhysicalComponent> getPhysicalComponentById(@PathVariable("PCpID") int PCpID) {
        PhysicalComponent PhysicalComponent = PhysicalComponentService.findById(PCpID);
        return ResponseEntity.ok(PhysicalComponent);
    }

    @PutMapping("/{PCpID}")

    public ResponseEntity<PhysicalComponent> updatePhysicalComponent(
            @PathVariable("PCpID") int PCpID,
            @RequestBody PhysicalComponent updatedPhysicalComponent) {

        PhysicalComponent updatedProduct = PhysicalComponentService.update(PCpID, updatedPhysicalComponent);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{PCpID}")
    public ResponseEntity<String> deletePhysicalComponent(@PathVariable("PCpID") int PCpID) {
        String resultMessage = PhysicalComponentService.delete(PCpID);
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
