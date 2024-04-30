package com.Bcm.Controller.ProductOfferingController.SubClassesController;

import com.Bcm.Exception.FamilyAlreadyExistsException;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Family;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.FamilyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/family")
@RequiredArgsConstructor
public class FamilyController {


    final FamilyService familyService;

    @PostMapping("/addFamily")
    public ResponseEntity<?> createType(@RequestBody Family family) {
        try {
            Family createdFamily = familyService.create(family);
            return ResponseEntity.ok(createdFamily);
        } catch (FamilyAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }


    @GetMapping("/listFamily")
    public ResponseEntity<List<Family>> getAllFamilies() {
        try {
            List<Family> families = familyService.read();
            return ResponseEntity.ok(families);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{po_FamilyCode}")
    public ResponseEntity<Family> getFamilyById(@PathVariable("po_FamilyCode") int po_FamilyCode) {
        try {
            Family family = familyService.findById(po_FamilyCode);
            return ResponseEntity.ok(family);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("/{po_FamilyCode}")
    public ResponseEntity<Family> updateFamily(
            @PathVariable("po_FamilyCode") int po_FamilyCode,
            @RequestBody Family updatedFamily) {

        try {
            Family updatedGroup = familyService.update(po_FamilyCode, updatedFamily);
            return ResponseEntity.ok(updatedGroup);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/{po_FamilyCode}")
    public ResponseEntity<String> deleteFamily(@PathVariable("po_FamilyCode") int po_FamilyCode) {
        try {
            String resultMessage = familyService.delete(po_FamilyCode);
            return ResponseEntity.ok(resultMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Family>> searchFamiliesByKeyword(@RequestParam("name") String name) {
        try {
            List<Family> searchResults = familyService.searchByKeyword(name);
            return ResponseEntity.ok(searchResults);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

}
