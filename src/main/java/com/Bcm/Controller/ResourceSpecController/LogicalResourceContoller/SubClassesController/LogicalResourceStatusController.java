package com.Bcm.Controller.ResourceSpecController.LogicalResourceContoller.SubClassesController;

import com.Bcm.Model.ResourceSpecABE.LogicalResourceBE.SubClasses.LogicalResourceStatus;
import com.Bcm.Service.Srvc.ResourceSpecService.LogicalResourceService.SubClasses.LogicalResourceStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/LogicalResourceStatus")
public class LogicalResourceStatusController {

    @Autowired
    LogicalResourceStatusService LogicalResourceStatusService;

    @PostMapping
    public ResponseEntity<LogicalResourceStatus> createLogicalResourceStatus(@RequestBody LogicalResourceStatus LogicalResourceStatus) {
        LogicalResourceStatus createdLogicalResourceStatus = LogicalResourceStatusService.create(LogicalResourceStatus);
        return ResponseEntity.ok(createdLogicalResourceStatus);
    }

    @GetMapping
    public ResponseEntity<List<LogicalResourceStatus>> getAllCategories() {
        List<LogicalResourceStatus> categories = LogicalResourceStatusService.read();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{LRSID}")
    public ResponseEntity<LogicalResourceStatus> getLogicalResourceStatusById(@PathVariable("LRSID") int LRSID) {
        LogicalResourceStatus LogicalResourceStatus = LogicalResourceStatusService.findById(LRSID);
        return ResponseEntity.ok(LogicalResourceStatus);
    }

    @PutMapping("/{LRSID}")
    public ResponseEntity<LogicalResourceStatus> updateLogicalResourceStatus(
            @PathVariable("LRSID") int LRSID,
            @RequestBody LogicalResourceStatus updatedLogicalResourceStatus) {

        LogicalResourceStatus updated = LogicalResourceStatusService.update(LRSID, updatedLogicalResourceStatus);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{LRSID}")
    public ResponseEntity<String> deleteLogicalResourceStatus(@PathVariable("LRSID") int LRSID) {
        String resultMessage = LogicalResourceStatusService.delete(LRSID);
        return ResponseEntity.ok(resultMessage);
    }

    @GetMapping("/search")
    public ResponseEntity<List<LogicalResourceStatus>> searchCategoriesByKeyword(@RequestParam("name") String name) {
        List<LogicalResourceStatus> searchResults = LogicalResourceStatusService.searchByKeyword(name);
        return ResponseEntity.ok(searchResults);
    }
}