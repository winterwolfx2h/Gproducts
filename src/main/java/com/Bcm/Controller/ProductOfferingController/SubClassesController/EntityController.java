package com.Bcm.Controller.ProductOfferingController.SubClassesController;

import com.Bcm.Exception.EntityAlreadyExistsException;
import com.Bcm.Exception.FamilyAlreadyExistsException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ProductOfferingABE.SubClasses.EligibilityEntity;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.EntityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Entity Controller", description = "All of the Entities methods")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/Entity")
@RequiredArgsConstructor
public class EntityController {

  final EntityService entityService;

  // final EligibilityService eligibilityService;

  @PostMapping("/addEntitys")
  public ResponseEntity<?> createEntity(@RequestBody EligibilityEntity entity) {
    try {
      EligibilityEntity createdEntity = entityService.create(entity);
      return ResponseEntity.ok(createdEntity);
    } catch (FamilyAlreadyExistsException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }
  }

  @GetMapping("/listEntity")
  public ResponseEntity<List<EligibilityEntity>> getAllFamilies() {
    try {
      List<EligibilityEntity> entities = entityService.read();
      return ResponseEntity.ok(entities);
    } catch (RuntimeException e) {
      return ResponseEntity.status(500).body(null);
    }
  }

  @GetMapping("/{entityCode}")
  public ResponseEntity<EligibilityEntity> getEntityById(@PathVariable("entityCode") int entityCode) {
    try {
      EligibilityEntity Entity = entityService.findById(entityCode);
      return ResponseEntity.ok(Entity);
    } catch (RuntimeException e) {
      return ResponseEntity.status(404).body(null);
    }
  }

  @PutMapping("/{entityCode}")
  public ResponseEntity<?> updateEntity(
      @PathVariable("entityCode") int entityCode, @RequestBody EligibilityEntity updatedEntity) {
    try {
      EligibilityEntity updatedGroup = entityService.update(entityCode, updatedEntity);
      return ResponseEntity.ok(updatedGroup);
    } catch (EntityAlreadyExistsException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
    }
  }

  @DeleteMapping("/{entityCode}")
  public ResponseEntity<String> deleteEntity(@PathVariable("entityCode") int entityCode) {
    try {
      String resultMessage = entityService.delete(entityCode);
      return ResponseEntity.ok(resultMessage);
    } catch (RuntimeException e) {
      return ResponseEntity.status(500).body(null);
    }
  }

  @GetMapping("/search")
  public ResponseEntity<List<EligibilityEntity>> searchEntitiesByKeyword(@RequestParam("name") String name) {
    try {
      List<EligibilityEntity> searchResults = entityService.searchByKeyword(name);
      return ResponseEntity.ok(searchResults);
    } catch (RuntimeException e) {
      return ResponseEntity.status(500).body(null);
    }
  }
}
