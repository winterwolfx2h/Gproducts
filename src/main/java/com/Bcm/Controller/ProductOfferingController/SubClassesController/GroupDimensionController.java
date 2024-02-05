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
        GroupDimension createdGroupDimension = groupDimensionService.create(groupDimension);
        return ResponseEntity.ok(createdGroupDimension);
    }

    @GetMapping
    public ResponseEntity<List<GroupDimension>> getAllGroupDimensions() {
        List<GroupDimension> groupDimensions = groupDimensionService.read();
        return ResponseEntity.ok(groupDimensions);
    }

    @GetMapping("/{po_GdCode}")
    public ResponseEntity<GroupDimension> getGroupDimensionById(@PathVariable("po_GdCode") int po_GdCode) {
        GroupDimension groupDimension = groupDimensionService.findById(po_GdCode);
        return ResponseEntity.ok(groupDimension);
    }

    @PutMapping("/{po_GdCode}")
    public ResponseEntity<GroupDimension> updateGroupDimension(
            @PathVariable("po_GdCode") int po_GdCode,
            @RequestBody GroupDimension updatedGroupDimension) {

        GroupDimension updatedGroup = groupDimensionService.update(po_GdCode, updatedGroupDimension);
        return ResponseEntity.ok(updatedGroup);
    }

    @DeleteMapping("/{po_GdCode}")
    public ResponseEntity<String> deleteGroupDimension(@PathVariable("po_GdCode") int po_GdCode) {
        String resultMessage = groupDimensionService.delete(po_GdCode);
        return ResponseEntity.ok(resultMessage);
    }

    @GetMapping("/search")
    public ResponseEntity<List<GroupDimension>> searchGroupDimensionsByKeyword(@RequestParam("name") String name) {
        List<GroupDimension> searchResults = groupDimensionService.searchByKeyword(name);
        return ResponseEntity.ok(searchResults);
    }
}
