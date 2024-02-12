package com.Bcm.Controller.ProductOfferingController.SubClassesController;

import com.Bcm.Model.ProductOfferingABE.SubClasses.RatePlan;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.RatePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/ratePlan")
public class RatePlanController {

    @Autowired
    private RatePlanService ratePlanService;

    @PostMapping
    public ResponseEntity<RatePlan> createRatePlan(@RequestBody RatePlan ratePlan) {
        try {
            RatePlan createdRatePlan = ratePlanService.create(ratePlan);
            return ResponseEntity.ok(createdRatePlan);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<RatePlan>> getAllRatePlans() {
        try {
            List<RatePlan> ratePlans = ratePlanService.read();
            return ResponseEntity.ok(ratePlans);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{po_RatePlanCode}")
    public ResponseEntity<RatePlan> getRatePlanById(@PathVariable("po_RatePlanCode") int po_RatePlanCode) {
        try {
            RatePlan ratePlan = ratePlanService.findById(po_RatePlanCode);
            return ResponseEntity.ok(ratePlan);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("/{po_RatePlanCode}")
    public ResponseEntity<RatePlan> updateRatePlan(
            @PathVariable("po_RatePlanCode") int po_RatePlanCode,
            @RequestBody RatePlan updatedRatePlan) {

        try {
            RatePlan updatedRatePlanResult = ratePlanService.update(po_RatePlanCode, updatedRatePlan);
            return ResponseEntity.ok(updatedRatePlanResult);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/{po_RatePlanCode}")
    public ResponseEntity<String> deleteRatePlan(@PathVariable("po_RatePlanCode") int po_RatePlanCode) {
        try {
            String resultMessage = ratePlanService.delete(po_RatePlanCode);
            return ResponseEntity.ok(resultMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<RatePlan>> searchRatePlansByKeyword(@RequestParam("name") String name) {
        try {
            List<RatePlan> searchResults = ratePlanService.searchByKeyword(name);
            return ResponseEntity.ok(searchResults);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
