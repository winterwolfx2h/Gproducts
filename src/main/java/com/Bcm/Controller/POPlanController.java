package com.Bcm.Controller;

import com.Bcm.Exception.ErrorMessage;
import com.Bcm.Exception.InvalidInputException;
import com.Bcm.Model.ProductOfferingABE.POPlan;
import com.Bcm.Service.Srvc.POPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/poplan")
public class POPlanController {

    @Autowired
    POPlanService poPlanService;

    @GetMapping("/listPoPlans")
    public List<POPlan> read(){
            return poPlanService.read();
    }

    @PostMapping("/addPOPlan")
    public ResponseEntity<?> create(@RequestBody POPlan poPlan) {
        try {
            POPlan createdPlan = poPlanService.create(poPlan);
            return ResponseEntity.ok(createdPlan);
        } catch (InvalidInputException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/updatePOPlan/{PO_ID}")
    public ResponseEntity<?> update(@PathVariable int PO_ID, @RequestBody POPlan poPlan) {
        try {
            POPlan updatedPlan = poPlanService.update(PO_ID, poPlan);
            return ResponseEntity.ok(updatedPlan);
        } catch (InvalidInputException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/{PO_ID}")
    public String delete(@PathVariable int PO_ID){
            return poPlanService.delete(PO_ID);
    }

    @GetMapping("/getById/{PO_ID}")
    public ResponseEntity<POPlan> getById(@PathVariable int PO_ID) {
        try {
            POPlan foundPlan = poPlanService.findById(PO_ID);
            return ResponseEntity.ok(foundPlan);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/searchByKeyword")
    public List<POPlan> searchByKeyword(@RequestParam String name) {
        try {
            return poPlanService.searchByKeyword(name);
        } catch (Exception e) {
            throw handleException(e);
        }
    }

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
