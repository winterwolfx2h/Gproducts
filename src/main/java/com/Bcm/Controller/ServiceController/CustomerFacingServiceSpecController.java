package com.Bcm.Controller.ServiceController;

import com.Bcm.Exception.ServiceAlreadyExistsException;
import com.Bcm.Exception.ServiceLogicException;
import com.Bcm.Model.ServiceABE.CustomerFacingServiceSpec;
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

    @PostMapping("/addCustomerFacingServiceSpec")
    public ResponseEntity<?> createCustomerFacingServiceSpec(@RequestBody CustomerFacingServiceSpec customerFacingServiceSpec) {
        try {
            if (customerFacingServiceSpec.getResourceFacingServiceSpec() != null) {
                customerFacingServiceSpec.getResourceFacingServiceSpec().setStatus("Working state");
            }

            CustomerFacingServiceSpec createdCustomerFacingServiceSpec = customerFacingServiceSpecService.create(customerFacingServiceSpec);
            return ResponseEntity.ok(createdCustomerFacingServiceSpec);
        } catch (ServiceAlreadyExistsException e) {
            return ResponseEntity.badRequest().body("Service with the same name already exists");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
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

    @GetMapping("/{CFSS_code}")
    public ResponseEntity<?> getCustomerFacingServiceSpecById(@PathVariable("CFSS_code") int CFSS_code) {
        try {
            CustomerFacingServiceSpec customerFacingServiceSpec = customerFacingServiceSpecService.findById(CFSS_code);
            return ResponseEntity.ok(customerFacingServiceSpec);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @PutMapping("/{CFSS_code}")
    public ResponseEntity<?> updateCustomerFacingServiceSpec(
            @PathVariable("CFSS_code") int CFSS_code,
            @RequestBody CustomerFacingServiceSpec updatedCustomerFacingServiceSpec) {
        try {
            CustomerFacingServiceSpec updatedProduct = customerFacingServiceSpecService.update(CFSS_code, updatedCustomerFacingServiceSpec);
            return ResponseEntity.ok(updatedProduct);
        } catch (ServiceAlreadyExistsException e) {
            return ResponseEntity.badRequest().body("Service with the same name already exists");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @DeleteMapping("/{CFSS_code}")
    public ResponseEntity<?> deleteCustomerFacingServiceSpec(@PathVariable("CFSS_code") int CFSS_code) {
        try {
            String resultMessage = customerFacingServiceSpecService.delete(CFSS_code);
            return ResponseEntity.ok(resultMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @PutMapping("/changeStatus/{CFSS_code}")
    public ResponseEntity<?> changeCustomerFacingServiceSpecStatus(@PathVariable int CFSS_code) {
        try {
            CustomerFacingServiceSpec updatedService = customerFacingServiceSpecService.changeServiceStatus(CFSS_code);
            return ResponseEntity.ok(updatedService);

        } catch (ServiceLogicException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }


}
