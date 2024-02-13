package com.Bcm.Controller.ResourceSpecController.ResourceConfigController.SubClassesController;

import com.Bcm.Model.ResourceSpecABE.ResourceConfigBE.SubClasses.ROUStatus;
import com.Bcm.Service.Srvc.ResourceSpecService.ResourceConfigService.SubClasses.ROUStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/ ROUStatus")
public class ROUStatusController {

    @Autowired
   ROUStatusService ROUStatusService;

    @PostMapping
    public ResponseEntity<ROUStatus> createROUStatus(@RequestBody ROUStatus  ROUStatus) {
        ROUStatus createdROUStatus =  ROUStatusService.create( ROUStatus);
        return ResponseEntity.ok(createdROUStatus);
    }

    @GetMapping
    public ResponseEntity<List< ROUStatus>> getAllCategories() {
        List< ROUStatus> categories =  ROUStatusService.read();
        return ResponseEntity.ok(categories);
    }
    @GetMapping("/{RSID}")
    public ResponseEntity< ROUStatus> getROUStatusById(@PathVariable("RSID") int RSID) {
        ROUStatus  ROUStatus =  ROUStatusService.findById(RSID);
        return ResponseEntity.ok( ROUStatus);
    }

    @PutMapping("/{RSID}")
    public ResponseEntity< ROUStatus> updateROUStatus(
            @PathVariable("RSID") int RSID,
            @RequestBody  ROUStatus updatedROUStatus) {

        ROUStatus updated =  ROUStatusService.update(RSID, updatedROUStatus);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{RSID}")
    public ResponseEntity<String> deleteROUStatus(@PathVariable("RSID") int RSID) {
        String resultMessage =  ROUStatusService.delete(RSID);
        return ResponseEntity.ok(resultMessage);
    }

    @GetMapping("/search")
    public ResponseEntity<List< ROUStatus>> searchCategoriesByKeyword(@RequestParam("name") String name) {
        List< ROUStatus> searchResults =  ROUStatusService.searchByKeyword(name);
        return ResponseEntity.ok(searchResults);
    }
}