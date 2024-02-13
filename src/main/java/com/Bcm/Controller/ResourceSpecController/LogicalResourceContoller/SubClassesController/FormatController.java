package com.Bcm.Controller.ResourceSpecController.LogicalResourceContoller.SubClassesController;


import com.Bcm.Model.ResourceSpecABE.LogicalResourceBE.SubClasses.Format;
import com.Bcm.Service.Srvc.ResourceSpecService.LogicalResourceService.SubClasses.FormatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/Format")
public class FormatController {

    @Autowired
    FormatService FormatService;

    @PostMapping
    public ResponseEntity<Format> createFormat(@RequestBody Format Format) {
        Format createdFormat = FormatService.create(Format);
        return ResponseEntity.ok(createdFormat);
    }

    @GetMapping
    public ResponseEntity<List<Format>> getAllCategories() {
        List<Format> categories = FormatService.read();
        return ResponseEntity.ok(categories);
    }
    @GetMapping("/{FID}")
    public ResponseEntity<Format> getFormatById(@PathVariable("FID") int FID) {
        Format Format = FormatService.findById(FID);
        return ResponseEntity.ok(Format);
    }

    @PutMapping("/{FID}")
    public ResponseEntity<Format> updateFormat(
            @PathVariable("FID") int FID,
            @RequestBody Format updatedFormat) {

        Format updated = FormatService.update(FID, updatedFormat);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{FID}")
    public ResponseEntity<String> deleteFormat(@PathVariable("FID") int FID) {
        String resultMessage = FormatService.delete(FID);
        return ResponseEntity.ok(resultMessage);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Format>> searchCategoriesByKeyword(@RequestParam("name") String name) {
        List<Format> searchResults = FormatService.searchByKeyword(name);
        return ResponseEntity.ok(searchResults);
    }
}
