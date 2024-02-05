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
    public ResponseEntity<RatePlan> createRatePlan(@RequestBody RatePlan RatePlan) {
        RatePlan createdRatePlan = ratePlanService.create(RatePlan);
        return ResponseEntity.ok(createdRatePlan);
    }

    @GetMapping
    public ResponseEntity<List<RatePlan>> getAllRatePlans() {
        List<RatePlan> RatePlans = ratePlanService.read();
        return ResponseEntity.ok(RatePlans);
    }

    @GetMapping("/{po_RatePlanCode}")
    public ResponseEntity<RatePlan> getRatePlanById(@PathVariable("po_RatePlanCode") int po_RatePlanCode) {
        RatePlan RatePlan = ratePlanService.findById(po_RatePlanCode);
        return ResponseEntity.ok(RatePlan);
    }

    @PutMapping("/{po_RatePlanCode}")
    public ResponseEntity<RatePlan> updateRatePlan(
            @PathVariable("po_RatePlanCode") int po_RatePlanCode,
            @RequestBody RatePlan updatedRatePlan) {

        RatePlan updatedGroup = ratePlanService.update(po_RatePlanCode, updatedRatePlan);
        return ResponseEntity.ok(updatedGroup);
    }

    @DeleteMapping("/{po_RatePlanCode}")
    public ResponseEntity<String> deleteRatePlan(@PathVariable("po_RatePlanCode") int po_RatePlanCode) {
        String resultMessage = ratePlanService.delete(po_RatePlanCode);
        return ResponseEntity.ok(resultMessage);
    }

    @GetMapping("/search")
    public ResponseEntity<List<RatePlan>> searchRatePlansByKeyword(@RequestParam("name") String name) {
        List<RatePlan> searchResults = ratePlanService.searchByKeyword(name);
        return ResponseEntity.ok(searchResults);
    }
}
