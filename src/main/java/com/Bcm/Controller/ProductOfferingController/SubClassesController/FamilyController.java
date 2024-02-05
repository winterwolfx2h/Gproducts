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

    @PostMapping
    public ResponseEntity<Family> createFamily(@RequestBody Family Family) {
        Family createdFamily = familyService.create(Family);
        return ResponseEntity.ok(createdFamily);
    }

    @GetMapping
    public ResponseEntity<List<Family>> getAllFamilys() {
        List<Family> Familys = familyService.read();
        return ResponseEntity.ok(Familys);
    }

    @GetMapping("/{po_FamilyCode}")
    public ResponseEntity<Family> getFamilyById(@PathVariable("po_FamilyCode") int po_FamilyCode) {
        Family Family = familyService.findById(po_FamilyCode);
        return ResponseEntity.ok(Family);
    }

    @PutMapping("/{po_FamilyCode}")
    public ResponseEntity<Family> updateFamily(
            @PathVariable("po_FamilyCode") int po_FamilyCode,
            @RequestBody Family updatedFamily) {

        Family updatedGroup = familyService.update(po_FamilyCode, updatedFamily);
        return ResponseEntity.ok(updatedGroup);
    }

    @DeleteMapping("/{po_FamilyCode}")
    public ResponseEntity<String> deleteFamily(@PathVariable("po_FamilyCode") int po_FamilyCode) {
        String resultMessage = familyService.delete(po_FamilyCode);
        return ResponseEntity.ok(resultMessage);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Family>> searchFamilysByKeyword(@RequestParam("name") String name) {
        List<Family> searchResults = familyService.searchByKeyword(name);
        return ResponseEntity.ok(searchResults);
    }
}
