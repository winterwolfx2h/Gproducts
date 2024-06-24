package com.Bcm.Controller.ProductOfferingController.SubClassesController;

import com.Bcm.Exception.FamilyAlreadyExistsException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ProductOfferingABE.SubClasses.*;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.FamilyService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Family Controller", description = "All of the Families methods")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/family")
@RequiredArgsConstructor
public class FamilyController {

    final FamilyService familyService;
    final JdbcTemplate jdbcTemplate;

    @PostMapping("/addFamily")
    public ResponseEntity<?> createFamily(@RequestBody Family family) {
        try {
            Family createdFamily = familyService.create(family);
            return ResponseEntity.ok(createdFamily);
        } catch (FamilyAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @ApiOperation(value = "Create a new Family", response = FamilyResponseDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Family created successfully", response = FamilyResponseDTO.class),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })

    @PostMapping("/CreateFamily")
    public ResponseEntity<FamilyResponseDTO> createFamily(@RequestBody FamilyRequestDTO familyRequestDTO) {
        Family createdFamily = familyService.createOrUpdateFamily(familyRequestDTO);
        FamilyResponseDTO responseDTO = convertToResponseDTO(createdFamily);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    private FamilyResponseDTO convertToResponseDTO(Family family) {
        FamilyResponseDTO responseDTO = new FamilyResponseDTO();
        responseDTO.setPo_FamilyCode(family.getPo_FamilyCode());
        responseDTO.setName(family.getName());
        responseDTO.setDescription(family.getDescription());

        List<SubFamilyResponseDTO> subFamiliesDTO = new ArrayList<>();
        for (SubFamily subFamily : family.getSubFamilies()) {
            SubFamilyResponseDTO subFamilyDTO = new SubFamilyResponseDTO();
            subFamilyDTO.setPo_SubFamilyCode(subFamily.getPo_SubFamilyCode());
            subFamilyDTO.setSubFamilyName(subFamily.getSubFamilyName());
            subFamiliesDTO.add(subFamilyDTO);
        }
        responseDTO.setSubFamilies(subFamiliesDTO);

        return responseDTO;
    }


    @GetMapping("/bySubFamily")
    public ResponseEntity<List<Family>> getFamiliesBySubFamily(@RequestParam String subFamilyName) {
        String sql = "SELECT f.po_family_code, f.name, f.description, f.sub_family_code " +
                "FROM Family f " +
                "JOIN sub_family s ON f.sub_family_code = s.po_sub_family_code " +
                "WHERE f.name = ?";

        List<Family> families = jdbcTemplate.query(sql, new Object[]{subFamilyName}, new BeanPropertyRowMapper<>(Family.class));
        return ResponseEntity.ok(families);
    }

    @GetMapping("/listFamily")
    public ResponseEntity<List<Family>> getAllFamilies() {
        try {
            List<Family> families = familyService.read();
            return ResponseEntity.ok(families);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{po_FamilyCode}")
    public ResponseEntity<Family> getFamilyById(@PathVariable("po_FamilyCode") int po_FamilyCode) {
        try {
            Family family = familyService.findById(po_FamilyCode);
            return ResponseEntity.ok(family);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("/{po_FamilyCode}")
    public ResponseEntity<?> updateFamily(
            @PathVariable("po_FamilyCode") int po_FamilyCode, @RequestBody Family updatedFamily) {
        try {
            Family updatedGroup = familyService.update(po_FamilyCode, updatedFamily);
            return ResponseEntity.ok(updatedGroup);
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
            return ResponseEntity.status(500).body(null);
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
}
