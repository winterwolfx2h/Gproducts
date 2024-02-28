package com.Bcm.Controller.ProductOfferingController.SubClassesController;


import com.Bcm.Model.ProductOfferingABE.SubClasses.SubFamily;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Type;
import com.Bcm.Model.product.Product;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.SubFamilyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/SubFamily")

public class SubFamilyController {

@Autowired
private SubFamilyService subFamilyService;
    @PostMapping
    public ResponseEntity<SubFamily> createSubFamily(@RequestBody SubFamily subFamily) {
        try {
            SubFamily createdSubFamily = subFamilyService.create(subFamily);
            return ResponseEntity.ok(createdSubFamily);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }


    @GetMapping
    public ResponseEntity<?> getAllSubFamily() {
        try {
            List<SubFamily> subFamily = subFamilyService.read();
            return ResponseEntity.ok(subFamily);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}
