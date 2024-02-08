package com.Bcm.Controller.ServiceController.SubClassesController;

import com.Bcm.Model.ServiceABE.SubClasses.CustomerType;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.SubClassesSrvc.CustomerTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/CustomerType")
public class CustomerTypeController {

    private final CustomerTypeService customerTypeService;

    @Autowired
    public CustomerTypeController(CustomerTypeService customerTypeService) {
        this.customerTypeService = customerTypeService;
    }

    @PostMapping
    public ResponseEntity<CustomerType> createCustomerType(@RequestBody CustomerType customerType) {
        try {
            CustomerType createdCustomerType = customerTypeService.create(customerType);
            return ResponseEntity.ok(createdCustomerType);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<CustomerType>> getAllCustomerTypes() {
        try {
            List<CustomerType> customerTypes = customerTypeService.read();
            return ResponseEntity.ok(customerTypes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{CT_code}")
    public ResponseEntity<CustomerType> getCustomerTypeById(@PathVariable("CT_code") int CT_code) {
        try {
            CustomerType customerType = customerTypeService.findById(CT_code);
            return ResponseEntity.ok(customerType);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{CT_code}")
    public ResponseEntity<CustomerType> updateCustomerType(
            @PathVariable("CT_code") int CT_code,
            @RequestBody CustomerType updatedCustomerType) {
        try {
            CustomerType updatedCustomerTypeResult = customerTypeService.update(CT_code, updatedCustomerType);
            return ResponseEntity.ok(updatedCustomerTypeResult);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{CT_code}")
    public ResponseEntity<String> deleteCustomerType(@PathVariable("CT_code") int CT_code) {
        try {
            String resultMessage = customerTypeService.delete(CT_code);
            return ResponseEntity.ok(resultMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<CustomerType>> searchCustomerTypesByKeyword(@RequestParam("name") String name) {
        try {
            List<CustomerType> searchResults = customerTypeService.searchByKeyword(name);
            return ResponseEntity.ok(searchResults);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
