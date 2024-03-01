package com.Bcm.Controller.ProductOfferingController.SubClassesController;

import com.Bcm.Model.ProductOfferingABE.SubClasses.LrServiceId;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.LrServiceIdService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/LrServiceId")
@RequiredArgsConstructor
public class LrServiceIdController {

    final LrServiceIdService lrServiceIdService;


    @PostMapping
    public ResponseEntity<LrServiceId> createType(@RequestBody LrServiceId LrServiceId) {
        try {
            LrServiceId createdType = lrServiceIdService.create(LrServiceId);
            return ResponseEntity.ok(createdType);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<LrServiceId>> getAllLrServiceId() {
        try {
            List<LrServiceId> LrServiceId = lrServiceIdService.read();
            return ResponseEntity.ok(LrServiceId);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{pr_LrServiceId}")
    public ResponseEntity<LrServiceId> getLrServiceIdById(@PathVariable("pr_LrServiceId") int pr_LrServiceId) {
        try {
            LrServiceId LrServiceId = lrServiceIdService.findById(pr_LrServiceId);
            return ResponseEntity.ok(LrServiceId);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("/{pr_LrServiceId}")
    public ResponseEntity<LrServiceId> updateLrServiceId(
            @PathVariable("pr_LrServiceId") int pr_LrServiceId,
            @RequestBody LrServiceId updatedLrServiceId) {

        try {
            LrServiceId updatedGroup = lrServiceIdService.update(pr_LrServiceId, updatedLrServiceId);
            return ResponseEntity.ok(updatedGroup);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/{pr_LrServiceId}")
    public ResponseEntity<String> deleteLrServiceId(@PathVariable("pr_LrServiceId") int pr_LrServiceId) {
        try {
            String resultMessage = lrServiceIdService.delete(pr_LrServiceId);
            return ResponseEntity.ok(resultMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<LrServiceId>> searchLrServiceIdsByKeyword(@RequestParam("name") String name) {
        try {
            List<LrServiceId> searchResults = lrServiceIdService.searchByKeyword(name);
            return ResponseEntity.ok(searchResults);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
