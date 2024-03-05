package com.Bcm.Controller.ProductOfferingController.SubClassesController;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Family;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/family")
public class FamilyController {

    @Autowired
    private FamilyService familyService;

   /*@PostMapping
    public ResponseEntity<?> create(@RequestBody Family family) {

        String subFamilyName = family.getSubFamily().getName();
        SubFamily subFamily = subFamilyService.findByName(subFamilyName);

        if (subFamily != null ) {
            family.setSubFamily(subFamily);

            Family createdFamily = familyService.create(family);
            return ResponseEntity.ok(createdFamily);
        } else {
            StringBuilder errorMessage = new StringBuilder("The following entities were not found:");
            if (subFamily == null) errorMessage.append(" SubFamily with name: ").append(subFamilyName);
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }
    }*/


    @PostMapping
    public ResponseEntity<Family> createType(@RequestBody Family family) {
        try {
            Family createdType = familyService.create(family);
            return ResponseEntity.ok(createdType);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping
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

   /* @GetMapping("/{po_FamilyCode}/subfamilies")
    public ResponseEntity<SubFamily> getSubFamilyOfFamily(@PathVariable("po_FamilyCode") int po_FamilyCode) {
        try {
            // Find the family by ID
            Family family = familyService.findById(po_FamilyCode);

            // Retrieve the associated subfamily from the family object
            SubFamily subFamily = family.getSubFamily();

            // Check if the subfamily is not null
            if (subFamily != null) {
                return ResponseEntity.ok(subFamily);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }*/







}
