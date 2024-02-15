package com.Bcm.Controller.ResourceSpecController.ResourceConfigController.SubClassesController;

import com.Bcm.Model.ResourceSpecABE.ResourceConfigBE.SubClasses.ResourceConfigVersion;
import com.Bcm.Service.Srvc.ResourceSpecService.ResourceConfigService.SubClasses.ResourceConfigVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/ResourceConfigVersion")
public class ResourceConfigVersionController {

    @Autowired
    ResourceConfigVersionService ResourceConfigVersionService;

    @PostMapping
    public ResponseEntity<ResourceConfigVersion> createResourceConfigVersion(@RequestBody ResourceConfigVersion ResourceConfigVersion) {
        ResourceConfigVersion createdResourceConfigVersion = ResourceConfigVersionService.create(ResourceConfigVersion);
        return ResponseEntity.ok(createdResourceConfigVersion);
    }

    @GetMapping
    public ResponseEntity<List<ResourceConfigVersion>> getAllCategories() {
        List<ResourceConfigVersion> categories = ResourceConfigVersionService.read();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{RCVID}")
    public ResponseEntity<ResourceConfigVersion> getResourceConfigVersionById(@PathVariable("RCVID") int RCVID) {
        ResourceConfigVersion ResourceConfigVersion = ResourceConfigVersionService.findById(RCVID);
        return ResponseEntity.ok(ResourceConfigVersion);
    }

    @PutMapping("/{RCVID}")
    public ResponseEntity<ResourceConfigVersion> updateResourceConfigVersion(
            @PathVariable("RCVID") int RCVID,
            @RequestBody ResourceConfigVersion updatedResourceConfigVersion) {

        ResourceConfigVersion updated = ResourceConfigVersionService.update(RCVID, updatedResourceConfigVersion);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{RCVID}")
    public ResponseEntity<String> deleteResourceConfigVersion(@PathVariable("RCVID") int RCVID) {
        String resultMessage = ResourceConfigVersionService.delete(RCVID);
        return ResponseEntity.ok(resultMessage);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ResourceConfigVersion>> searchCategoriesByKeyword(@RequestParam("name") String name) {
        List<ResourceConfigVersion> searchResults = ResourceConfigVersionService.searchByKeyword(name);
        return ResponseEntity.ok(searchResults);
    }
}