package com.Bcm.Controller.ProductOfferingController.SubClassesController;

import com.Bcm.Exception.FamilyAlreadyExistsException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Family.*;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.FamilyService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Family Controller", description = "All of the Families methods")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/family")
@RequiredArgsConstructor
public class FamilyController {

  final FamilyService familyService;

  @ApiOperation(value = "Create a new Family", response = FamilyResponseDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 201, message = "Family created successfully", response = FamilyResponseDTO.class),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 500, message = "Internal server error")
      })
  @PostMapping("/createFamily")
  public ResponseEntity<FamilyResponseDTO> createFamily(@RequestBody FamilyRequestDTO familyRequestDTO) {
    try {
      FamilyResponseDTO createdFamily = familyService.createOrUpdateFamily(familyRequestDTO);
      return ResponseEntity.status(HttpStatus.CREATED).body(createdFamily);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @GetMapping("/subFamiliesByFamily")
  public ResponseEntity<?> getSubFamiliesByFamily(@RequestParam String name) {
    Family family = familyService.findByName(name);
    if (family == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body("Family with name '" + name + "' does not exist in the database.");
    }

    List<SubFamily> subFamilies = family.getSubFamilies();

    if (subFamilies.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No subfamilies found for family name: " + name);
    }

    List<Map<String, Object>> response = new ArrayList<>();
    for (SubFamily subFamily1 : subFamilies) {
      Map<String, Object> subFamilyDetails = new HashMap<>();
      subFamilyDetails.put("po_sub_family_code", subFamily1.getPo_SubFamilyCode());
      subFamilyDetails.put("sub_family_name", subFamily1.getSubFamilyName());
      subFamilyDetails.put("familyName", family.getName());
      Map<String, Object> apply = subFamilyDetails;
      response.add(apply);
    }

    return ResponseEntity.ok(response);
  }

  @GetMapping("/listFamily")
  public ResponseEntity<List<FamilyResponseDTO>> getAllFamilies() {
    try {
      List<FamilyResponseDTO> families = familyService.getAllFamilies();
      return ResponseEntity.ok(families);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @GetMapping("/{po_FamilyCode}")
  public ResponseEntity<Family> getFamilyById(@PathVariable("po_FamilyCode") int po_FamilyCode) {
    try {
      Family family = familyService.findById(po_FamilyCode);
      return ResponseEntity.ok(family);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
  }

  @PutMapping("/{po_FamilyCode}")
  public ResponseEntity<?> updateFamily(
      @PathVariable("po_FamilyCode") int po_FamilyCode, @RequestBody FamilyRequestDTOUpdate familyRequestDTO) {
    try {
      FamilyResponseDTO updatedFamily = familyService.update(po_FamilyCode, familyRequestDTO);
      return ResponseEntity.ok(updatedFamily);
    } catch (FamilyAlreadyExistsException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
    }
  }

  @DeleteMapping("/{po_FamilyCode}")
  public ResponseEntity<String> deleteFamily(@PathVariable("po_FamilyCode") int po_FamilyCode) {
    try {
      String resultMessage = familyService.delete(po_FamilyCode);
      return ResponseEntity.ok(resultMessage);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @GetMapping("/search")
  public ResponseEntity<List<Family>> searchFamiliesByKeyword(@RequestParam("name") String name) {
    try {
      List<Family> searchResults = familyService.searchByKeyword(name);
      return ResponseEntity.ok(searchResults);
    } catch (RuntimeException e) {
      return ResponseEntity.status(500).body(null);
    }
  }

  @PutMapping("/unlinkSubFamily")
  public ResponseEntity<String> unlinkSubFamilyFromFamily(@RequestParam int familyId, @RequestParam int subFamilyId) {
    try {
      familyService.unlinkSubFamilyFromFamily(familyId, subFamilyId);
      return ResponseEntity.ok("SubFamily with ID " + subFamilyId + " unlinked from Family with ID " + familyId);
    } catch (ResourceNotFoundException | IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
    }
  }

  @GetMapping("/listSubFamilies")
  public ResponseEntity<List<SubFamilyResponseDTO>> getAllSubFamilies() {
    try {
      List<SubFamily> subFamilies = familyService.readSubFamilies();
      List<SubFamilyResponseDTO> subFamilyResponseDTOs = new ArrayList<>();
      for (SubFamily subFamily : subFamilies) {
        SubFamilyResponseDTO subFamilyResponseDTO =
            new SubFamilyResponseDTO(
                subFamily.getPo_SubFamilyCode(), subFamily.getSubFamilyName(), subFamily.getSubFamilyDescription());
        subFamilyResponseDTOs.add(subFamilyResponseDTO);
      }
      return ResponseEntity.ok(subFamilyResponseDTOs);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @DeleteMapping("/subFamily/{po_SubFamilyCode}")
  public ResponseEntity<String> deleteSubFamily(@PathVariable int po_SubFamilyCode) {
    try {
      familyService.deleteSubFamily(po_SubFamilyCode);
      return ResponseEntity.ok("SubFamily with ID " + po_SubFamilyCode + " was successfully deleted");
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }
  }
}
