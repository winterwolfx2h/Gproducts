package com.Bcm.Controller.ServiceController.SubClassesController;

import com.Bcm.Model.ServiceABE.SubClasses.DependentService;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.SubClassesSrvc.DependentServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/DependentService")
public class DependentServiceController {

    @Autowired
    private DependentServiceService dependentServiceService;

    @PostMapping
    public ResponseEntity<DependentService> createDependentService(@RequestBody DependentService DependentService) {
        DependentService createdDependentService = dependentServiceService.create(DependentService);
        return ResponseEntity.ok(createdDependentService);
    }

    @GetMapping
    public ResponseEntity<List<DependentService>> getAllDependentServices() {
        List<DependentService> DependentServices = dependentServiceService.read();
        return ResponseEntity.ok(DependentServices);
    }

    @GetMapping("/{DS_code}")
    public ResponseEntity<DependentService> getDependentServiceById(@PathVariable("DS_code") int DS_code) {
        DependentService DependentService = dependentServiceService.findById(DS_code);
        return ResponseEntity.ok(DependentService);
    }

    @PutMapping("/{DS_code}")
    public ResponseEntity<DependentService> updateDependentService(
            @PathVariable("DS_code") int DS_code,
            @RequestBody DependentService updatedDependentService) {

        DependentService updatedGroup = dependentServiceService.update(DS_code, updatedDependentService);
        return ResponseEntity.ok(updatedGroup);
    }

    @DeleteMapping("/{DS_code}")
    public ResponseEntity<String> deleteDependentService(@PathVariable("DS_code") int DS_code) {
        String resultMessage = dependentServiceService.delete(DS_code);
        return ResponseEntity.ok(resultMessage);
    }

    @GetMapping("/search")
    public ResponseEntity<List<DependentService>> searchDependentServicesByKeyword(@RequestParam("name") String name) {
        List<DependentService> searchResults = dependentServiceService.searchByKeyword(name);
        return ResponseEntity.ok(searchResults);
    }
}
