package com.Bcm.Controller.ProductOfferingController.SubClassesController;

import com.Bcm.Model.ProductOfferingABE.SubClasses.RelationType;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.RelationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/RelationType")
public class RelationTypeController {

    @Autowired
    private RelationTypeService relationTypeService;

    @PostMapping
    public ResponseEntity<RelationType> createRelationType(@RequestBody RelationType RelationType) {
        try {
            RelationType createdRelationType = relationTypeService.create(RelationType);
            return ResponseEntity.ok(createdRelationType);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<RelationType>> getAllRelationTypes() {
        try {
            List<RelationType> RelationTypes = relationTypeService.read();
            return ResponseEntity.ok(RelationTypes);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{poRelationType_code}")
    public ResponseEntity<RelationType> getRelationTypeById(@PathVariable("poRelationType_code") int poRelationType_code) {
        try {
            RelationType RelationType = relationTypeService.findById(poRelationType_code);
            return ResponseEntity.ok(RelationType);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("/{poRelationType_code}")
    public ResponseEntity<RelationType> updateRelationType(
            @PathVariable("poRelationType_code") int poRelationType_code,
            @RequestBody RelationType updatedRelationType) {

        try {
            RelationType updatedRelationTypeResult = relationTypeService.update(poRelationType_code, updatedRelationType);
            return ResponseEntity.ok(updatedRelationTypeResult);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/{poRelationType_code}")
    public ResponseEntity<String> deleteRelationType(@PathVariable("poRelationType_code") int poRelationType_code) {
        try {
            String resultMessage = relationTypeService.delete(poRelationType_code);
            return ResponseEntity.ok(resultMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<RelationType>> searchRelationTypesByKeyword(@RequestParam("name") String name) {
        try {
            List<RelationType> searchResults = relationTypeService.searchByKeyword(name);
            return ResponseEntity.ok(searchResults);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
