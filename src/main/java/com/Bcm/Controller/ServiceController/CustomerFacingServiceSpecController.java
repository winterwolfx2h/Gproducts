package com.Bcm.Controller.ServiceController;

import com.Bcm.Exception.InvalidInputException;
import com.Bcm.Exception.ServiceAlreadyExistsException;
import com.Bcm.Exception.ServiceLogicException;
import com.Bcm.Model.ServiceABE.CustomerFacingServiceSpec;
import com.Bcm.Service.Srvc.ProductResourceSrvc.LogicalResourceService;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.CustomerFacingServiceSpecService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Customer Facing Service Controller", description = "All of the Customer Facing Service's methods")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/CustomerFacingServiceSpec")
@RequiredArgsConstructor
public class CustomerFacingServiceSpecController {

  final JdbcTemplate base;

  final CustomerFacingServiceSpecService customerFacingServiceSpecService;
  final LogicalResourceService logicalResourceService;

  @PostMapping("/addCustomerFacingServiceSpec")
  public ResponseEntity<?> createCustomerFacingServiceSpec(
      @RequestBody CustomerFacingServiceSpec customerFacingServiceSpec) {
    try {

      // Check if customerFacingServiceSpec with the same name exists
      if (customerFacingServiceSpecService.existsByName(customerFacingServiceSpec.getName())) {
        return ResponseEntity.badRequest()
            .body("A product CustomerFacingServiceSpec with the same name already exists.");
      }

      CustomerFacingServiceSpec createdCustomerFacingServiceSpec =
          customerFacingServiceSpecService.create(customerFacingServiceSpec);
      return ResponseEntity.ok(createdCustomerFacingServiceSpec);

    } catch (ServiceAlreadyExistsException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    } catch (InvalidInputException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (RuntimeException e) {
      // Log the exception message for debugging
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
    }
  }

  @GetMapping("/listCustomerFacingServiceSpecs")
  public ResponseEntity<?> getAllCustomerFacingServiceSpecs() {
    try {
      List<CustomerFacingServiceSpec> customerFacingServiceSpecs = customerFacingServiceSpecService.read();
      return ResponseEntity.ok(customerFacingServiceSpecs);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }
  }

  @GetMapping("/{serviceId}")
  public ResponseEntity<?> getCustomerFacingServiceSpecById(@PathVariable("serviceId") int serviceId) {
    try {
      CustomerFacingServiceSpec customerFacingServiceSpec = customerFacingServiceSpecService.findById(serviceId);
      return ResponseEntity.ok(customerFacingServiceSpec);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }
  }

  @PutMapping("/{serviceId}")
  public ResponseEntity<?> updateCustomerFacingServiceSpec(
      @PathVariable("serviceId") int serviceId,
      @RequestBody CustomerFacingServiceSpec updatedCustomerFacingServiceSpec) {
    try {
      CustomerFacingServiceSpec updatedProduct =
          customerFacingServiceSpecService.update(serviceId, updatedCustomerFacingServiceSpec);
      return ResponseEntity.ok(updatedProduct);
    } catch (ServiceAlreadyExistsException e) {
      return ResponseEntity.badRequest().body("Service with the same type already exists");
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }
  }

  @DeleteMapping("/{serviceId}")
  public ResponseEntity<?> deleteCustomerFacingServiceSpec(@PathVariable("serviceId") int serviceId) {
    try {
      String resultMessage = customerFacingServiceSpecService.delete(serviceId);
      return ResponseEntity.ok(resultMessage);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }
  }

  @PutMapping("/changeStatus/{serviceId}")
  public ResponseEntity<?> changeCustomerFacingServiceSpecStatus(@PathVariable int serviceId) {
    try {
      CustomerFacingServiceSpec updatedService = customerFacingServiceSpecService.changeServiceStatus(serviceId);
      return ResponseEntity.ok(updatedService);

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
        "SELECT cfss.service_id AS service_id, "
            + "cfss.name AS service_name, "
            + "lr.lr_id AS lr_id, "
            + "lr.po_market_code AS market_code, "
            + "market.name AS market_name, "
            + "lr.po_sub_market_code AS sub_market_code, "
            + "sub_market.sub_market_name AS sub_market_name "
            + "FROM public.logical_resource lr "
            + "JOIN public.customer_facing_service_spec cfss ON lr.lr_id = cfss.lr_id "
            + "JOIN public.market market ON lr.po_market_code = market.po_market_code "
            + "JOIN public.sub_market sub_market ON lr.po_sub_market_code = sub_market.po_sub_market_code "
            + "WHERE market.name = ? AND sub_market.sub_market_name = ? "
            + "ORDER BY lr.lr_id ASC, cfss.service_id ASC";

    List<Map<String, Object>> result =
        base.query(
            sqlSearchByMarketSubMarket,
            new Object[] {marketName, subMarketName},
            new RowMapper<Map<String, Object>>() {
              @Override
              public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
                Map<String, Object> response = new HashMap<>();
                response.put("service_id", rs.getInt("service_id"));
                response.put("service_name", rs.getString("service_name"));
                response.put("lr_id", rs.getInt("lr_id"));
                response.put("market_code", rs.getInt("market_code"));
                response.put("market_name", rs.getString("market_name"));
                response.put("sub_market_code", rs.getInt("sub_market_code"));
                response.put("sub_market_name", rs.getString("sub_market_name"));
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
