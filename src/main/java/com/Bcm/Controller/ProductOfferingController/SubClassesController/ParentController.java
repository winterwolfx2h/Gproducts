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
    public ResponseEntity<Parent> createParent(@RequestBody Parent Parent) {
        Parent createdParent = parentService.create(Parent);
        return ResponseEntity.ok(createdParent);
    }

    @GetMapping
    public ResponseEntity<List<Parent>> getAllParents() {
        List<Parent> Parents = parentService.read();
        return ResponseEntity.ok(Parents);
    }

    @GetMapping("/{po_ParentCode}")
    public ResponseEntity<Parent> getParentById(@PathVariable("po_ParentCode") int po_ParentCode) {
        Parent Parent = parentService.findById(po_ParentCode);
        return ResponseEntity.ok(Parent);
    }

    @PutMapping("/{po_ParentCode}")
    public ResponseEntity<Parent> updateParent(
            @PathVariable("po_ParentCode") int po_ParentCode,
            @RequestBody Parent updatedParent) {

        Parent updatedGroup = parentService.update(po_ParentCode, updatedParent);
        return ResponseEntity.ok(updatedGroup);
    }

    @DeleteMapping("/{po_ParentCode}")
    public ResponseEntity<String> deleteParent(@PathVariable("po_ParentCode") int po_ParentCode) {
        String resultMessage = parentService.delete(po_ParentCode);
        return ResponseEntity.ok(resultMessage);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Parent>> searchParentsByKeyword(@RequestParam("name") String name) {
        List<Parent> searchResults = parentService.searchByKeyword(name);
        return ResponseEntity.ok(searchResults);
    }
}
