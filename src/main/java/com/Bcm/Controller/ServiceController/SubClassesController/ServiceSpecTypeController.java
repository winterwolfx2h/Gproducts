package com.Bcm.Controller.ServiceController.SubClassesController;

import com.Bcm.Model.ServiceABE.SubClasses.ServiceSpecType;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.SubClassesSrvc.ServiceSpecTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/ServiceSpecType")
public class ServiceSpecTypeController {

    @Autowired
    private ServiceSpecTypeService serviceSpecTypeService;

    @PostMapping
    public ResponseEntity<ServiceSpecType> createServiceSpecType(@RequestBody ServiceSpecType ServiceSpecType) {
        ServiceSpecType createdServiceSpecType = serviceSpecTypeService.create(ServiceSpecType);
        return ResponseEntity.ok(createdServiceSpecType);
    }

    @GetMapping
    public ResponseEntity<List<ServiceSpecType>> getAllServiceSpecTypes() {
        List<ServiceSpecType> ServiceSpecTypes = serviceSpecTypeService.read();
        return ResponseEntity.ok(ServiceSpecTypes);
    }

    @GetMapping("/{SST_code}")
    public ResponseEntity<ServiceSpecType> getServiceSpecTypeById(@PathVariable("SST_code") int SST_code) {
        ServiceSpecType ServiceSpecType = serviceSpecTypeService.findById(SST_code);
        return ResponseEntity.ok(ServiceSpecType);
    }

    @PutMapping("/{SST_code}")
    public ResponseEntity<ServiceSpecType> updateServiceSpecType(
            @PathVariable("SST_code") int SST_code,
            @RequestBody ServiceSpecType updatedServiceSpecType) {

        ServiceSpecType updatedGroup = serviceSpecTypeService.update(SST_code, updatedServiceSpecType);
        return ResponseEntity.ok(updatedGroup);
    }

    @DeleteMapping("/{SST_code}")
    public ResponseEntity<String> deleteServiceSpecType(@PathVariable("SST_code") int SST_code) {
        String resultMessage = serviceSpecTypeService.delete(SST_code);
        return ResponseEntity.ok(resultMessage);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ServiceSpecType>> searchServiceSpecTypesByKeyword(@RequestParam("name") String name) {
        List<ServiceSpecType> searchResults = serviceSpecTypeService.searchByKeyword(name);
        return ResponseEntity.ok(searchResults);
    }
}
