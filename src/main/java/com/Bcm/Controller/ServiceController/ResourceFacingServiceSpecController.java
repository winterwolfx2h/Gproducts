package com.Bcm.Controller.ServiceController;

import com.Bcm.Exception.DatabaseOperationException;
import com.Bcm.Exception.InvalidInputException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Exception.ServiceAlreadyExistsException;
import com.Bcm.Model.ProductResourceABE.LogicalResource;
import com.Bcm.Model.ServiceABE.CustomerFacingServiceSpec;
import com.Bcm.Model.ServiceABE.ResourceFacingServiceSpec;
import com.Bcm.Repository.ProductResourceRepository.LogicalResourceRepository;
import com.Bcm.Repository.ServiceConfigRepo.CustomerFacingServiceSpecRepository;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.ResourceFacingServiceSpecService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Resource Facing Service Controller", description = "All of the Resource Facing Service's methods")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/Resource-facing-Service-Spec")
@RequiredArgsConstructor
public class ResourceFacingServiceSpecController {

  private static final String error = "An unexpected error occurred";
  final ResourceFacingServiceSpecService resourceFacingServiceSpecService;
  final LogicalResourceRepository logicalResourceRepository;
  final CustomerFacingServiceSpecRepository customerFacingServiceSpecRepository;

  @PostMapping("/addResourceFacingServiceSpec")
  public ResponseEntity<?> createResourceFacingServiceSpec(
      @RequestBody ResourceFacingServiceSpec resourceFacingServiceSpec) {
    try {
      LogicalResource logicalResource =
          logicalResourceRepository
              .findById(resourceFacingServiceSpec.getLogicalResource().getLR_id())
              .orElseThrow(
                  () ->
                      new ResourceNotFoundException(
                          "LogicalResource with ID "
                              + resourceFacingServiceSpec.getLogicalResource().getLR_id()
                              + " not found"));

      CustomerFacingServiceSpec customerFacingServiceSpec =
          customerFacingServiceSpecRepository
              .findById(resourceFacingServiceSpec.getCustomerFacingServiceSpec().getServiceId())
              .orElseThrow(
                  () ->
                      new ResourceNotFoundException(
                          "CustomerFacingServiceSpec with ID "
                              + resourceFacingServiceSpec.getCustomerFacingServiceSpec().getServiceId()
                              + " not found"));

      resourceFacingServiceSpec.setLogicalResource(logicalResource);
      resourceFacingServiceSpec.setCustomerFacingServiceSpec(customerFacingServiceSpec);

      ResourceFacingServiceSpec createdResourceFacingServiceSpec =
          resourceFacingServiceSpecService.create(resourceFacingServiceSpec);

      return new ResponseEntity<>(createdResourceFacingServiceSpec, HttpStatus.CREATED);
    } catch (ResourceNotFoundException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    } catch (DatabaseOperationException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    } catch (InvalidInputException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/listResourceFacingServiceSpecs")
  public ResponseEntity<?> getAllResourceFacingServiceSpecs() {
    try {
      List<ResourceFacingServiceSpec> resourceFacingServiceSpecs = resourceFacingServiceSpecService.read();
      return ResponseEntity.ok(resourceFacingServiceSpecs);
    } catch (Exception e) {
      return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/{Rfss_code}")
  public ResponseEntity<?> getResourceFacingServiceSpecById(@PathVariable("Rfss_code") int Rfss_code) {
    try {
      ResourceFacingServiceSpec resourceFacingServiceSpec = resourceFacingServiceSpecService.findById(Rfss_code);
      return ResponseEntity.ok(resourceFacingServiceSpec);
    } catch (ResourceNotFoundException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/{Rfss_code}")
  public ResponseEntity<?> updateResourceFacingServiceSpec(
      @PathVariable("Rfss_code") int Rfss_code, @RequestBody ResourceFacingServiceSpec resourceFacingServiceSpec) {
    try {
      ResourceFacingServiceSpec updatedResource =
          resourceFacingServiceSpecService.update(Rfss_code, resourceFacingServiceSpec);
      return ResponseEntity.ok(updatedResource);
    } catch (ServiceAlreadyExistsException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    } catch (ResourceNotFoundException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/{Rfss_code}")
  public ResponseEntity<?> deleteResourceFacingServiceSpec(@PathVariable("Rfss_code") int Rfss_code) {
    try {
      String resultMessage = resourceFacingServiceSpecService.delete(Rfss_code);
      return ResponseEntity.ok(resultMessage);
    } catch (ResourceNotFoundException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
