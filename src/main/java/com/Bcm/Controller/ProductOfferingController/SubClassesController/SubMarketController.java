package com.Bcm.Controller.ProductOfferingController.SubClassesController;

import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Exception.SubMarketAlreadyExistsException;
import com.Bcm.Model.ProductOfferingABE.SubClasses.SubMarket;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.SubMarketService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Sub-Market Controller", description = "All of the Sub-Market's methods")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/SubMarket")
@RequiredArgsConstructor
public class SubMarketController {

    final SubMarketService subMarketService;

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
    public ResponseEntity<?> updateSubMarket(
            @PathVariable("po_SubMarketCode") int po_SubMarketCode, @RequestBody SubMarket updatedSubMarket) {
        try {
            SubMarket updatedSubMarketResult = subMarketService.update(po_SubMarketCode, updatedSubMarket);
            return ResponseEntity.ok(updatedSubMarketResult);

        } catch (SubMarketAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
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
