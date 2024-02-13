package com.Bcm.Controller.ResourceSpecController.PhysicalResourceContoller;

import com.Bcm.Exception.ErrorMessage;
import com.Bcm.Model.ResourceSpecABE.PhysicalResourceBE.PhysicalPort;
import com.Bcm.Service.Srvc.ResourceSpecService.PhysicalResourceService.PhysicalPortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/PhysicalPort")
public class PhysicalPortController {

    @Autowired
    PhysicalPortService PhysicalPortService;

    @PostMapping("/addProdOff")
    public ResponseEntity<PhysicalPort> createPhysicalPort(@RequestBody PhysicalPort PhysicalPort) {
        PhysicalPort createdPhysicalPort = PhysicalPortService.create(PhysicalPort);
        return ResponseEntity.ok(createdPhysicalPort);
    }

    @GetMapping
    public ResponseEntity<List<PhysicalPort>> getAllPhysicalPorts() {
        List<PhysicalPort> PhysicalPorts = PhysicalPortService.read();
        return ResponseEntity.ok(PhysicalPorts);
    }

    @GetMapping("/{PPID}")
    public ResponseEntity<PhysicalPort> getPhysicalPortById(@PathVariable("PPID") int PPID) {
        PhysicalPort PhysicalPort = PhysicalPortService.findById(PPID);
        return ResponseEntity.ok(PhysicalPort);
    }

    @PutMapping("/{PPID}")

    public ResponseEntity<PhysicalPort> updatePhysicalPort(
            @PathVariable("PPID") int PPID,
            @RequestBody PhysicalPort updatedPhysicalPort) {

        PhysicalPort updatedProduct = PhysicalPortService.update(PPID, updatedPhysicalPort);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{PPID}")
    public ResponseEntity<String> deletePhysicalPort(@PathVariable("PPID") int PPID) {
        String resultMessage = PhysicalPortService.delete(PPID);
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