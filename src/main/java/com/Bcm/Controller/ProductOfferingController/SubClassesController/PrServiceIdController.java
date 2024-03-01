package com.Bcm.Controller.ProductOfferingController.SubClassesController;

import com.Bcm.Model.ProductOfferingABE.SubClasses.PrServiceId;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.PrServiceIdService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/PrServiceId")
@RequiredArgsConstructor
public class PrServiceIdController {

    final PrServiceIdService prServiceIdService;


    @PostMapping
    public ResponseEntity<PrServiceId> createType(@RequestBody PrServiceId PrServiceId) {
        try {
            PrServiceId createdType = prServiceIdService.create(PrServiceId);
            return ResponseEntity.ok(createdType);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<PrServiceId>> getAllPrServiceId() {
        try {
            List<PrServiceId> PrServiceId = prServiceIdService.read();
            return ResponseEntity.ok(PrServiceId);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{pr_PrServiceId}")
    public ResponseEntity<PrServiceId> getPrServiceIdById(@PathVariable("pr_PrServiceId") int pr_PrServiceId) {
        try {
            PrServiceId PrServiceId = prServiceIdService.findById(pr_PrServiceId);
            return ResponseEntity.ok(PrServiceId);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("/{pr_PrServiceId}")
    public ResponseEntity<PrServiceId> updatePrServiceId(
            @PathVariable("pr_PrServiceId") int pr_PrServiceId,
            @RequestBody PrServiceId updatedPrServiceId) {

        try {
            PrServiceId updatedGroup = prServiceIdService.update(pr_PrServiceId, updatedPrServiceId);
            return ResponseEntity.ok(updatedGroup);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/{pr_PrServiceId}")
    public ResponseEntity<String> deletePrServiceId(@PathVariable("pr_PrServiceId") int pr_PrServiceId) {
        try {
            String resultMessage = prServiceIdService.delete(pr_PrServiceId);
            return ResponseEntity.ok(resultMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<PrServiceId>> searchPrServiceIdsByKeyword(@RequestParam("name") String name) {
        try {
            List<PrServiceId> searchResults = prServiceIdService.searchByKeyword(name);
            return ResponseEntity.ok(searchResults);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
