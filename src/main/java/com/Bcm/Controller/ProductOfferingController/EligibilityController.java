package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Model.ProductOfferingABE.Eligibility;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.EligibilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/Eligibility")
public class EligibilityController {

    final EligibilityService eligibilityService;

    @PostMapping("/addEligibility")
    public ResponseEntity<?> createcreateResource(@RequestBody Eligibility Eligibility) {
        try {
            Eligibility createdEligibility = eligibilityService.create(Eligibility);
            return ResponseEntity.ok(createdEligibility);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/listEligibilitys")
    public ResponseEntity<?> getAllEligibilitys() {
        try {
            List<Eligibility> Eligibilitys = eligibilityService.read();
            return ResponseEntity.ok(Eligibilitys);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/{eligibilityId}")
    public ResponseEntity<?> getEligibilityById(@PathVariable("eligibilityId") int eligibilityId) {
        try {
            Eligibility Eligibility = eligibilityService.findById(eligibilityId);
            return ResponseEntity.ok(Eligibility);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @PutMapping("/{eligibilityId}")
    public ResponseEntity<?> updateEligibility(
            @PathVariable("eligibilityId") int eligibilityId,
            @RequestBody Eligibility updatedEligibility) {
        try {
            Eligibility updatedGroup = eligibilityService.update(eligibilityId, updatedEligibility);
            return ResponseEntity.ok(updatedGroup);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @DeleteMapping("/{eligibilityId}")
    public ResponseEntity<?> deleteEligibility(@PathVariable("eligibilityId") int eligibilityId) {
        try {
            String resultMessage = eligibilityService.delete(eligibilityId);
            return ResponseEntity.ok(resultMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchEligibilitiesByKeyword(@RequestParam("channel") String channel) {
        try {
            List<Eligibility> searchResults = eligibilityService.searchByKeyword(channel);
            return ResponseEntity.ok(searchResults);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}
