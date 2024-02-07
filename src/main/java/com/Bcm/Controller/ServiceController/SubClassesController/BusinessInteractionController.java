package com.Bcm.Controller.ServiceController.SubClassesController;

import com.Bcm.Model.ServiceABE.SubClasses.BusinessInteraction;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.SubClassesSrvc.BusinessInteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/BusinessInteraction")
public class BusinessInteractionController {

    @Autowired
    private BusinessInteractionService businessInteractionService;

    @PostMapping
    public ResponseEntity<BusinessInteraction> createBusinessInteraction(@RequestBody BusinessInteraction BusinessInteraction) {
        BusinessInteraction createdBusinessInteraction = businessInteractionService.create(BusinessInteraction);
        return ResponseEntity.ok(createdBusinessInteraction);
    }

    @GetMapping
    public ResponseEntity<List<BusinessInteraction>> getAllBusinessInteractions() {
        List<BusinessInteraction> BusinessInteractions = businessInteractionService.read();
        return ResponseEntity.ok(BusinessInteractions);
    }

    @GetMapping("/{BI_code}")
    public ResponseEntity<BusinessInteraction> getBusinessInteractionById(@PathVariable("BI_code") int BI_code) {
        BusinessInteraction BusinessInteraction = businessInteractionService.findById(BI_code);
        return ResponseEntity.ok(BusinessInteraction);
    }

    @PutMapping("/{BI_code}")
    public ResponseEntity<BusinessInteraction> updateBusinessInteraction(
            @PathVariable("BI_code") int BI_code,
            @RequestBody BusinessInteraction updatedBusinessInteraction) {

        BusinessInteraction updatedGroup = businessInteractionService.update(BI_code, updatedBusinessInteraction);
        return ResponseEntity.ok(updatedGroup);
    }

    @DeleteMapping("/{BI_code}")
    public ResponseEntity<String> deleteBusinessInteraction(@PathVariable("BI_code") int BI_code) {
        String resultMessage = businessInteractionService.delete(BI_code);
        return ResponseEntity.ok(resultMessage);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BusinessInteraction>> searchBusinessInteractionsByKeyword(@RequestParam("name") String name) {
        List<BusinessInteraction> searchResults = businessInteractionService.searchByKeyword(name);
        return ResponseEntity.ok(searchResults);
    }
}
