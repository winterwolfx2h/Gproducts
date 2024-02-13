package com.Bcm.Controller.ResourceSpecController.LogicalResourceContoller;

import com.Bcm.Exception.ErrorMessage;
import com.Bcm.Model.ResourceSpecABE.LogicalResourceBE.LogicalResource;
import com.Bcm.Service.Srvc.ResourceSpecService.LogicalResourceService.LogicalResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.List;
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/LogicalResource")
public class LogicalResourceController {

    @Autowired
    LogicalResourceService logicalResourceService;

    @PostMapping("/addProdOff")
    public ResponseEntity<LogicalResource> createLogicalResource(@RequestBody LogicalResource LogicalResource) {
        LogicalResource createdLogicalResource = logicalResourceService.create(LogicalResource);
        return ResponseEntity.ok(createdLogicalResource);
    }

    @GetMapping
    public ResponseEntity<List<LogicalResource>> getAllLogicalResources() {
        List<LogicalResource> LogicalResources = logicalResourceService.read();
        return ResponseEntity.ok(LogicalResources);
    }

    @GetMapping("/{LRID}")
    public ResponseEntity<LogicalResource> getLogicalResourceById(@PathVariable("LRID") int LRID) {
        LogicalResource LogicalResource = logicalResourceService.findById(LRID);
        return ResponseEntity.ok(LogicalResource);
    }

    @PutMapping("/{LRID}")

    public ResponseEntity<LogicalResource> updateLogicalResource(
            @PathVariable("LRID") int LRID,
            @RequestBody LogicalResource updatedLogicalResource) {

        LogicalResource updatedProduct = logicalResourceService.update(LRID, updatedLogicalResource);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{LRID}")
    public ResponseEntity<String> deleteLogicalResource(@PathVariable("LRID") int LRID) {
        String resultMessage = logicalResourceService.delete(LRID);
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
