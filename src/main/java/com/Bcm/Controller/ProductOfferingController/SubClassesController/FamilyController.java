package com.Bcm.Controller.ProductOfferingController.SubClassesController;

import com.Bcm.Exception.FamilyAlreadyExistsException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Family.*;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.FamilyService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Tag(name = "Family Controller", description = "All of the Families methods")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/family")
@RequiredArgsConstructor
public class FamilyController {

    final FamilyService familyService;
    final JdbcTemplate jdbcTemplate;

    @ApiOperation(value = "Create a new Family", response = FamilyResponseDTO.class)
    @ApiResponses(value = {
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
        Optional<Integer> familyId = findFamilyIdByName(name);
        if (familyId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Family with name '" + name + "' does not exist in the database.");
        }

        String sql = "SELECT sf.po_sub_family_code, sf.sub_family_name, f.name AS familyName " +
                "FROM sub_family sf " +
                "JOIN family f ON f.po_family_code = sf.family_code " +
                "WHERE f.name = ?";

        try {
            List<Map<String, Object>> subFamilies = jdbcTemplate.queryForList(sql, name);
            if (subFamilies.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No subfamilies found for family name: " + name);
            }
            return ResponseEntity.ok(subFamilies);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving subfamilies: " + e.getMessage());
        }
    }

    private Optional<Integer> findFamilyIdByName(String name) {
        String sql = "SELECT po_family_code FROM family WHERE name = ?";
        try {
            Integer familyId = jdbcTemplate.queryForObject(sql, Integer.class, name);
            return Optional.ofNullable(familyId);
        } catch (Exception e) {
            return Optional.empty();
        }
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
    public ResponseEntity<String> unlinkSubFamilyFromFamily(
            @RequestParam int familyId, @RequestParam int subFamilyId) {
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
            List<SubFamilyResponseDTO> subFamilyResponseDTOs = subFamilies.stream()
                    .map(subFamily -> new SubFamilyResponseDTO(subFamily.getPo_SubFamilyCode(),
                            subFamily.getSubFamilyName(),
                            subFamily.getSubFamilyDescription()))
                    .collect(Collectors.toList());
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

