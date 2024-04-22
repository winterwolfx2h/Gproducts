package com.Bcm.Controller.ProductOfferingController.SubClassesController;

import com.Bcm.Model.ProductOfferingABE.SubClasses.SubMarket;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.SubMarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/SubMarket")
public class SubMarketController {

    @Autowired
    private SubMarketService subMarketService;

    @PostMapping("/addSubMarket")
    public ResponseEntity<?> createSubMarket(@RequestBody SubMarket subMarket) {
        try {
            SubMarket createdSubMarket = subMarketService.create(subMarket);
            return ResponseEntity.ok(createdSubMarket);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("already exists")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
            } else {
                return ResponseEntity.status(500).body("An error occurred while creating the SubMarket.");
            }
        }
    }

    @GetMapping("/listSubMarket")
    public ResponseEntity<List<SubMarket>> getAllSubMarkets() {
        try {
            List<SubMarket> SubMarkets = subMarketService.read();
            return ResponseEntity.ok(SubMarkets);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{po_SubMarketCode}")
    public ResponseEntity<SubMarket> getSubMarketById(@PathVariable("po_SubMarketCode") int po_SubMarketCode) {
        try {
            SubMarket SubMarket = subMarketService.findById(po_SubMarketCode);
            return ResponseEntity.ok(SubMarket);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("/{po_SubMarketCode}")
    public ResponseEntity<SubMarket> updateSubMarket(
            @PathVariable("po_SubMarketCode") int po_SubMarketCode,
            @RequestBody SubMarket updatedSubMarket) {

        try {
            SubMarket updatedSubMarketResult = subMarketService.update(po_SubMarketCode, updatedSubMarket);
            return ResponseEntity.ok(updatedSubMarketResult);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/{po_SubMarketCode}")
    public ResponseEntity<String> deleteSubMarket(@PathVariable("po_SubMarketCode") int po_SubMarketCode) {
        try {
            String resultMessage = subMarketService.delete(po_SubMarketCode);
            return ResponseEntity.ok(resultMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<SubMarket>> searchSubMarketsByKeyword(@RequestParam("name") String name) {
        try {
            List<SubMarket> searchResults = subMarketService.searchByKeyword(name);
            return ResponseEntity.ok(searchResults);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
