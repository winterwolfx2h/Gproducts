package com.Bcm.Controller.ResourceSpecController.PhysicalResourceContoller.SubClassesController;

import com.Bcm.Model.ResourceSpecABE.PhysicalResourceBE.SubClasses.ConnectorType;
import com.Bcm.Service.Srvc.ResourceSpecService.PhysicalResourceService.SubClasses.ConnectorTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/ConnectorType")
public class ConnectorTypeController {

    @Autowired
    ConnectorTypeService ConnectorTypeService;

    @PostMapping
    public ResponseEntity<ConnectorType> createConnectorType(@RequestBody ConnectorType  ConnectorType) {
        ConnectorType createdConnectorType =  ConnectorTypeService.create( ConnectorType);
        return ResponseEntity.ok(createdConnectorType);
    }

    @GetMapping
    public ResponseEntity<List< ConnectorType>> getAllCategories() {
        List< ConnectorType> categories =  ConnectorTypeService.read();
        return ResponseEntity.ok(categories);
    }
    @GetMapping("/{CnTID}")
    public ResponseEntity< ConnectorType> getConnectorTypeById(@PathVariable("CnTID") int CnTID) {
        ConnectorType  ConnectorType =  ConnectorTypeService.findById(CnTID);
        return ResponseEntity.ok( ConnectorType);
    }

    @PutMapping("/{CnTID}")
    public ResponseEntity< ConnectorType> updateConnectorType(
            @PathVariable("CnTID") int CnTID,
            @RequestBody  ConnectorType updatedConnectorType) {

        ConnectorType updated =  ConnectorTypeService.update(CnTID, updatedConnectorType);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{CnTID}")
    public ResponseEntity<String> deleteConnectorType(@PathVariable("CnTID") int CnTID) {
        String resultMessage =  ConnectorTypeService.delete(CnTID);
        return ResponseEntity.ok(resultMessage);
    }

    @GetMapping("/search")
    public ResponseEntity<List< ConnectorType>> searchCategoriesByKeyword(@RequestParam("name") String name) {
        List< ConnectorType> searchResults =  ConnectorTypeService.searchByKeyword(name);
        return ResponseEntity.ok(searchResults);
    }
}