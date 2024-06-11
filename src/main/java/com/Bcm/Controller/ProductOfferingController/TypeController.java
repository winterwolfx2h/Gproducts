package com.Bcm.Controller.ProductOfferingController;


import com.Bcm.Model.ProductOfferingABE.Type;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/Type")
public class TypeController {

    final TypeService typeService;

    @PostMapping("/addTypes")
    @CacheEvict(value = "TypesCache", allEntries = true)
    public ResponseEntity<?> create(@RequestBody List<Type> types) {
        try {
            List<Type> createdTypes = typeService.create(types);
            return ResponseEntity.ok(createdTypes);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }


    @GetMapping("/listTypes")
    @Cacheable(value = "TypesCache")
    public ResponseEntity<?> getAllTypes() {
        try {
            List<Type> types = typeService.read();
            return ResponseEntity.ok(types);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/{type_id}")
    public ResponseEntity<?> getTypeById(@PathVariable("type_id") int type_id) {
        try {
            Type Type = typeService.findById(type_id);
            return ResponseEntity.ok(Type);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @PutMapping("/{type_id}")
    @CacheEvict(value = "TypesCache", allEntries = true)
    public ResponseEntity<?> updateType(
            @PathVariable("type_id") int type_id,
            @RequestBody Type updatedType) {
        try {
            Type updatedGroup = typeService.update(type_id, updatedType);
            return ResponseEntity.ok(updatedGroup);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @DeleteMapping("/{type_id}")
    @CacheEvict(value = "TypesCache", allEntries = true)
    public ResponseEntity<?> deleteType(@PathVariable("type_id") int type_id) {
        try {
            String resultMessage = typeService.delete(type_id);
            return ResponseEntity.ok(resultMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchTypesByKeyword(@RequestParam("typeName") String typeName) {
        try {
            List<Type> searchResults = typeService.searchByKeyword(typeName);
            return ResponseEntity.ok(searchResults);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @CacheEvict(value = "TypesCache", allEntries = true)
    public void invalidateTypesCache() {
    }
}
