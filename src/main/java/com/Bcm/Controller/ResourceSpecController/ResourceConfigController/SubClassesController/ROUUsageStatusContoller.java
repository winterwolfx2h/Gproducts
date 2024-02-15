package com.Bcm.Controller.ResourceSpecController.ResourceConfigController.SubClassesController;

import com.Bcm.Model.ResourceSpecABE.ResourceConfigBE.SubClasses.ROUUsageStatus;
import com.Bcm.Service.Srvc.ResourceSpecService.ResourceConfigService.SubClasses.ROUUsageStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/ROUUsageStatus")
public class ROUUsageStatusContoller {

    @Autowired
    ROUUsageStatusService ROUUsageStatusService;

    @PostMapping
    public ResponseEntity<ROUUsageStatus> createROUUsageStatus(@RequestBody ROUUsageStatus ROUUsageStatus) {
        ROUUsageStatus createdROUUsageStatus = ROUUsageStatusService.create(ROUUsageStatus);
        return ResponseEntity.ok(createdROUUsageStatus);
    }

    @GetMapping
    public ResponseEntity<List<ROUUsageStatus>> getAllCategories() {
        List<ROUUsageStatus> categories = ROUUsageStatusService.read();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{RUSID}")
    public ResponseEntity<ROUUsageStatus> getROUUsageStatusById(@PathVariable("RUSID") int RUSID) {
        ROUUsageStatus ROUUsageStatus = ROUUsageStatusService.findById(RUSID);
        return ResponseEntity.ok(ROUUsageStatus);
    }

    @PutMapping("/{RUSID}")
    public ResponseEntity<ROUUsageStatus> updateROUUsageStatus(
            @PathVariable("RUSID") int RUSID,
            @RequestBody ROUUsageStatus updatedROUUsageStatus) {

        ROUUsageStatus updated = ROUUsageStatusService.update(RUSID, updatedROUUsageStatus);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{RUSID}")
    public ResponseEntity<String> deleteROUUsageStatus(@PathVariable("RUSID") int RUSID) {
        String resultMessage = ROUUsageStatusService.delete(RUSID);
        return ResponseEntity.ok(resultMessage);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ROUUsageStatus>> searchCategoriesByKeyword(@RequestParam("name") String name) {
        List<ROUUsageStatus> searchResults = ROUUsageStatusService.searchByKeyword(name);
        return ResponseEntity.ok(searchResults);
    }
}