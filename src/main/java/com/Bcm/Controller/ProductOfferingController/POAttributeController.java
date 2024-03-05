package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Exception.ErrorMessage;
import com.Bcm.Exception.InvalidInputException;
import com.Bcm.Model.ProductOfferingABE.POAttributes;
import com.Bcm.Model.ProductOfferingABE.SubClasses.AttributeCategory;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.POAttributesService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.AttributeCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/POAttribute")
public class POAttributeController {

    final POAttributesService poAttributesService;
    final AttributeCategoryService attributeCategoryService;

    @GetMapping("/listPOAttributess")
    public List<POAttributes> read() {
        return poAttributesService.read();
    }

    @PostMapping("/addPOAttributes")
    public ResponseEntity<?> create(@RequestBody List<POAttributes> POAttributesList) {
        List<POAttributes> createdPOAttributesList = new ArrayList<>();

        for (POAttributes poAttribute : POAttributesList) {
            String attributeCategoryName = poAttribute.getAttributeCategory();
            AttributeCategory attributeCategory = attributeCategoryService.findByName(attributeCategoryName);

            if (attributeCategory != null) {
                poAttribute.setAttributeCategory(attributeCategoryName);
                POAttributes createdPlan = poAttributesService.create(poAttribute);
                createdPOAttributesList.add(createdPlan);
            } else {
                StringBuilder errorMessage = new StringBuilder("The following entities were not found:");
                if (attributeCategory == null)
                    errorMessage.append(" AttributeCategory with name: ").append(attributeCategoryName);
                return ResponseEntity.badRequest().body(errorMessage.toString());
            }
        }

        return ResponseEntity.ok(createdPOAttributesList);
    }


    @PutMapping("/updatePOAttributes/{poAttribute_code}")
    public ResponseEntity<?> update(@PathVariable int poAttribute_code, @RequestBody POAttributes POAttributes) {
        try {
            POAttributes updatedPlan = poAttributesService.update(poAttribute_code, POAttributes);
            return ResponseEntity.ok(updatedPlan);
        } catch (InvalidInputException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/{poAttribute_code}")
    public String delete(@PathVariable int poAttribute_code) {
        return poAttributesService.delete(poAttribute_code);
    }

    @GetMapping("/getById/{poAttribute_code}")
    public ResponseEntity<POAttributes> getById(@PathVariable int poAttribute_code) {
        try {
            POAttributes foundPlan = poAttributesService.findById(poAttribute_code);
            return ResponseEntity.ok(foundPlan);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /*@GetMapping("/searchByKeyword")
    public List<POAttributes> searchByKeyword(@RequestParam String attributeValDesc) {
        try {
            return poAttributesService.searchByKeyword(attributeValDesc);
        } catch (Exception e) {
            throw handleException(e);
        }
    }*/

    private RuntimeException handleException(Exception e) {
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                e.getMessage(),
                "There was an error processing the request."
        );
        return new RuntimeException(errorMessage.toString(), e);
    }
}
