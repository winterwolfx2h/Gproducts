package com.Bcm.Controller.ResourceSpecController.PhysicalResourceContoller.SubClassesController;

import com.Bcm.Model.ResourceSpecABE.PhysicalResourceBE.SubClasses.Gender;
import com.Bcm.Service.Srvc.ResourceSpecService.PhysicalResourceService.SubClasses.GenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/ Gender")
public class GenderController {

    @Autowired
    GenderService GenderService;

    @PostMapping
    public ResponseEntity<Gender> createGender(@RequestBody Gender  Gender) {
        Gender createdGender =  GenderService.create( Gender);
        return ResponseEntity.ok(createdGender);
    }

    @GetMapping
    public ResponseEntity<List< Gender>> getAllCategories() {
        List< Gender> categories =  GenderService.read();
        return ResponseEntity.ok(categories);
    }
    @GetMapping("/{GID}")
    public ResponseEntity< Gender> getGenderById(@PathVariable("GID") int GID) {
        Gender  Gender =  GenderService.findById(GID);
        return ResponseEntity.ok( Gender);
    }

    @PutMapping("/{GID}")
    public ResponseEntity< Gender> updateGender(
            @PathVariable("GID") int GID,
            @RequestBody  Gender updatedGender) {

        Gender updated =  GenderService.update(GID, updatedGender);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{GID}")
    public ResponseEntity<String> deleteGender(@PathVariable("GID") int GID) {
        String resultMessage =  GenderService.delete(GID);
        return ResponseEntity.ok(resultMessage);
    }

    @GetMapping("/search")
    public ResponseEntity<List< Gender>> searchCategoriesByKeyword(@RequestParam("name") String name) {
        List< Gender> searchResults =  GenderService.searchByKeyword(name);
        return ResponseEntity.ok(searchResults);
    }
}