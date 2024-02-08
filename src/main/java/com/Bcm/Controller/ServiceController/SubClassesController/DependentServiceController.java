package com.Bcm.Controller.ServiceController.SubClassesController;

import com.Bcm.Model.ServiceABE.SubClasses.DependentService;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.SubClassesSrvc.DependentServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/DependentService")
public class DependentServiceController {

    private final DependentServiceService dependentServiceService;

    @Autowired
    public DependentServiceController(DependentServiceService dependentServiceService) {
        this.dependentServiceService = dependentServiceService;
    }

    @PostMapping
    public ResponseEntity<DependentService> createDependentService(@RequestBody DependentService dependentService) {
        try {
            DependentService createdDependentService = dependentServiceService.create(dependentService);
            return ResponseEntity.ok(createdDependentService);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<DependentService>> getAllDependentServices() {
        try {
            List<DependentService> dependentServices = dependentServiceService.read();
            return ResponseEntity.ok(dependentServices);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{DS_code}")
    public ResponseEntity<DependentService> getDependentServiceById(@PathVariable("DS_code") int DS_code) {
        try {
            DependentService dependentService = dependentServiceService.findById(DS_code);
            return ResponseEntity.ok(dependentService);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{DS_code}")
    public ResponseEntity<DependentService> updateDependentService(
            @PathVariable("DS_code") int DS_code,
            @RequestBody DependentService updatedDependentService) {
        try {
            DependentService updatedDependentServiceResult = dependentServiceService.update(DS_code, updatedDependentService);
            return ResponseEntity.ok(updatedDependentServiceResult);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{DS_code}")
    public ResponseEntity<String> deleteDependentService(@PathVariable("DS_code") int DS_code) {
        try {
            String resultMessage = dependentServiceService.delete(DS_code);
            return ResponseEntity.ok(resultMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<DependentService>> searchDependentServicesByKeyword(@RequestParam("name") String name) {
        try {
            List<DependentService> searchResults = dependentServiceService.searchByKeyword(name);
            return ResponseEntity.ok(searchResults);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
