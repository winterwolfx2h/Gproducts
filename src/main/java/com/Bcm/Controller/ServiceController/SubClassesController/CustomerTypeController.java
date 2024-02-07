package com.Bcm.Controller.ServiceController.SubClassesController;

import com.Bcm.Model.ServiceABE.SubClasses.CustomerType;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.SubClassesSrvc.CustomerTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/CustomerType")
public class CustomerTypeController {

    @Autowired
    private CustomerTypeService customerTypeService;

    @PostMapping
    public ResponseEntity<CustomerType> createCustomerType(@RequestBody CustomerType CustomerType) {
        CustomerType createdCustomerType = customerTypeService.create(CustomerType);
        return ResponseEntity.ok(createdCustomerType);
    }

    @GetMapping
    public ResponseEntity<List<CustomerType>> getAllCustomerTypes() {
        List<CustomerType> CustomerTypes = customerTypeService.read();
        return ResponseEntity.ok(CustomerTypes);
    }

    @GetMapping("/{CT_code}")
    public ResponseEntity<CustomerType> getCustomerTypeById(@PathVariable("CT_code") int CT_code) {
        CustomerType CustomerType = customerTypeService.findById(CT_code);
        return ResponseEntity.ok(CustomerType);
    }

    @PutMapping("/{CT_code}")
    public ResponseEntity<CustomerType> updateCustomerType(
            @PathVariable("CT_code") int CT_code,
            @RequestBody CustomerType updatedCustomerType) {

        CustomerType updatedGroup = customerTypeService.update(CT_code, updatedCustomerType);
        return ResponseEntity.ok(updatedGroup);
    }

    @DeleteMapping("/{CT_code}")
    public ResponseEntity<String> deleteCustomerType(@PathVariable("CT_code") int CT_code) {
        String resultMessage = customerTypeService.delete(CT_code);
        return ResponseEntity.ok(resultMessage);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CustomerType>> searchCustomerTypesByKeyword(@RequestParam("name") String name) {
        List<CustomerType> searchResults = customerTypeService.searchByKeyword(name);
        return ResponseEntity.ok(searchResults);
    }
}
