package com.Bcm.Controller.ProductOfferingController.SubClassesController;

import com.Bcm.Model.ProductOfferingABE.SubClasses.GroupDimension;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.GroupDimensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/group-dimension")
public class GroupDimensionController {

    @Autowired
    private GroupDimensionService groupDimensionService;

    @PostMapping
    public ResponseEntity<GroupDimension> createGroupDimension(@RequestBody GroupDimension groupDimension) {
        try {
            GroupDimension createdGroupDimension = groupDimensionService.create(groupDimension);
            return ResponseEntity.ok(createdGroupDimension);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<GroupDimension>> getAllGroupDimensions() {
        try {
            List<GroupDimension> groupDimensions = groupDimensionService.read();
            return ResponseEntity.ok(groupDimensions);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{po_GdCode}")
    public ResponseEntity<GroupDimension> getGroupDimensionById(@PathVariable("po_GdCode") int po_GdCode) {
        try {
            GroupDimension groupDimension = groupDimensionService.findById(po_GdCode);
            return ResponseEntity.ok(groupDimension);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("/{po_GdCode}")
    public ResponseEntity<GroupDimension> updateGroupDimension(
            @PathVariable("po_GdCode") int po_GdCode,
            @RequestBody GroupDimension updatedGroupDimension) {

        try {
            GroupDimension updatedGroup = groupDimensionService.update(po_GdCode, updatedGroupDimension);
            return ResponseEntity.ok(updatedGroup);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/{po_GdCode}")
    public ResponseEntity<String> deleteGroupDimension(@PathVariable("po_GdCode") int po_GdCode) {
        try {
            String resultMessage = groupDimensionService.delete(po_GdCode);
            return ResponseEntity.ok(resultMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<GroupDimension>> searchGroupDimensionsByKeyword(@RequestParam("name") String name) {
        try {
            List<GroupDimension> searchResults = groupDimensionService.searchByKeyword(name);
            return ResponseEntity.ok(searchResults);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
