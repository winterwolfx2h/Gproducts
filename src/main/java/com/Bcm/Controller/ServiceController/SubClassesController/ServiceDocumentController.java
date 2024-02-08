package com.Bcm.Controller.ServiceController.SubClassesController;

import com.Bcm.Model.ServiceABE.SubClasses.ServiceDocument;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.SubClassesSrvc.ServiceDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/ServiceDocument")
public class ServiceDocumentController {

    @Autowired
    private ServiceDocumentService serviceDocumentService;

    @PostMapping
    public ResponseEntity<?> createServiceDocument(@RequestBody ServiceDocument serviceDocument) {
        try {
            ServiceDocument createdServiceDocument = serviceDocumentService.create(serviceDocument);
            return ResponseEntity.ok(createdServiceDocument);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllServiceDocuments() {
        try {
            List<ServiceDocument> serviceDocuments = serviceDocumentService.read();
            return ResponseEntity.ok(serviceDocuments);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/{SD_code}")
    public ResponseEntity<?> getServiceDocumentById(@PathVariable("SD_code") int SD_code) {
        try {
            ServiceDocument serviceDocument = serviceDocumentService.findById(SD_code);
            return ResponseEntity.ok(serviceDocument);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @PutMapping("/{SD_code}")
    public ResponseEntity<?> updateServiceDocument(
            @PathVariable("SD_code") int SD_code,
            @RequestBody ServiceDocument updatedServiceDocument) {
        try {
            ServiceDocument updatedGroup = serviceDocumentService.update(SD_code, updatedServiceDocument);
            return ResponseEntity.ok(updatedGroup);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @DeleteMapping("/{SD_code}")
    public ResponseEntity<?> deleteServiceDocument(@PathVariable("SD_code") int SD_code) {
        try {
            String resultMessage = serviceDocumentService.delete(SD_code);
            return ResponseEntity.ok(resultMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchServiceDocumentsByKeyword(@RequestParam("name") String name) {
        try {
            List<ServiceDocument> searchResults = serviceDocumentService.searchByKeyword(name);
            return ResponseEntity.ok(searchResults);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}
