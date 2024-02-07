package com.Bcm.Controller.ServiceController.SubClassesController;

import com.Bcm.Model.ServiceABE.SubClasses.ServiceDocument;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.SubClassesSrvc.ServiceDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<ServiceDocument> createServiceDocument(@RequestBody ServiceDocument ServiceDocument) {
        ServiceDocument createdServiceDocument = serviceDocumentService.create(ServiceDocument);
        return ResponseEntity.ok(createdServiceDocument);
    }

    @GetMapping
    public ResponseEntity<List<ServiceDocument>> getAllServiceDocuments() {
        List<ServiceDocument> ServiceDocuments = serviceDocumentService.read();
        return ResponseEntity.ok(ServiceDocuments);
    }

    @GetMapping("/{SD_code}")
    public ResponseEntity<ServiceDocument> getServiceDocumentById(@PathVariable("SD_code") int SD_code) {
        ServiceDocument ServiceDocument = serviceDocumentService.findById(SD_code);
        return ResponseEntity.ok(ServiceDocument);
    }

    @PutMapping("/{SD_code}")
    public ResponseEntity<ServiceDocument> updateServiceDocument(
            @PathVariable("SD_code") int SD_code,
            @RequestBody ServiceDocument updatedServiceDocument) {

        ServiceDocument updatedGroup = serviceDocumentService.update(SD_code, updatedServiceDocument);
        return ResponseEntity.ok(updatedGroup);
    }

    @DeleteMapping("/{SD_code}")
    public ResponseEntity<String> deleteServiceDocument(@PathVariable("SD_code") int SD_code) {
        String resultMessage = serviceDocumentService.delete(SD_code);
        return ResponseEntity.ok(resultMessage);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ServiceDocument>> searchServiceDocumentsByKeyword(@RequestParam("name") String name) {
        List<ServiceDocument> searchResults = serviceDocumentService.searchByKeyword(name);
        return ResponseEntity.ok(searchResults);
    }
}
