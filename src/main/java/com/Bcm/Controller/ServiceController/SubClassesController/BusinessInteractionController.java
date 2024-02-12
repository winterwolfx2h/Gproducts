package com.Bcm.Controller.ServiceController.SubClassesController;

import com.Bcm.Model.ServiceABE.SubClasses.BusinessInteraction;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.SubClassesSrvc.BusinessInteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/BusinessInteraction")
public class BusinessInteractionController {

    private final BusinessInteractionService businessInteractionService;

    @Autowired
    public BusinessInteractionController(BusinessInteractionService businessInteractionService) {
        this.businessInteractionService = businessInteractionService;
    }

    @PostMapping
    public ResponseEntity<BusinessInteraction> createBusinessInteraction(@RequestBody BusinessInteraction businessInteraction) {
        try {
            BusinessInteraction createdBusinessInteraction = businessInteractionService.create(businessInteraction);
            return ResponseEntity.ok(createdBusinessInteraction);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<BusinessInteraction>> getAllBusinessInteractions() {
        try {
            List<BusinessInteraction> businessInteractions = businessInteractionService.read();
            return ResponseEntity.ok(businessInteractions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{BI_code}")
    public ResponseEntity<BusinessInteraction> getBusinessInteractionById(@PathVariable("BI_code") int BI_code) {
        try {
            BusinessInteraction businessInteraction = businessInteractionService.findById(BI_code);
            return ResponseEntity.ok(businessInteraction);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{BI_code}")
    public ResponseEntity<BusinessInteraction> updateBusinessInteraction(
            @PathVariable("BI_code") int BI_code,
            @RequestBody BusinessInteraction updatedBusinessInteraction) {
        try {
            BusinessInteraction updatedBusinessInteractionResult = businessInteractionService.update(BI_code, updatedBusinessInteraction);
            return ResponseEntity.ok(updatedBusinessInteractionResult);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{BI_code}")
    public ResponseEntity<String> deleteBusinessInteraction(@PathVariable("BI_code") int BI_code) {
        try {
            String resultMessage = businessInteractionService.delete(BI_code);
            return ResponseEntity.ok(resultMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<BusinessInteraction>> searchBusinessInteractionsByKeyword(@RequestParam("name") String name) {
        try {
            List<BusinessInteraction> searchResults = businessInteractionService.searchByKeyword(name);
            return ResponseEntity.ok(searchResults);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
