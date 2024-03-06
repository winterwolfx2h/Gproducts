package com.Bcm.Controller.ProductResourceController;

import com.Bcm.Exception.ErrorMessage;
import com.Bcm.Model.ProductResourceABE.PhysicalResource;
import com.Bcm.Service.Srvc.ProductResourceSrvc.PhysicalResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/physicalResource")
public class PhysicalResourceController {

    final PhysicalResourceService physicalResourceService;

    @PostMapping("/addPhysicalResource")
    public ResponseEntity<PhysicalResource> createPhysicalResource(@RequestBody PhysicalResource PhysicalResource) {
        PhysicalResource createdPhysicalResource = physicalResourceService.create(PhysicalResource);
        return ResponseEntity.ok(createdPhysicalResource);
    }

    @GetMapping("/listPhysicalResources")
    public ResponseEntity<?> getAllPhysicalResources() {
        try {
            List<PhysicalResource> PhysicalResources = physicalResourceService.read();
            return ResponseEntity.ok(PhysicalResources);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/{phyResourceId}")
    public ResponseEntity<?> getPhysicalResourceById(@PathVariable("phyResourceId") int phyResourceId) {
        try {
            PhysicalResource PhysicalResource = physicalResourceService.findById(phyResourceId);
            return ResponseEntity.ok(PhysicalResource);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @PutMapping("/{phyResourceId}")
    public ResponseEntity<?> updatePhysicalResource(
            @PathVariable("phyResourceId") int phyResourceId,
            @RequestBody PhysicalResource updatedPhysicalResource) {
        try {
            PhysicalResource updatedGroup = physicalResourceService.update(phyResourceId, updatedPhysicalResource);
            return ResponseEntity.ok(updatedGroup);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @DeleteMapping("/{phyResourceId}")
    public ResponseEntity<?> deletePhysicalResource(@PathVariable("phyResourceId") int phyResourceId) {
        try {
            String resultMessage = physicalResourceService.delete(phyResourceId);
            return ResponseEntity.ok(resultMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<PhysicalResource>> searchPhysicalResourceByKeyword(@RequestParam("physicalResourceType") String physicalResourceType) {
        List<PhysicalResource> searchResults = physicalResourceService.searchByKeyword(physicalResourceType);
        return ResponseEntity.ok(searchResults);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

