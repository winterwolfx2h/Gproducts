package com.Bcm.Controller.ProductOfferingController.SubClassesController;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Parent;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/parent")
public class ParentController {

    @Autowired
    private ParentService parentService;

    @PostMapping
    public ResponseEntity<Parent> createParent(@RequestBody Parent parent) {
        try {
            Parent createdParent = parentService.create(parent);
            return ResponseEntity.ok(createdParent);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Parent>> getAllParents() {
        try {
            List<Parent> parents = parentService.read();
            return ResponseEntity.ok(parents);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{po_ParentCode}")
    public ResponseEntity<Parent> getParentById(@PathVariable("po_ParentCode") int po_ParentCode) {
        try {
            Parent parent = parentService.findById(po_ParentCode);
            return ResponseEntity.ok(parent);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("/{po_ParentCode}")
    public ResponseEntity<Parent> updateParent(
            @PathVariable("po_ParentCode") int po_ParentCode,
            @RequestBody Parent updatedParent) {

        try {
            Parent updatedParentResult = parentService.update(po_ParentCode, updatedParent);
            return ResponseEntity.ok(updatedParentResult);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/{po_ParentCode}")
    public ResponseEntity<String> deleteParent(@PathVariable("po_ParentCode") Integer po_ParentCode) {
        try {
            String resultMessage = parentService.delete(po_ParentCode);
            return ResponseEntity.ok(resultMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Parent>> searchParentsByKeyword(@RequestParam("name") String name) {
        try {
            List<Parent> searchResults = parentService.searchByKeyword(name);
            return ResponseEntity.ok(searchResults);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
