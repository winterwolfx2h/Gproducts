package com.Bcm.Controller.ResourceSpecController.PhysicalResourceContoller.SubClassesController;

import com.Bcm.Model.ResourceSpecABE.PhysicalResourceBE.SubClasses.CableType;
import com.Bcm.Service.Srvc.ResourceSpecService.PhysicalResourceService.SubClasses.CableTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/ CableType")
public class CableTypeController {

    @Autowired
    CableTypeService CableTypeService;

    @PostMapping
    public ResponseEntity<CableType> createCableType(@RequestBody CableType  CableType) {
        CableType createdCableType =  CableTypeService.create( CableType);
        return ResponseEntity.ok(createdCableType);
    }

    @GetMapping
    public ResponseEntity<List< CableType>> getAllCategories() {
        List< CableType> categories =  CableTypeService.read();
        return ResponseEntity.ok(categories);
    }
    @GetMapping("/{CbTID}")
    public ResponseEntity< CableType> getCableTypeById(@PathVariable("CbTID") int CbTID) {
        CableType  CableType =  CableTypeService.findById(CbTID);
        return ResponseEntity.ok( CableType);
    }

    @PutMapping("/{CbTID}")
    public ResponseEntity< CableType> updateCableType(
            @PathVariable("CbTID") int CbTID,
            @RequestBody  CableType updatedCableType) {

        CableType updated =  CableTypeService.update(CbTID, updatedCableType);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{CbTID}")
    public ResponseEntity<String> deleteCableType(@PathVariable("CbTID") int CbTID) {
        String resultMessage =  CableTypeService.delete(CbTID);
        return ResponseEntity.ok(resultMessage);
    }

    @GetMapping("/search")
    public ResponseEntity<List< CableType>> searchCategoriesByKeyword(@RequestParam("name") String name) {
        List< CableType> searchResults =  CableTypeService.searchByKeyword(name);
        return ResponseEntity.ok(searchResults);
    }
}