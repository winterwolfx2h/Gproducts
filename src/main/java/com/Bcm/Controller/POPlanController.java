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
    public List<POPlan> read() {
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

    @PutMapping("/updatePOPlan/{TMCODE}")
    public ResponseEntity<?> update(@PathVariable int TMCODE, @RequestBody POPlan poPlan) {
        try {
            POPlan updatedPlan = poPlanService.update(TMCODE, poPlan);
            return ResponseEntity.ok(updatedPlan);
        } catch (InvalidInputException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/{TMCODE}")
    public String delete(@PathVariable int TMCODE) {
        return poPlanService.delete(TMCODE);
    }

    @GetMapping("/getById/{TMCODE}")
    public ResponseEntity<POPlan> getById(@PathVariable int TMCODE) {
        try {
            POPlan foundPlan = poPlanService.findById(TMCODE);
            return ResponseEntity.ok(foundPlan);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/searchByKeyword")
    public List<POPlan> searchByKeyword(@RequestParam String DES) {
        try {
            return poPlanService.searchByKeyword(DES);
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
