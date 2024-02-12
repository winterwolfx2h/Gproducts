package com.Bcm.Controller.ProductOfferingController.SubClassesController;

import com.Bcm.Model.ProductOfferingABE.SubClasses.DureeEngagement;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.DureeEngagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/dureeEngagement")
public class DureeEngagementController {

    @Autowired
    private DureeEngagementService dureeEngagementService;

    @PostMapping
    public ResponseEntity<DureeEngagement> createDureeEngagement(@RequestBody DureeEngagement dureeEngagement) {
        DureeEngagement createdDureeEngagement = dureeEngagementService.create(dureeEngagement);
        return ResponseEntity.ok(createdDureeEngagement);
    }

    @GetMapping
    public ResponseEntity<List<DureeEngagement>> getAllDureeEngagements() {
        List<DureeEngagement> dureeEngagements = dureeEngagementService.read();
        return ResponseEntity.ok(dureeEngagements);
    }

    @GetMapping("/{po_DureeEngCode}")
    public ResponseEntity<DureeEngagement> getDureeEngagementById(@PathVariable("po_DureeEngCode") int po_DureeEngCode) {
        DureeEngagement dureeEngagement = dureeEngagementService.findById(po_DureeEngCode);
        return ResponseEntity.ok(dureeEngagement);
    }

    @PutMapping("/{po_DureeEngCode}")
    public ResponseEntity<DureeEngagement> updateDureeEngagement(
            @PathVariable("po_DureeEngCode") int po_DureeEngCode,
            @RequestBody DureeEngagement updatedDureeEngagement) {

        DureeEngagement updatedGroup = dureeEngagementService.update(po_DureeEngCode, updatedDureeEngagement);
        return ResponseEntity.ok(updatedGroup);
    }

    @DeleteMapping("/{po_DureeEngCode}")
    public ResponseEntity<String> deleteDureeEngagement(@PathVariable("po_DureeEngCode") int po_DureeEngCode) {
        String resultMessage = dureeEngagementService.delete(po_DureeEngCode);
        return ResponseEntity.ok(resultMessage);
    }

    @GetMapping("/search")
    public ResponseEntity<List<DureeEngagement>> searchDureeEngagementsByKeyword(@RequestParam("name") String name) {
        List<DureeEngagement> searchResults = dureeEngagementService.searchByKeyword(name);
        return ResponseEntity.ok(searchResults);
    }
}
