package com.Bcm.Controller.ResourceSpecController.PhysicalResourceContoller;

import com.Bcm.Exception.ErrorMessage;
import com.Bcm.Model.ResourceSpecABE.PhysicalResourceBE.PhysicalConnector;
import com.Bcm.Service.Srvc.ResourceSpecService.PhysicalResourceService.PhysicalConnectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/PhysicalConnector")
public class PhysicalConnectorController {

    @Autowired
    PhysicalConnectorService PhysicalConnectorService;

    @PostMapping("/addProdOff")
    public ResponseEntity<PhysicalConnector> createPhysicalConnector(@RequestBody PhysicalConnector PhysicalConnector) {
        PhysicalConnector createdPhysicalConnector = PhysicalConnectorService.create(PhysicalConnector);
        return ResponseEntity.ok(createdPhysicalConnector);
    }

    @GetMapping
    public ResponseEntity<List<PhysicalConnector>> getAllPhysicalConnectors() {
        List<PhysicalConnector> PhysicalConnectors = PhysicalConnectorService.read();
        return ResponseEntity.ok(PhysicalConnectors);
    }

    @GetMapping("/{PCnID}")
    public ResponseEntity<PhysicalConnector> getPhysicalConnectorById(@PathVariable("PCnID") int PCnID) {
        PhysicalConnector PhysicalConnector = PhysicalConnectorService.findById(PCnID);
        return ResponseEntity.ok(PhysicalConnector);
    }

    @PutMapping("/{PCnID}")

    public ResponseEntity<PhysicalConnector> updatePhysicalConnector(
            @PathVariable("PCnID") int PCnID,
            @RequestBody PhysicalConnector updatedPhysicalConnector) {

        PhysicalConnector updatedProduct = PhysicalConnectorService.update(PCnID, updatedPhysicalConnector);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{PCnID}")
    public ResponseEntity<String> deletePhysicalConnector(@PathVariable("PCnID") int PCnID) {
        String resultMessage = PhysicalConnectorService.delete(PCnID);
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