package com.Bcm.Controller.ProductOfferingController.SubClassesController;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Status;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/status")
public class StatusController {

    @Autowired
    private StatusService statusService;

    @PostMapping
    public ResponseEntity<Status> createStatus(@RequestBody Status Status) {
        try {
            Status createdStatus = statusService.create(Status);
            return ResponseEntity.ok(createdStatus);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Status>> getAllStatuss() {
        try {
            List<Status> Statuss = statusService.read();
            return ResponseEntity.ok(Statuss);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{pos_code}")
    public ResponseEntity<Status> getStatusById(@PathVariable("pos_code") int pos_code) {
        try {
            Status Status = statusService.findById(pos_code);
            return ResponseEntity.ok(Status);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("/{pos_code}")
    public ResponseEntity<Status> updateStatus(
            @PathVariable("pos_code") int pos_code,
            @RequestBody Status updatedStatus) {

        try {
            Status updatedStatusResult = statusService.update(pos_code, updatedStatus);
            return ResponseEntity.ok(updatedStatusResult);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/{pos_code}")
    public ResponseEntity<String> deleteStatus(@PathVariable("pos_code") int pos_code) {
        try {
            String resultMessage = statusService.delete(pos_code);
            return ResponseEntity.ok(resultMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Status>> searchStatussByKeyword(@RequestParam("name") String name) {
        try {
            List<Status> searchResults = statusService.searchByKeyword(name);
            return ResponseEntity.ok(searchResults);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
