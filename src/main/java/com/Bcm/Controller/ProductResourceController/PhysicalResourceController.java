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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "Physical Resource Controller", description = "All of the Physical Resource's methods")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/physicalResource")
public class PhysicalResourceController {
    final JdbcTemplate base;
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

    @GetMapping("/searchByMarketSubMarket")
    public List<Map<String, Object>> searchByMarketSubMarket(
            @RequestParam String marketName, @RequestParam String subMarketName) {

        String sqlSearchByMarketSubMarket =
                "SELECT "
                        + "    lr.name AS resource_name, "
                        + "    lr.po_market_code, "
                        + "    market.name AS market_name, "
                        + "    lr.po_sub_market_code, "
                        + "    sub_market.sub_market_name, "
                        + "    lr.pr_id AS pr_id, "
                        + "    lr.physical_resource_format, "
                        + // Include physical_resource_format
                        "    lr.physical_resource_type "
                        + // Include physical_resource_type
                        "FROM public.physical_resource lr "
                        + "JOIN public.market market ON lr.po_market_code = market.po_market_code "
                        + "JOIN public.sub_market sub_market ON lr.po_sub_market_code = sub_market.po_sub_market_code "
                        + "WHERE market.name = ? AND sub_market.sub_market_name = ? "
                        + "ORDER BY lr.pr_id ASC, market.po_market_code ASC, sub_market.po_sub_market_code ASC;";

        List<Map<String, Object>> result =
                base.query(
                        sqlSearchByMarketSubMarket,
                        new Object[]{marketName, subMarketName},
                        new RowMapper<Map<String, Object>>() {
                            @Override
                            public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
                                Map<String, Object> response = new HashMap<>();
                                response.put("resource_name", rs.getString("resource_name"));
                                response.put("po_market_code", rs.getInt("po_market_code"));
                                response.put("market_name", rs.getString("market_name")); // corrected key name
                                response.put("po_sub_market_code", rs.getInt("po_sub_market_code"));
                                response.put("sub_market_name", rs.getString("sub_market_name"));
                                response.put("pr_id", rs.getString("pr_id"));
                                response.put(
                                        "physical_resource_format",
                                        rs.getString("physical_resource_format")); // Add physical_resource_format
                                response.put(
                                        "physical_resource_type", rs.getString("physical_resource_type")); // Add physical_resource_type
                                return response;
                            }
                        });

        // Check if any results were found
        if (result.isEmpty()) {
            throw new IllegalArgumentException("No data found for market " + marketName + " and sub-market " + subMarketName);
        }

        return result;
    }
}
