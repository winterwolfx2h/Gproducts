package com.Bcm.Controller.ProductOfferingController.SubClassesController;

import com.Bcm.Exception.EntityAlreadyExistsException;
import com.Bcm.Exception.FamilyAlreadyExistsException;
import com.Bcm.Exception.InvalidInputException;
import com.Bcm.Exception.ResourceNotFoundException;

import com.Bcm.Model.ProductOfferingABE.SubClasses.EligibilityEntity;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.EligibilityService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.EntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/Entity")
@RequiredArgsConstructor
public class EntityController {


    final EntityService entityService;
    final EligibilityService eligibilityService;




    @PostMapping("/addEntitys")

    public ResponseEntity<?> createChannels(@RequestBody EligibilityEntity entity) {
        try {
            EligibilityEntity createdEntity = entityService.create(entity);
            return ResponseEntity.ok(createdEntity);
        } catch (FamilyAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
//    public ResponseEntity<?> createEntities(@RequestBody List<Entity> entities) {
//        try {
//            List<String> createdEntityNames = new ArrayList<>(); // List to hold Entity names
//
//            // Create each Entity and add its name to the list of created Entity names
//            for (Entity entity : entities) {
//                Entity createdEntity = entityService.create(entity);
//                createdEntityNames.add(createdEntity.getName());
//            }
//
//            // Update all existing Eligibility instances to associate them with the created Entitys
//            List<Eligibility> eligibilities = eligibilityService.read();
//            for (Eligibility eligibility : eligibilities) {
//                eligibility.getEntities().addAll(createdEntityNames);
//                eligibilityService.update(eligibility.getEligibility_id(), eligibility);
//            }
//
//            return ResponseEntity.ok(createdEntityNames);
//        } catch (EntityAlreadyExistsException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        } catch (InvalidInputException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        } catch (RuntimeException e) {
//            // Log the stack trace for debugging purposes
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
//        }
//    }


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
            @PathVariable("entityCode") int entityCode,
            @RequestBody EligibilityEntity updatedEntity) {
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
    public ResponseEntity<List<EligibilityEntity>> searchFamiliesByKeyword(@RequestParam("name") String name) {
        try {
            List<EligibilityEntity> searchResults = entityService.searchByKeyword(name);
            return ResponseEntity.ok(searchResults);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

}

