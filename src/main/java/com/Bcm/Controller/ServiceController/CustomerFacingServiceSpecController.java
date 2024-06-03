package com.Bcm.Controller.ServiceController;

import com.Bcm.Exception.InvalidInputException;
import com.Bcm.Exception.ServiceAlreadyExistsException;
import com.Bcm.Exception.ServiceLogicException;
import com.Bcm.Model.ServiceABE.CustomerFacingServiceSpec;
import com.Bcm.Service.Srvc.ProductResourceSrvc.LogicalResourceService;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.CustomerFacingServiceSpecService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/CustomerFacingServiceSpec")
@RequiredArgsConstructor
public class CustomerFacingServiceSpecController {

    final CustomerFacingServiceSpecService customerFacingServiceSpecService;
    final LogicalResourceService logicalResourceService;

    @PostMapping("/addCustomerFacingServiceSpec")
    public ResponseEntity<?> createCustomerFacingServiceSpec(@RequestBody CustomerFacingServiceSpec customerFacingServiceSpec) {
        try {


            // Check if customerFacingServiceSpec with the same name exists
            if (customerFacingServiceSpecService.existsByName(customerFacingServiceSpec.getName())) {
                return ResponseEntity.badRequest().body("A product CustomerFacingServiceSpec with the same name already exists.");
            }

            CustomerFacingServiceSpec createdCustomerFacingServiceSpec = customerFacingServiceSpecService.create(customerFacingServiceSpec);
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
            CustomerFacingServiceSpec updatedProduct = customerFacingServiceSpecService.update(serviceId, updatedCustomerFacingServiceSpec);
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


}
