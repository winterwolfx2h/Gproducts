package com.Bcm.Controller.ProductOfferingController.SubClassesController;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Type;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/type")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @PostMapping
    public ResponseEntity<Type> createType(@RequestBody Type Type) {
        try {
            Type createdType = typeService.create(Type);
            return ResponseEntity.ok(createdType);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Type>> getAllTypes() {
        try {
            List<Type> Types = typeService.read();
            return ResponseEntity.ok(Types);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{po_TypeCode}")
    public ResponseEntity<Type> getTypeById(@PathVariable("po_TypeCode") int po_TypeCode) {
        try {
            Type Type = typeService.findById(po_TypeCode);
            return ResponseEntity.ok(Type);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("/{po_TypeCode}")
    public ResponseEntity<Type> updateType(
            @PathVariable("po_TypeCode") int po_TypeCode,
            @RequestBody Type updatedType) {

        try {
            Type updatedTypeResult = typeService.update(po_TypeCode, updatedType);
            return ResponseEntity.ok(updatedTypeResult);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/{po_TypeCode}")
    public ResponseEntity<String> deleteType(@PathVariable("po_TypeCode") int po_TypeCode) {
        try {
            String resultMessage = typeService.delete(po_TypeCode);
            return ResponseEntity.ok(resultMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Type>> searchTypesByKeyword(@RequestParam("name") String name) {
        try {
            List<Type> searchResults = typeService.searchByKeyword(name);
            return ResponseEntity.ok(searchResults);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
