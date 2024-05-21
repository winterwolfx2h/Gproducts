package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Exception.InvalidInputException;
import com.Bcm.Model.ProductOfferingABE.Eligibility;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.EligibilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    public ResponseEntity<?> createEligibility(@RequestBody List<Eligibility> eligibilityList) {
        try {
            // Validate channels in each eligibility
            for (Eligibility eligibility : eligibilityList) {
                if (eligibility.getChannels() == null || eligibility.getChannels().isEmpty()) {
                    return ResponseEntity.badRequest().body("Channels list cannot be empty.");
                }
            }
            // Proceed with creating eligibility instances
            List<Eligibility> createdEligibilities = eligibilityService.create(eligibilityList);
            return ResponseEntity.ok(createdEligibilities);
        } catch (InvalidInputException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            // Log the stack trace for debugging purposes
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/listEligibilitys")
    @Cacheable(value = "EligibilityCache")
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
    @CacheEvict(value = "EligibilityCache", allEntries = true)
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
    @CacheEvict(value = "EligibilityCache", allEntries = true)
    public ResponseEntity<?> deleteEligibility(@PathVariable("eligibilityId") int eligibilityId) {
        try {
            String resultMessage = eligibilityService.delete(eligibilityId);
            return ResponseEntity.ok(resultMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @CacheEvict(value = "EligibilityCache", allEntries = true)
    public void invalidateEligibilityCache() {
    }

}
