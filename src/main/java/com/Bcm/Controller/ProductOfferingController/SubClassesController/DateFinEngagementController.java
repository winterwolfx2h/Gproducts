package com.Bcm.Controller.ProductOfferingController.SubClassesController;

import com.Bcm.Model.ProductOfferingABE.SubClasses.DateFinEngagement;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.DateFinEngagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/dateFinEngagement")
public class DateFinEngagementController {

    @Autowired
    private DateFinEngagementService dateFinEngagementService;

    @PostMapping
    public ResponseEntity<DateFinEngagement> createDateFinEngagement(@RequestBody DateFinEngagement DateFinEngagement) {
        DateFinEngagement createdDateFinEngagement = dateFinEngagementService.create(DateFinEngagement);
        return ResponseEntity.ok(createdDateFinEngagement);
    }

    @GetMapping
    public ResponseEntity<List<DateFinEngagement>> getAllDateFinEngagements() {
        List<DateFinEngagement> DateFinEngagements = dateFinEngagementService.read();
        return ResponseEntity.ok(DateFinEngagements);
    }

    @GetMapping("/{po_DateFinEngCode}")
    public ResponseEntity<DateFinEngagement> getDateFinEngagementById(@PathVariable("po_DateFinEngCode") int po_DateFinEngCode) {
        DateFinEngagement DateFinEngagement = dateFinEngagementService.findById(po_DateFinEngCode);
        return ResponseEntity.ok(DateFinEngagement);
    }

    @PutMapping("/{po_DateFinEngCode}")
    public ResponseEntity<DateFinEngagement> updateDateFinEngagement(
            @PathVariable("dateFinEngagementService") int po_DateFinEngCode,
            @RequestBody DateFinEngagement updatedDateFinEngagement) {

        DateFinEngagement updatedGroup = dateFinEngagementService.update(po_DateFinEngCode, updatedDateFinEngagement);
        return ResponseEntity.ok(updatedGroup);
    }

    @DeleteMapping("/{po_DateFinEngCode}")
    public ResponseEntity<String> deleteDateFinEngagement(@PathVariable("po_DateFinEngCode") int po_DateFinEngCode) {
        String resultMessage = dateFinEngagementService.delete(po_DateFinEngCode);
        return ResponseEntity.ok(resultMessage);
    }

    @GetMapping("/search")
    public ResponseEntity<List<DateFinEngagement>> searchDateFinEngagementsByKeyword(@RequestParam("name") String name) {
        List<DateFinEngagement> searchResults = dateFinEngagementService.searchByKeyword(name);
        return ResponseEntity.ok(searchResults);
    }
}
