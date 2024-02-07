package com.Bcm.Controller.ServiceController.SubClassesController;

import com.Bcm.Model.ServiceABE.SubClasses.ServiceStatus;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.SubClassesSrvc.ServiceStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/ServiceStatus")
public class ServiceStatusController {

    @Autowired
    private ServiceStatusService serviceStatusService;

    @PostMapping
    public ResponseEntity<ServiceStatus> createServiceStatus(@RequestBody ServiceStatus ServiceStatus) {
        ServiceStatus createdServiceStatus = serviceStatusService.create(ServiceStatus);
        return ResponseEntity.ok(createdServiceStatus);
    }

    @GetMapping
    public ResponseEntity<List<ServiceStatus>> getAllServiceStatuss() {
        List<ServiceStatus> ServiceStatuss = serviceStatusService.read();
        return ResponseEntity.ok(ServiceStatuss);
    }

    @GetMapping("/{SS_code}")
    public ResponseEntity<ServiceStatus> getServiceStatusById(@PathVariable("SS_code") int SS_code) {
        ServiceStatus ServiceStatus = serviceStatusService.findById(SS_code);
        return ResponseEntity.ok(ServiceStatus);
    }

    @PutMapping("/{SS_code}")
    public ResponseEntity<ServiceStatus> updateServiceStatus(
            @PathVariable("SS_code") int SS_code,
            @RequestBody ServiceStatus updatedServiceStatus) {

        ServiceStatus updatedGroup = serviceStatusService.update(SS_code, updatedServiceStatus);
        return ResponseEntity.ok(updatedGroup);
    }

    @DeleteMapping("/{SS_code}")
    public ResponseEntity<String> deleteServiceStatus(@PathVariable("SS_code") int SS_code) {
        String resultMessage = serviceStatusService.delete(SS_code);
        return ResponseEntity.ok(resultMessage);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ServiceStatus>> searchServiceStatussByKeyword(@RequestParam("name") String name) {
        List<ServiceStatus> searchResults = serviceStatusService.searchByKeyword(name);
        return ResponseEntity.ok(searchResults);
    }
}
