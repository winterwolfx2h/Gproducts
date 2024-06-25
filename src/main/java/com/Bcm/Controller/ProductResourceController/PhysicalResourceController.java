package com.Bcm.Controller.ProductResourceController;

import com.Bcm.Exception.ErrorMessage;
import com.Bcm.Exception.ServiceAlreadyExistsException;
import com.Bcm.Exception.ServiceLogicException;
import com.Bcm.Model.ProductResourceABE.PhysicalResource;
import com.Bcm.Service.Srvc.ProductResourceSrvc.PhysicalResourceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.List;

@Tag(name = "Physical Resource Controller", description = "All of the Physical Resource's methods")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/physicalResource")
public class PhysicalResourceController {

    final PhysicalResourceService physicalResourceService;




    @PostMapping("/addPhysicalResource")
    public ResponseEntity<?> createPhysicalResource(@RequestBody PhysicalResource physicalResource) {
        try {
            PhysicalResource createdPhysicalResource = physicalResourceService.create(physicalResource);
            return new ResponseEntity<>(createdPhysicalResource, HttpStatus.CREATED);
        } catch (ServiceAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
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

    @GetMapping("/{PR_id}")
    public ResponseEntity<?> getPhysicalResourceById(@PathVariable("PR_id") int PR_id) {
        try {
            PhysicalResource PhysicalResource = physicalResourceService.findById(PR_id);
            return ResponseEntity.ok(PhysicalResource);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @PutMapping("/{PR_id}")
    public ResponseEntity<?> updatePhysicalResource(
            @PathVariable("PR_id") int PR_id, @RequestBody PhysicalResource updatedPhysicalResource) {
        try {
            PhysicalResource updatedGroup = physicalResourceService.update(PR_id, updatedPhysicalResource);
            return ResponseEntity.ok(updatedGroup);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @DeleteMapping("/{PR_id}")
    public ResponseEntity<?> deletePhysicalResource(@PathVariable("PR_id") int PR_id) {
        try {
            String resultMessage = physicalResourceService.delete(PR_id);
            return ResponseEntity.ok(resultMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<PhysicalResource>> searchPhysicalResourceByKeyword(
            @RequestParam("physicalResourceType") String physicalResourceType) {
        List<PhysicalResource> searchResults = physicalResourceService.searchByKeyword(physicalResourceType);
        return ResponseEntity.ok(searchResults);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorMessage message =
                new ErrorMessage(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/changeStatus/{PR_id}")
    public ResponseEntity<?> changePhysicalResourceStatus(@PathVariable int PR_id) {
        try {
            PhysicalResource updatedResource = physicalResourceService.changeServiceStatus(PR_id);
            return ResponseEntity.ok(updatedResource);

        } catch (ServiceLogicException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}
