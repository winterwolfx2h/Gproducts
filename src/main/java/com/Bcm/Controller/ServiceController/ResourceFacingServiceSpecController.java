package com.Bcm.Controller.ServiceController;

import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Exception.ServiceAlreadyExistsException;
import com.Bcm.Exception.ServiceLogicException;
import com.Bcm.Model.ServiceABE.ResourceFacingServiceSpec;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.ResourceFacingServiceSpecService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/Resource-facing-Service-Spec")
@RequiredArgsConstructor
public class ResourceFacingServiceSpecController {


    final ResourceFacingServiceSpecService resourceFacingServiceSpecService;

    @PostMapping("/addResourceFacingServiceSpec")
    public ResponseEntity<?> createResourceFacingServiceSpec(@RequestBody ResourceFacingServiceSpec resourceFacingServiceSpec) {
        try {
            ResourceFacingServiceSpec createdResourceFacingServiceSpec = resourceFacingServiceSpecService.create(resourceFacingServiceSpec);
            return new ResponseEntity<>(createdResourceFacingServiceSpec, HttpStatus.CREATED);
        } catch (ServiceAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listResourceFacingServiceSpecs")
    public ResponseEntity<?> getAllResourceFacingServiceSpecs() {
        try {
            List<ResourceFacingServiceSpec> resourceFacingServiceSpecs = resourceFacingServiceSpecService.read();
            return ResponseEntity.ok(resourceFacingServiceSpecs);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{Rfss_code}")
    public ResponseEntity<?> getResourceFacingServiceSpecById(@PathVariable("Rfss_code") int id) {
        try {
            ResourceFacingServiceSpec resourceFacingServiceSpec = resourceFacingServiceSpecService.findById(id);
            return ResponseEntity.ok(resourceFacingServiceSpec);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{Rfss_code}")
    public ResponseEntity<?> updateResourceFacingServiceSpec(@PathVariable("Rfss_code") int id, @RequestBody ResourceFacingServiceSpec resourceFacingServiceSpec) {
        try {
            ResourceFacingServiceSpec updatedResource = resourceFacingServiceSpecService.update(id, resourceFacingServiceSpec);
            return ResponseEntity.ok(updatedResource);
        } catch (ServiceAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{Rfss_code}")
    public ResponseEntity<?> deleteResourceFacingServiceSpec(@PathVariable("Rfss_code") int id) {
        try {
            String resultMessage = resourceFacingServiceSpecService.delete(id);
            return ResponseEntity.ok(resultMessage);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/changeStatus/{Rfss_code}")
    public ResponseEntity<?> changeResourceFacingServiceSpecStatus(@PathVariable int Rfss_code) {
        try {
            ResourceFacingServiceSpec updatedResource = resourceFacingServiceSpecService.changeServiceStatus(Rfss_code);
            return ResponseEntity.ok(updatedResource);

        } catch (ServiceLogicException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}
