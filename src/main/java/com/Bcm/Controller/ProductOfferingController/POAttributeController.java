package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Exception.ErrorMessage;
import com.Bcm.Exception.InvalidInputException;
import com.Bcm.Exception.ServiceAlreadyExistsException;
import com.Bcm.Model.ProductOfferingABE.POAttributes;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.POAttributesService;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.CustomerFacingServiceSpecService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "PO-Attribute Controller", description = "All of the PO-Attribute's methods")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/POAttribute")
public class POAttributeController {

  final POAttributesService poAttributesService;
  final CustomerFacingServiceSpecService customerFacingServiceSpecService;

  @GetMapping("/listPOAttributes")
  @Cacheable(value = "AttributesCache")
  public List<POAttributes> read() {
    return poAttributesService.read();
  }

  @PostMapping("/add")
  @CacheEvict(value = "AttributesCache", allEntries = true)
  public ResponseEntity<?> create(@RequestBody List<POAttributes> POAttributesList) {
    try {
      List<POAttributes> createdPOAttributesList = new ArrayList<>();

      for (POAttributes poAttribute : POAttributesList) {
        // Validate service
        String service = poAttribute.getService();
        if (service != null && !service.isEmpty() && !customerFacingServiceSpecService.findByNameexist(service)) {
          return ResponseEntity.badRequest().body("Service with name '" + service + "' does not exist.");
        }

        // Validate and create POAttributes
        String attributeCategoryName = poAttribute.getCategory();
        if (attributeCategoryName != null && !attributeCategoryName.isEmpty()) {
          for (POAttributes.ValueDescription valueDescription : poAttribute.getValueDescription()) {
            if (valueDescription.getDescription() == null) {
              valueDescription.setDescription("Default Description");
            }
          }

          POAttributes createdPlan = poAttributesService.create(poAttribute);
          createdPOAttributesList.add(createdPlan);
        } else {
          return ResponseEntity.badRequest().body("Attribute category is missing for one or more POAttributes.");
        }
      }

      return ResponseEntity.ok(createdPOAttributesList);
    } catch (ServiceAlreadyExistsException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    } catch (InvalidInputException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
    }
  }

  @PutMapping("/updatePOAttributes/{poAttribute_code}")
  @CacheEvict(value = "AttributesCache", allEntries = true)
  public ResponseEntity<?> update(@PathVariable int poAttribute_code, @RequestBody POAttributes POAttributes) {
    try {
      POAttributes updatedPlan = poAttributesService.update(poAttribute_code, POAttributes);
      return ResponseEntity.ok(updatedPlan);
    } catch (InvalidInputException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/{poAttribute_code}")
  @CacheEvict(value = "AttributesCache", allEntries = true)
  public String delete(@PathVariable int poAttribute_code) {
    return poAttributesService.delete(poAttribute_code);
  }

  @GetMapping("/getById/{poAttribute_code}")
  public ResponseEntity<POAttributes> getById(@PathVariable int poAttribute_code) {
    try {
      POAttributes foundPlan = poAttributesService.findById(poAttribute_code);
      return ResponseEntity.ok(foundPlan);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
  }

  private RuntimeException handleException(Exception e) {
    ErrorMessage errorMessage =
        new ErrorMessage(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            new Date(),
            e.getMessage(),
            "There was an error processing the request.");
    return new RuntimeException(errorMessage.toString(), e);
  }

  @CacheEvict(value = "AttributesCache", allEntries = true)
  public void invalidateAttributesCache() {}
}
